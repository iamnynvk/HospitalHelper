package com.example.hospitalhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView Logo;
    ImageView HeadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Logo = findViewById(R.id.splashscreen_logo);
        HeadingText = findViewById(R.id.hospital_helper_text);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.e("Task","===>"+Utility.restorePrefData(MainActivity.this));

                if (Utility.restorePrefData(MainActivity.this)) {
                    if(Utility.getIsLogin(getApplicationContext()) != null && Utility.getIsLogin(getApplicationContext()).trim().length() > 0){
                        Log.e("task","=====>"+Utility.getIsLogin(getApplicationContext()).trim().length());
                        Intent mainActivity = new Intent(getApplicationContext(),HomeScreen.class );
                        startActivity(mainActivity);
                        overridePendingTransition(0,0);
                        finish();
                    }else
                    {
                        Intent mainActivity = new Intent(getApplicationContext(),LogInScreen.class );
                        startActivity(mainActivity);
                        overridePendingTransition(0,0);
                        finish();
                    }
                }else{
                    Intent mainActivity = new Intent(getApplicationContext(),Intro_Screen.class );
                    startActivity(mainActivity);
                    overridePendingTransition(0,0);
                    finish();
                }
            }
        }, 3000);
    }

}