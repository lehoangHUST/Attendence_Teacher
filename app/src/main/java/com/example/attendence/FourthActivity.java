package com.example.attendence;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class FourthActivity extends AppCompatActivity {

    private EditText UserNameEmail, UserClassName, UserPasswordRegister;
    private Button UserRegisterBtn;
    private CheckBox showcheck_btn;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String AdminId;

    // Lưu trữ dữ liệu User
    private ArrayList<Admin> admins = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        UserNameEmail = findViewById(R.id.profemail);
        UserClassName = findViewById(R.id.classnameregister);
        UserPasswordRegister = findViewById(R.id.passwordRegister);
        UserRegisterBtn = findViewById(R.id.buttonRegister);
        showcheck_btn = findViewById(R.id.checkbox_btn);

        // Firebase cho người dùng đăng nhập
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("admin");

        readData();

        // Chọn nút bấm đăng kí
        UserRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(validate()){
                   // Register to database upload (user)
                   String user_email = UserNameEmail.getText().toString().trim();
                   String user_password = UserPasswordRegister.getText().toString().trim();
                   // Kiểm tra xem liệu tài khoản đó đã có trong database hay chưa ?
                   if (TextUtils.isEmpty(AdminId))
                   {
                       AdminId = mFirebaseDatabase.push().getKey();
                   }

                   Admin admin = new Admin(user_email, user_password);
                   mFirebaseDatabase.child(AdminId).setValue(admin);
                   startActivity(new Intent(FourthActivity.this,ThirdActivity.class));
               }
            }
        });

        // Chọn nút bấm hiện và che mật khẩu
        showcheck_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // Show Password
                    UserPasswordRegister.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    // Hide Password
                    UserPasswordRegister.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    // Kiểm tra đã nhập đúng các giá trị hay chưa
    private boolean validate(){
        boolean result = false;

        // Kiểm tra xem đã nhập đúng trường email hay chưa
        // Bắt buộc phải có đuôi @gmail.com
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern passwordPattern =
                Pattern.compile("^" +
                        "(?=.*[@#$%^&+=])" +     // at least 1 special character
                        "(?=\\S+$)" +            // no white spaces
                        ".{4,}" +                // at least 4 characters
                        "$");

        // Kiểm tra xem có phải mật khẩu mạnh hay không ?

        String name = UserNameEmail.getText().toString();
        String password = UserPasswordRegister.getText().toString();
        String classname = UserClassName.getText().toString();

        // Kiểm tra xem đã nhập kí tự nào trong các trường chưa
        if(name.isEmpty() && password.isEmpty() || classname.isEmpty()){
            Toast.makeText(this,"Please Enter all details",Toast.LENGTH_SHORT).show();
            result = false;
        }else{
            result =true;
        }

        // Kiểm tra xem có nhập đúng format của email không ?
        if (name.matches(emailPattern))
        {
            Toast.makeText(this,"valid email address",Toast.LENGTH_SHORT).show();
            result = true;
        }
        else
        {
            Toast.makeText(this,"Invalid email address",Toast.LENGTH_SHORT).show();
            result = false;
        }

        // Kiểm tra xem có phải passwrod mạnh hay không ?
        if (!passwordPattern.matcher(password).matches())
        {
            Toast.makeText(this, "Password is weak", Toast.LENGTH_SHORT).show();
            result = false;
        }
        else{
            result = true;
        }

        // Kiểm tra xem tài khoản đó đã được đăng kí hay chưa
        // So sánh với kết quả đã có trong firebase hay chưa
        for (Admin admin: admins)
        {
            System.out.println(admin.getAdminUser());
            if (admin.getAdminUser().equals(name) && admin.getAdminPass().equals(password))
            {
                Toast.makeText(this, "Valid account in database", Toast.LENGTH_SHORT).show();
                result = false;
                break;
            }
        }

        return result;
    }


    // Đọc data từ firebase
    private void readData()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        // Lấy data từ key là admin của hệ thống
        databaseReference.child("admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                admins.clear(); // Xóa đi list toàn bộ để tránh trường hợp còn
                for (DataSnapshot snap: snapshot.getChildren()) {
                    Admin admin = snap.getValue(Admin.class);
                    admins.add(admin);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // kiểm tra xem
}
