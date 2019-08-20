package com.example.app_intro_example;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class App_Intro_Activity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_app__intro_);

        addSlide(AppIntroFragment.newInstance("HTML", "Hyper Text Markup Launguage",
                R.drawable.html, ContextCompat.getColor(getApplicationContext(), R.color.slide1)));

        addSlide(AppIntroFragment.newInstance("CSS", "Cascading Style Sheets",
                R.drawable.css, ContextCompat.getColor(getApplicationContext(), R.color.slide2)));

        addSlide(AppIntroFragment.newInstance("JS", "Java Script",
                R.drawable.js, ContextCompat.getColor(getApplicationContext(), R.color.slide3)));

        setFadeAnimation();
        /*
        setFadeAnimation()
        setZoomAnimation()
        setFlowAnimation()
        setSlideOverAnimation()
        setDepthAnimation()
         */
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
