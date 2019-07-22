package com.example.fragments_with_recyclerview;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PersonAdapter.ItemClicked {

    TextView tvName,tvTel;
    EditText etName,etTel;
    Button btnAdd;
    ListFrag listFrag;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvName=(TextView)findViewById(R.id.tvName);
        tvTel=(TextView)findViewById(R.id.tvTel);
        etName=(EditText) findViewById(R.id.etName);
        etTel=(EditText) findViewById(R.id.etTel);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        fragmentManager=this.getSupportFragmentManager();
        listFrag=(ListFrag) fragmentManager.findFragmentById(R.id.listFrag);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().isEmpty() || etTel.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Enterv all field", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ApplicationClass.people.add(new Person(etName.getText().toString().trim(),etTel.getText().toString().trim()));
                    Toast.makeText(MainActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    etTel.setText(null);
                    etName.setText(null);
                    listFrag.notifyDataChanged();
                }
            }
        });
        onItemClicked(0);
    }

    @Override
    public void onItemClicked(int index) {
        tvName.setText(ApplicationClass.people.get(index).getName());
        tvTel.setText(ApplicationClass.people.get(index).getTel());
    }
}
