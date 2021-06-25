package com.example.hospitalhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.hospitalhelper.Adapter.IntroViewPagerAdapter;
import com.example.hospitalhelper.Data_Holder.ScreenItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Intro_Screen extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter ;
    TabLayout tabIndicator;

    Button buttonNext;
    int position = 0 ;
    Button ButtonGetStarted;
    Animation ButtonAnimation ;
    Button ButtonSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro__screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        buttonNext = findViewById(R.id.button_next);
        ButtonGetStarted = findViewById(R.id.button_getstarted);
        ButtonSkip = findViewById(R.id.button_skip);
        tabIndicator = findViewById(R.id.tab_indicator);
        ButtonAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.get_started_animation_button);

        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Get Started In Your Check\nHealth Level,\nWelcome!\n","We can care your health and\nsave your life",R.drawable.doctor));
        mList.add(new ScreenItem("You can get a doctor's\nAppoiment\nonline\n","The patient does not have\nto sit line in the hospital",R.drawable.hospital_admin_office));
        mList.add(new ScreenItem("Can call a patient\ndoctor directly\n","You can get primary advice and\ninformation directly from\nthe doctor",R.drawable.doctor_suggestion));

        // setup viewpager
        screenPager =findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position == mList.size()-1) { // when we rech to the last screen
                    // TODO : show the GETSTARTED Button and hide the indicator and the next button
                    loaddLastScreen();
                }
            }
        });

        // tablayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1) {
                    loaddLastScreen();
                }else{
                    visibleButton();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Get Started button click listener
        ButtonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open main activity
                Intent mainActivity = new Intent(getApplicationContext(),LogInScreen.class);
                startActivity(mainActivity);
                overridePendingTransition(0,0);
                savePrefsData();
                finish();
            }
        });

        // skip button click listener
        ButtonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();
    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private void loaddLastScreen() {

        buttonNext.setVisibility(View.INVISIBLE);
        ButtonGetStarted.setVisibility(View.VISIBLE);
        ButtonSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        ButtonGetStarted.setAnimation(ButtonAnimation);
    }
    private void visibleButton() {
        buttonNext.setVisibility(View.VISIBLE);
        ButtonGetStarted.setVisibility(View.INVISIBLE);
        ButtonSkip.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.VISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
    }
}