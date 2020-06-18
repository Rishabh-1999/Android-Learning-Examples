package com.example.balanceviewer.App_Into;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.example.balanceviewer.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/* Made by Rishabh Anand */

public class App_Intro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1st Slide
        addSlide(AppIntroFragment.newInstance("Welcome", "App Used to store Balance of People",
                R.drawable.front, ContextCompat.getColor(getApplicationContext(), R.color.slides)));

        //2nd Slide
        addSlide(AppIntroFragment.newInstance("FingerPrint", "It is secured by FingerPrint password",
                R.drawable.fingerprint, ContextCompat.getColor(getApplicationContext(), R.color.slides)));

        //3rd Slide
        addSlide(AppIntroFragment.newInstance("Add People", "Click to Add Person",
                R.drawable.add_people, ContextCompat.getColor(getApplicationContext(), R.color.slides)));


        addSlide(AppIntroFragment.newInstance("Delete", "Click to delete Persons Balance",
                R.drawable.delete_person, ContextCompat.getColor(getApplicationContext(), R.color.slides)));

        addSlide(AppIntroFragment.newInstance("Share", "Click to Share Balance",
                R.drawable.share_person, ContextCompat.getColor(getApplicationContext(), R.color.slides)));

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