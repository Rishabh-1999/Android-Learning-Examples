package com.example.risha.myapplicationon14_06_18;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //camera
        et = findViewById(R.id.etNo);
        findViewById(R.id.btnCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(i);
            }
        });

        findViewById(R.id.btnGallery).setOnClickListener(new View.OnClickListener() {
            //Gallery
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("content://media/external/images/media/"));
                startActivity(i);
            }
        });
        //callLog
        findViewById(R.id.btnCallLog).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("content://call_log/calls/1"));
                startActivity(i);
            }
        });
        //browser
        findViewById(R.id.btnBrowser).setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.goggle.com/"));
                startActivity(Intent.createChooser(i, "Title"));
            }
        });
        //contact button
        findViewById(R.id.btnContact).setOnClickListener(new View.OnClickListener()
                //Gallery
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("content://contacts/people/"));
                startActivity(i);
            }
        });
        //callbutton
        findViewById(R.id.btnCall).setOnClickListener(new View.OnClickListener() {
            //Gallery

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + et.getText()));
                startActivity(i);


            }
        });
        //dialbutton
        findViewById(R.id.btnDial).setOnClickListener(new View.OnClickListener() {
                //Gallery

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + et.getText()));
                startActivity(i);
             /*   Intent I = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + et.getText()));
                startActivity(i);
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + et.getText())));*/
            }
        });
    }
}