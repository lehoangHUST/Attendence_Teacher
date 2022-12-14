package com.example.attendence;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class FifthActivity extends AppCompatActivity {
   private TextView btnvalue;
    int request_code =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        btnvalue = findViewById(R.id.passedclassname);

        Intent classintent = getIntent();
        String classnamepassed = classintent.getStringExtra("Classname");
        btnvalue.setText(classnamepassed);
    }
    public  void addStudents (View view){
        //startActivity(new Intent(this,AddStudent.class));

        String TextClassname = btnvalue.getText().toString();
        // starting our intent
        Intent classintent = new Intent(this,AddStudent.class);
        classintent.putExtra("Classname1",TextClassname);
        startActivityForResult(classintent,request_code);
    }

    public  void takeAttendence(View view){
       // startActivity(new Intent(this,TakeAttendence.class));

        String TextClassname = btnvalue.getText().toString();
        // starting our intent
        Intent classintent = new Intent(this,TakeAttendence.class);
        classintent.putExtra("Classname1",TextClassname);
        startActivityForResult(classintent,request_code);
    }






    // logout below
    private void Logout()
    {
        finish();
        startActivity(new Intent(FifthActivity.this,SecondActivity.class));
        Toast.makeText(FifthActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logoutMenu:{
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
