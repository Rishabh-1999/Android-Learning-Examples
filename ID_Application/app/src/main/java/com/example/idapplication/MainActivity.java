package com.example.idapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText ed1;
    Button btn1;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=(EditText)findViewById(R.id.et1);
        btn1=(Button)findViewById(R.id.btn1);
        tv1=(TextView)findViewById(R.id.tv1);
        tv1.setVisibility(View.GONE);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=ed1.getText().toString().trim();
                String dob=str.substring(0,6);
                String sGender;
                int gender=Integer.parseInt(Character.toString(str.charAt(6)));
                if(gender<5)
                    sGender= (String) getString(R.string.male);
                else
                    sGender= (String) getString(R.string.female);
                int nationality=Integer.parseInt(Character.toString(str.charAt(10)));
                String sNationality;
                if(nationality==0)
                    sNationality= (String) getString(R.string.sacitizen);
                else
                    sNationality= (String) getString(R.string.permanenttcitizen);
                String text="Date of Birth :"+dob+"\n"+"Gender : "+sGender+"\n"+"Nationality : "+sNationality;
                tv1.setText(text);
                tv1.setVisibility(View.VISIBLE);
            }
        });
    }
}
