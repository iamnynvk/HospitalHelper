package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.ktx.Firebase;

import java.util.concurrent.TimeUnit;

public class ForgotpasswordOTPVerification extends AppCompatActivity {

    ImageView backButton;
    EditText inputcode1,inputcode2,inputcode3,inputcode4,inputcode5,inputcode6;
    Button buttonVerify;
    TextView resendOtp,txtMobileno;
    ProgressDialog mprogress;
    String verificationId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword_o_t_p_verification);

        mprogress = new ProgressDialog(this);
        backButton = findViewById(R.id.back_button_for);
        resendOtp = findViewById(R.id.buttonResentOtp);
        txtMobileno = findViewById(R.id.txtMobilenumber);
        buttonVerify = findViewById(R.id.buttonGetOtp);

        inputcode1 = findViewById(R.id.inputcode1);
        inputcode2 = findViewById(R.id.inputcode2);
        inputcode3 = findViewById(R.id.inputcode3);
        inputcode4 = findViewById(R.id.inputcode4);
        inputcode5 = findViewById(R.id.inputcode5);
        inputcode6 = findViewById(R.id.inputcode6);

        // Action Here
        txtMobileno.setText(String.format(
                "+91-%s", getIntent().getStringExtra("mobile")
        ));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setupOtpInputs();

        verificationId = getIntent().getStringExtra("verificationId");

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputcode1.getText().toString().trim().isEmpty()
                || inputcode2.getText().toString().trim().isEmpty()
                || inputcode3.getText().toString().trim().isEmpty()
                || inputcode4.getText().toString().trim().isEmpty()
                || inputcode5.getText().toString().trim().isEmpty()
                || inputcode6.getText().toString().trim().isEmpty()){

                    Toast.makeText(ForgotpasswordOTPVerification.this,"Please Enter Valid Code",Toast.LENGTH_SHORT).show();
                    return;
                }

                String code =
                        inputcode1.getText().toString() +
                                inputcode2.getText().toString() +
                                inputcode3.getText().toString() +
                                inputcode4.getText().toString() +
                                inputcode5.getText().toString() +
                                inputcode6.getText().toString();

                if (verificationId != null){
                    mprogress.setMessage("Please Wait...");
                    mprogress.show();
                    buttonVerify.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    mprogress.dismiss();
                                    buttonVerify.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()){
                                        startActivity(new Intent(ForgotpasswordOTPVerification.this,ForgotpasswordActivity.class));
                                        finish();
                                    }else {
                                        Toast.makeText(ForgotpasswordOTPVerification.this,"The varification code entered was invalid!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        ForgotpasswordOTPVerification.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(ForgotpasswordOTPVerification.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newverificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId = newverificationId;
                                Toast.makeText(ForgotpasswordOTPVerification.this,"OTP sent",Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                mprogress.dismiss();
            }
        });
    }
    public void onBackPressed(){
        Intent i = new Intent(ForgotpasswordOTPVerification.this,ForgotPasswordOTPSend.class);
        startActivity(i);
        finish();
    }

    private void setupOtpInputs(){
        inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}