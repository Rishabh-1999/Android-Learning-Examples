package com.example.cricketchirps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    TextView tv1;
    EditText et1;
    DecimalFormat format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=(Button)findViewById(R.id.btn1);
        tv1=(TextView)findViewById(R.id.tv1);
        et1=(EditText)findViewById(R.id.et1);
        format=new DecimalFormat("$0.0");
        tv1.setVisibility(View.GONE);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, "Plz enter all field", Toast.LENGTH_SHORT).show();
                else
                {
                    int chirps= Integer.parseInt(et1.getText().toString().trim());
                    double temp=(chirps/3.00)+4;
                    String str="Appox. Temp is "+format.format(temp);
                    tv1.setText(str);
                    tv1.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
