package com.example.hospitalhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BloodDonate extends AppCompatActivity {
    Button yes,no;
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donate);

        yes = findViewById(R.id.yes_button);
        no = findViewById(R.id.no_button);
        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BloodDonate.this,HomeScreen.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BloodDonate.this,BloodDonateUser.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }
    public void onBackPressed(){
        Intent i = new Intent(BloodDonate.this,HomeScreen.class);
        startActivity(i);
        overridePendingTransition(0,0);
        finish();
    }
}