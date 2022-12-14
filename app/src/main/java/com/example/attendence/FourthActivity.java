package com.example.attendence;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FourthActivity extends AppCompatActivity {

    private EditText UserNameEmail, UserClassName, UserPasswordRegister;
    private Button UserRegisterBtn;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String AdminId;

    // Lưu trữ dữ liệu User
    private ArrayList<Students> users = new ArrayList<Students>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        UserNameEmail = findViewById(R.id.profemail);
        UserClassName = findViewById(R.id.classnameregister);
        UserPasswordRegister = findViewById(R.id.passwordRegister);
        UserRegisterBtn= findViewById(R.id.buttonRegister);

        // Firebase cho người dùng đăng nhập
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("admin");

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
               }
               startActivity(new Intent(FourthActivity.this,ThirdActivity.class));
            }
        });

    }

    private Boolean validate(){
        boolean result = false;
        String name = UserNameEmail.getText().toString();
        String password = UserPasswordRegister.getText().toString();
        String classname = UserClassName.getText().toString();

        if(name.isEmpty() && password.isEmpty() || classname.isEmpty()){
            Toast.makeText(this,"Please Enter all details",Toast.LENGTH_SHORT).show();
        }else{
            result =true;
        }
        return result;
    }
}
