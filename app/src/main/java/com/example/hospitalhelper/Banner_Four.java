package com.example.hospitalhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Banner_Four extends AppCompatActivity {

    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner__four);

        backButton = findViewById(R.id.back_button_benner1);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void onBackPressed(){
        Intent i = new Intent(Banner_Four.this,HomeScreen.class);
        startActivity(i);
        overridePendingTransition(0,0);
        finish();
    }
}