package com.example.application_3_intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {
    TextView tvWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        tvWelcome=(TextView)findViewById(R.id.tvWelcome);
        String name= getIntent().getStringExtra("name");
        tvWelcome.setText(name+" , welcome to activity 2");
    }
}