package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hospitalhelper.Data_Holder.BloodRequestUser;
import com.example.hospitalhelper.Data_Holder.ContactUsUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ContactUs extends AppCompatActivity {

    EditText fullnameEt, mobileEt, emailEt, message;
    ImageView back_button;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        fullnameEt = findViewById(R.id.fullname_edittext);
        mobileEt = findViewById(R.id.mobile_edittext);
        emailEt = findViewById(R.id.email_edittext);
        message = findViewById(R.id.message);
        submitBtn = findViewById(R.id.submit_button);
        back_button = findViewById(R.id.back_button);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactUs();
            }
        });
    }

    private void ContactUs()
    {

        if (Validate()) {
            final String fullname = fullnameEt.getText().toString();
            final String mobilno = mobileEt.getText().toString();
            final String email = emailEt.getText().toString();
            final String msg = message.getText().toString();

            ProgressDialog dialog = new ProgressDialog(ContactUs.this);
            dialog.setTitle("Submit");
            dialog.setMessage("Wait...");
            dialog.show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Storedata in Realtime Database
                    //String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference root = db.getReference("ContactUs");


                    ContactUsUser contactUsUser = new ContactUsUser(fullname, mobilno, email, msg, user);
                    root.child(user).setValue(contactUsUser);

                    Toast.makeText(ContactUs.this, "Submitted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ContactUs.this,HomeScreen.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            }, 2500);

        }

    }

    private boolean Validate() {
        boolean result = false;

        String fullname = fullnameEt.getText().toString().trim();
        String mobileno = mobileEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String msg = message.getText().toString().trim();

        if(fullname.isEmpty())
        {
            fullnameEt.setError("Please Enter Fullname");
            fullnameEt.requestFocus();
            return false;
        }
        else if(mobileno.isEmpty())
        {
            mobileEt.setError("Enter MobileNo");
            mobileEt.requestFocus();
            return false;
        }
        else if (mobileno.length() == 0 || mobileno.length() == 1 || mobileno.length() == 2 || mobileno.length() == 3 || mobileno.length() == 4 || mobileno.length() == 5 || mobileno.length() == 6 || mobileno.length() == 7 || mobileno.length() == 8 || mobileno.length() == 9)
        {
            mobileEt.setError("Enter Valid MobileNo");
            mobileEt.requestFocus();
            return false;
        }
        else if(email.isEmpty())
        {
            emailEt.setError("Email is required");
            emailEt.requestFocus();
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEt.setError("Provide valid email");
            emailEt.requestFocus();
            return false;
        }
        else if(msg.isEmpty())
        {
            message.setError("Message is required");
            message.requestFocus();
            return false;
        }
        else {
            result = true;
        }
        return result;
    }

    public void onBackPressed(){
        Intent i = new Intent(ContactUs.this,HomeScreen.class);
        startActivity(i);
        finish();
    }
}
