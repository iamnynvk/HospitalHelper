package com.example.hospitalhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        /*Utility.setIsLogin(getApplicationContext(),"");
                Intent ik = new Intent(this,MainActivity.class);
                startActivity(ik);
                finishAffinity();
                break;*/
    }
}