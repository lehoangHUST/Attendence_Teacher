package com.example.attendence;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText classname;
    int request_code =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //classname = findViewById(R.id.editclassname);
    }
    public void submit (View view)
    {
        startActivity(new Intent(this,SecondActivity.class));
    }
    public void register (View view)
    {
        startActivity(new Intent(this,FourthActivity.class));
    }
}
