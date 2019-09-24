package com.rishabhanand.design_profile_screen_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;

public class MainActivity extends AppCompatActivity {

    AppBarLayout appBarLayout;
    Toolbar toolbar;

    int colorOffset=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appBarLayout=(AppBarLayout)findViewById(R.id.appbar);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Hero's Profile");

        setSupportActionBar(toolbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                colorOffset=-i;
                if(colorOffset>256)
                    colorOffset=256;

                toolbar.getBackground().setAlpha(colorOffset);
                toolbar.setAlpha(colorOffset/256f);
            }
        });

    }
}
