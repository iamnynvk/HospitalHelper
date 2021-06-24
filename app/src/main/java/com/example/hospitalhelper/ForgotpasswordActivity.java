package com.example.hospitalhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hospitalhelper.Data_Holder.NewPasswordHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForgotpasswordActivity extends AppCompatActivity {

    EditText newPassword;
    Button submitButton;
    ProgressDialog mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        newPassword = findViewById(R.id.newpassword_edittext);
        submitButton = findViewById(R.id.forgot_btn_submit);
        mprogress = new ProgressDialog(ForgotpasswordActivity.this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        final String password = newPassword.getText().toString().trim();

                        mprogress.setMessage("Please Wait...");
                        mprogress.show();

                        // Storedata in Realtime Database
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference root = db.getReference("Patients");

                        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        NewPasswordHolder passwordHolder = new NewPasswordHolder(password, user);
                        root.child(user).setValue(passwordHolder);


                        Toast.makeText(ForgotpasswordActivity.this, "Upload Data Successfully", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(ForgotpasswordActivity.this, LogInScreen.class);
                        startActivity(i);
                        finish();
                }
            });
    }
}