package com.example.recycleviewandcardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PersonAdapter.ItemClicked {
RecyclerView recyclerView;
RecyclerView.Adapter myAdater;
RecyclerView.LayoutManager layoutManager;

ArrayList<Person> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        //layoutManager=new LinearLayoutManager(this);
        //layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        layoutManager=new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);



        recyclerView.setLayoutManager(layoutManager);
        people=new ArrayList<Person>();
        people.add(new Person("Rishabh","Anand","plane"));
        people.add(new Person("Rajat","Gupta","bus"));
        people.add(new Person("Ridhav","Modi","plane"));
        people.add(new Person("Rishav","Garg","bus"));
        myAdater=new PersonAdapter(this,people);
        recyclerView.setAdapter(myAdater);
    }

    @Override
    public void onItemClicked(int index) {
        Toast.makeText(this, "Surname: "+people.get(index).getSurname(), Toast.LENGTH_SHORT).show();
    }
}
