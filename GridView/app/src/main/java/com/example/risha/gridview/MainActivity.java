package com.example.risha.gridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
GridView gridView;
Integer[] android1={R.drawable.lollipop,R.drawable.lollipop,R.drawable.lollipop,R.drawable.lollipop};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView=findViewById(R.id.gridview);
        ArrayAdapter<Integer> adapter=new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1,android1);
        gridView.setAdapter(adapter);
    }
}
