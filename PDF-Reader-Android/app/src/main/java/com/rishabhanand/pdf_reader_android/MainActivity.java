package com.rishabhanand.pdf_reader_android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.rishabhanand.pdf_reader_android.Adapter.PDF_Adapter;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listview_pdf;
    EditText et_url;
    Button open_url;
    public static ArrayList<File> pdfarray =new ArrayList<>();
    PDF_Adapter obj_adapter;
    public  static int REQUEST_PERMISSION = 1;
    boolean boolean_permission;
    File dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview_pdf=(ListView)findViewById(R.id.listview_pdf);
        dir = new File(Environment.getExternalStorageDirectory().toString());
        et_url=(EditText)findViewById(R.id.Url);
        open_url=(Button)findViewById(R.id.open_url);

        permission_fun();

        open_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_url.getText().toString().trim() != "") {
                    Intent intent = new Intent(getApplicationContext(), ViewPDFFiles.class);
                    intent.putExtra("type", "url");
                    intent.putExtra("url",et_url.getText().toString() );
                    startActivity(intent);
                }
            }
        });

        listview_pdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ViewPDFFiles.class);
                intent.putExtra("type","local");
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

    }

    private void permission_fun() {

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION);
            }
        } else {
            boolean_permission = true;
            getFile(dir);
            obj_adapter = new PDF_Adapter(getApplicationContext(),pdfarray);
            listview_pdf.setAdapter(obj_adapter);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION) {

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                boolean_permission= true;
                getFile(dir);
                obj_adapter=new PDF_Adapter(getApplicationContext(),pdfarray);
                listview_pdf.setAdapter(obj_adapter);
            } else {
                Toast.makeText(MainActivity.this,"Please Allow Permmission",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private ArrayList<File> getFile(File dir) {

        File listFile[] = dir.listFiles();

        if(listFile!=null && listFile.length>0) {
            for(int i=0;i<listFile.length;i++) {

                if(listFile[i].isDirectory()) {
                    getFile(listFile[i]);
                } else {
                    boolean booleanpdf = false;
                    if(listFile[i].getName().endsWith(".pdf")) {
                        for(int j=0;j<pdfarray.size();j++) {
                            if(pdfarray.get(j).getName().equals(listFile[i].getName())) {
                                booleanpdf=true;
                            } else {

                            }
                        }
                        if (booleanpdf) {

                            booleanpdf=false;
                        }
                        else {
                            pdfarray.add(listFile[i]);
                        }
                    }
                }
            }
        }
        return  pdfarray;
    }
}
