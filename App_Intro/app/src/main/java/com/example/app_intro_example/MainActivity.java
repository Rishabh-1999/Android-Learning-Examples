package com.example.app_intro_example;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Boolean firstStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a shared Preferences to know whether it is first or not
        SharedPreferences settings=getSharedPreferences("PREFS", Context.MODE_PRIVATE);

        //get value from shared Preferences by keeping default true
        firstStart=settings.getBoolean("firstTime",true);

        // checking firstStart
        if(firstStart) {
            // changing shared Preferences to false
            SharedPreferences.Editor editor=settings.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();

            //Changing Activity to Introduction Activity
            Intent intent = new Intent(getApplicationContext(), App_Intro_Activity.class);
            startActivity(intent);
            Toast.makeText(this, "First Time", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Not First Time", Toast.LENGTH_SHORT).show();
        }
    }
}
