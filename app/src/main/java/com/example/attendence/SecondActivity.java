package com.example.attendence;

import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    private EditText Email;
    private EditText Password;
    private Button Login;
    private TextView userRegistration;
    boolean isValid = false;

    private ArrayList<Admin> admins = new ArrayList<>();
    private ProgressDialog processDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Email = (EditText) findViewById(R.id.editemail);
        Password =(EditText) findViewById(R.id.editpassword);
        Login = (Button ) findViewById(R.id.loginmainbutton);

        readData();
        processDialog = new ProgressDialog(this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra xem các trường nhập vào xem có đúng hay không ??
                isValid = validate(admins, Email.getText().toString(), Password.getText().toString());
            }
        });

        //classname = findViewById(R.id.viewclassname);

//        Intent classintent = getIntent();
//        String classnamepassed = classintent.getStringExtra("Classname");
//        classname.setText(classnamepassed);
    }

  private boolean validate (ArrayList<Admin> admins, String userEmail, String userPassword){

        processDialog.setMessage("........Please Wait.......");
        processDialog.show();

      // So sánh với kết quả đã có trong firebase hay chưa
      for (Admin admin: admins)
      {
          if (admin.getAdminUser().equals(userEmail) && admin.getAdminPass().equals(userPassword))
              return true;
      }
      return false;

  }

    // Sử dụng firebase để tiến hành đăng nhập
    private void readData()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                admins.clear();
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

}
