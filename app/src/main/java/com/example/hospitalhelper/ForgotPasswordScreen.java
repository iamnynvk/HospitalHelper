package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPasswordScreen extends AppCompatActivity {

    EditText forgot_email_edittext;
    Button forgot_btn_send;
    private ProgressDialog mprogress;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_screen);

        forgot_email_edittext = findViewById(R.id.forgot_email_edittext);
        forgot_btn_send = findViewById(R.id.forgot_btn_send);
        mprogress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        forgot_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetpassword();
            }
        });

    }

    private void resetpassword()
    {
        String email = forgot_email_edittext.getText().toString();

        if(email.isEmpty())
        {
            forgot_email_edittext.setError("Email is required");
            forgot_email_edittext.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            forgot_email_edittext.setError("Please provide valid email");
            forgot_email_edittext.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgotPasswordScreen.this,"Check your email to reset your password!",Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(ForgotPasswordScreen.this,LogInScreen.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(ForgotPasswordScreen.this,"Try again! Something wrong happened!",Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}