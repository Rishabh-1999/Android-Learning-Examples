package com.example.bmi_calculator_android;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextInputEditText edweight;
    TextInputEditText edheight;
    TextView tvvalue;
    TextView tvsuggestion;
    Button btnsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edweight=(TextInputEditText)findViewById(R.id.weight);
        edheight=(TextInputEditText)findViewById(R.id.height);
        btnsubmit=(Button)findViewById(R.id.submit);
        tvvalue=(TextView)findViewById(R.id.value);
        tvsuggestion=(TextView)findViewById(R.id.suggestion);

        tvvalue.setVisibility(View.GONE);
        tvsuggestion.setVisibility(View.GONE);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateValue();
            }
        });
    }

    private void calculateValue() {
        double weight= Double.parseDouble(edweight.getText().toString().trim());

        double height= Double.parseDouble(edheight.getText().toString().trim())/100;

        double value=Math.round(weight * 10 / height / height) / 10;

        if(value<=12) {
            tvvalue.setText("Value Not Possible");
            tvsuggestion.setText("Value Not Possible");
        }
        else if(value>12 && 18.5>value) {
            tvvalue.setText("Value : "+value);
            tvsuggestion.setText("UnderWeight");
        }
        else if(value>=18.5 && 25>value) {
            tvvalue.setText("Value : "+value);
            tvsuggestion.setText("Normal");
        }
        else if(value>=25 && 30>value) {
            tvvalue.setText("Value : "+value);
            tvsuggestion.setText("Over Weight");
        }
        else if(value>=30 && 35>value) {
            tvvalue.setText("Value : "+value);
            tvsuggestion.setText("Obese Class I");
        }
        else if(value>=35 && 40>value) {
            tvvalue.setText("Value : "+value);
            tvsuggestion.setText("Obese Class II");
        }
        else if(value>=40) {
            tvvalue.setText("Value : "+value);
            tvsuggestion.setText("Obese Class III");
        }
        tvvalue.setVisibility(View.VISIBLE);
        tvsuggestion.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit");
        builder.setMessage("Are You Sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                System.exit(0);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
