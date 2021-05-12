package com.example.hospitalhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPasswordScreen extends AppCompatActivity {

    EditText forgot_email_edittext;
    Button forgot_link_send;
    private ProgressDialog mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_screen);

        forgot_email_edittext = findViewById(R.id.forgot_email_edittext);
        forgot_link_send = findViewById(R.id.forgot_link_send);
        mprogress = new ProgressDialog(this);


    }
}