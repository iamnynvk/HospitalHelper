package com.example.hospitalhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


public class LogInScreen extends AppCompatActivity {

    EditText email_edittext;
    EditText password_edittext;
    TextView forgot_pass;
    Button login_button;
    TextView signup_link;
    private ProgressDialog mprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);

        email_edittext = findViewById(R.id.email_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        forgot_pass = findViewById(R.id.forgot_pass);
        signup_link = findViewById(R.id.signup_link);
        mprogress = new ProgressDialog(this);

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInScreen.this,ForgotPasswordScreen.class);
                startActivity(i);
                finish();
            }
        });

        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInScreen.this,SignUpScreen.class);
                startActivity(i);
                finish();
            }
        });

    }

}