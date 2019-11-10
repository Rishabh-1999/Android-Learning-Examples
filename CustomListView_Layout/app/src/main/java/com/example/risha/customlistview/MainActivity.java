package com.example.risha.customlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView li;
    ArrayList<Data> mList;
    String name[]={"rishabh","Nishit","fghj","dfgh"};
    String number[] = {"dfgh","fhjk","rtyu","dfgh"};
    CustomAdapter mCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
        li = findViewById(R.id.list);
        mList = new ArrayList<Data>();
        for(int i=0;i<4;i++){
            Data data = new Data();
            data.setName(name[i]);
            data.setNumber(number[i]);
            mList.add(data);
        }
        mCustomAdapter = new CustomAdapter(MainActivity.this,mList);
        li.setAdapter(mCustomAdapter);
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String selection = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(MainActivity.this, ""+name[i], Toast.LENGTH_SHORT).show();
            }
        });
    }
}