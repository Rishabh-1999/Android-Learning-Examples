package com.example.fragments_with_recyclerview;

import android.app.Application;

import java.util.ArrayList;

public class ApplicationClass extends Application {
    public  static ArrayList<Person> people;


    @Override
    public void onCreate() {
        super.onCreate();
        people=new ArrayList<Person>();
        people.add(new Person("Rishabh","88732947306"));
        people.add(new Person("Rishabh","88732947306"));
        people.add(new Person("Rishabh","88732947306"));
        people.add(new Person("Rishabh","88732947306"));
    }
}
