package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordOTPSend extends AppCompatActivity {

    ImageView backButton;
    EditText inputMobileNo;
    Button buttonGetOTP;
    ProgressDialog mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_o_t_p_send);

        backButton = findViewById(R.id.back_button);
        inputMobileNo = findViewById(R.id.inputmobile);
        buttonGetOTP = findViewById(R.id.buttonGetOtp);
        mprogress = new ProgressDialog(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        buttonGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobile = inputMobileNo.getText().toString().trim();

                if (mobile.isEmpty() || mobile.length() < 10 || mobile.startsWith("1") || mobile.startsWith("2") ||  mobile.startsWith("3") ||
                        mobile.startsWith("4") ||  mobile.startsWith("5") || mobile.startsWith("0")){


                    inputMobileNo.setError("Enter Valid Mobile No!");
                    inputMobileNo.requestFocus();
                    return;
                }
                /*progress_bar.setVisibility(View.VISIBLE);*/
                mprogress.setMessage("Please wait check in Server");
                mprogress.show();

                // Check Weather user exist or not in database
                Query checkUser = FirebaseDatabase.getInstance().getReference().child("Patients").orderByChild("mobileno").equalTo(mobile);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            inputMobileNo.setError(null);

                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    "+91" + inputMobileNo.getText().toString(),
                                    60,
                                    TimeUnit.SECONDS,
                                    ForgotPasswordOTPSend.this,
                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                            mprogress.show();
                                            buttonGetOTP.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                            mprogress.dismiss();
                                            buttonGetOTP.setVisibility(View.VISIBLE);
                                            Toast.makeText(ForgotPasswordOTPSend.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                            mprogress.setMessage("Please Wait...");
                                            mprogress.show();
                                            buttonGetOTP.setVisibility(View.GONE);
                                            Intent i = new Intent(getApplicationContext(),ForgotpasswordOTPVerification.class);
                                            i.putExtra("mobile",inputMobileNo.getText().toString());
                                            i.putExtra("verificationId",verificationId);
                                            startActivity(i);
                                            overridePendingTransition(0,0);
                                            finish();
                                        }
                                    }
                            );
                            mprogress.dismiss();
                        }
                        else {
                            mprogress.dismiss();
                            inputMobileNo.setError("No such user exist!");
                            inputMobileNo.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    public void onBackPressed(){
        Intent i = new Intent(ForgotPasswordOTPSend.this,LogInScreen.class);
        startActivity(i);
        overridePendingTransition(0,0);
        finish();
    }
}