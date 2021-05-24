package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.UserNotAuthenticatedException;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LogInScreen extends AppCompatActivity {

    EditText email_edittext;
    EditText password_edittext;
    TextView forgot_pass;
    Button login_button;
    TextView signup_link;
    ProgressDialog mprogress;
    int counter = 0;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);

        email_edittext = findViewById(R.id.email_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        forgot_pass = findViewById(R.id.forgot_pass);
        signup_link = findViewById(R.id.signup_link);
        login_button = findViewById(R.id.login_button);
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

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("email","===>"+email_edittext.getText());
                Log.e("Passsword","===>"+password_edittext.getText());

                UserAuthenticate();
            }
        });

    }

    private void UserAuthenticate() {
        final String emailid=email_edittext.getText().toString();
        final String password = password_edittext.getText().toString();

        mprogress.setMessage("Loading...");
        mprogress.show();
        if(validate())
        {
            mAuth = FirebaseAuth.getInstance();

            mAuth.signInWithEmailAndPassword(emailid,password)
                    .addOnCompleteListener(LogInScreen.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Utility.setIsLogin(getApplicationContext(),"isLogin");
                                Intent intent = new Intent(LogInScreen.this,HomeScreen.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                mprogress.dismiss();
                                password_edittext.setText("");
                                Toast.makeText(getApplicationContext(),"Invalid email/password",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private boolean validate() {
        Boolean result = false;

        final String emailid=email_edittext.getText().toString();
        final String password = password_edittext.getText().toString();
        if (!validateEmailAddress(email_edittext)){
            Toast.makeText(this,"Please Enter Email ID", Toast.LENGTH_SHORT).show();
        }
        else if(password.length()==0)
        {
            Toast.makeText(this,"Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        else {
            return true;
        }
        return result;
    }
    private boolean validateEmailAddress(EditText email) {
        String emailInput = email_edittext.getText().toString();

        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
        {
            return true;
        }
        else {
            return  false;
        }
    }

    @Override
    public void onBackPressed() {
        counter++;
        if (counter == 1){
            Toast.makeText(this,"double backpress exit the apps",Toast.LENGTH_LONG).show();
        }
        else if (counter == 3){
            super.onBackPressed();
        }
    }
}