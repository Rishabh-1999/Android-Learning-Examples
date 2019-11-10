package com.example.risha.homeworkon12_06_18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    CheckBox c1, c2, c3, c4;
    RadioGroup rg;
    Button b2;
    TextView v;
    String h = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);
        rg = findViewById(R.id.rg);
        b2 = findViewById(R.id.button1);
        v = findViewById(R.id.dis);

        Bundle extra = getIntent().getExtras();
        String user= extra.getString("username");
        String pass = extra.getString("password");

      /*  Intent i = getIntent();
        String user = i.getStringExtra("username");
        String pass = i.getStringExtra("password");*/
        v.setText("My Username is "+user+" and my Password is: "+pass);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int id = rg.getCheckedRadioButtonId();
                RadioButton r = findViewById(id);

                if (c1.isChecked()) {
                    h = h + ", " + c1.getText().toString();
                }
                if (c2.isChecked()) {
                    h = h + ", " + c2.getText().toString();
                }
                if (c3.isChecked()) {
                    h = h + " , " + c3.getText().toString();
                }
                if (c4.isChecked()) {
                    h = h + " , " + c4.getText().toString();
                }
                Toast.makeText(Main2Activity.this, "I qualified in " +h+ " And i am " + r.getText(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
