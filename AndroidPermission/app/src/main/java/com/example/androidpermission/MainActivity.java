package com.example.androidpermission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button btn;
final int UNIQUE_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= UNIQUE_REQUEST_CODE)
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, UNIQUE_REQUEST_CODE);
                else
                {
                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

       if(requestCode==UNIQUE_REQUEST_CODE)
       {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Thank you! Permission granted", Toast.LENGTH_SHORT).show();
            }
            else if (grantResults[0]==PackageManager.PERMISSION_DENIED)
            {
                if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                    dialog.setMessage("This permission is important to save a file to the phone")
                            .setTitle("Important Permission Required!");

                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, UNIQUE_REQUEST_CODE);
                        }
                    });

                    dialog.setNegativeButton("NO THANKS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Cannot be done", Toast.LENGTH_SHORT).show();
                        }
                    });

                 dialog.show();
                }
                else
                {
                    Toast.makeText(this, "Never Show", Toast.LENGTH_SHORT).show();
                }
            }
       }


    }
}
