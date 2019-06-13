
package com.example.application_3_intent;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity3 extends AppCompatActivity {
    EditText etSurname;
    Button btnSubmit,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        etSurname=(EditText)findViewById(R.id.etSurname);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etSurname.getText().toString().isEmpty())
                {
                    Toast.makeText(Activity3.this, "Plz enter all value", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    String surname=etSurname.getText().toString();
                    Intent intent=new Intent();
                    intent.putExtra("surname",surname);
                    setResult(RESULT_OK, intent);
                    Activity3.this.finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                Activity3.this.finish();
            }
        });
    }


}
