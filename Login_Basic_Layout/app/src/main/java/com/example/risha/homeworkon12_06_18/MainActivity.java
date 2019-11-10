package com.example.risha.homeworkon12_06_18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText user,password,password1;
    Button b1;
    String u="rishabh";
    String p="1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=findViewById(R.id.user);
        password=findViewById(R.id.password);
        password1=findViewById(R.id.password1);
        b1=findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(u.equals(user.getText().toString()) && p.equals(password.getText().toString()) && p.equals(password1.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Logined Succesfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this , Main2Activity.class);
                    i.putExtra("username",user.getText().toString());
                    i.putExtra("password",password.getText().toString());
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Wrong Credential", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

