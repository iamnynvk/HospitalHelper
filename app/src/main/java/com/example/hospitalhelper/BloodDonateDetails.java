package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hospitalhelper.Data_Holder.BloodDonateRequestUser;
import com.example.hospitalhelper.Data_Holder.BloodRequestUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BloodDonateDetails extends AppCompatActivity {

    EditText fullnameEt, mobileEt, emailEt, age, bloodgroupET;
    ImageView back_button;
    Button submitBtn;
    RadioGroup GenderButtonGroup;
    RadioButton GenderButton;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donate_details);

        fullnameEt = findViewById(R.id.fullname_edittext);
        mobileEt = findViewById(R.id.mobile_edittext);
        emailEt = findViewById(R.id.email_edittext);
        age = findViewById(R.id.ageB);
        bloodgroupET = findViewById(R.id.bloodgroup_edittext);
        submitBtn = findViewById(R.id.submit_button);
        GenderButtonGroup = findViewById(R.id.genderbutton_group);
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
                BloodDonateDetails();
            }
        });
    }

    private void BloodDonateDetails() {
        if (Validate()) {

            int genderselecter = GenderButtonGroup.getCheckedRadioButtonId();
            GenderButton = findViewById(genderselecter);

            final String fullname = fullnameEt.getText().toString();
            final String mobilno = mobileEt.getText().toString();
            final String email = emailEt.getText().toString();
            final String ageP = age.getText().toString();
            final String bloodgroup = bloodgroupET.getText().toString();
            final String Genderbutton = GenderButton.getText().toString();

            Query quary = FirebaseDatabase.getInstance().getReference().child("BloodDonateRequest").orderByChild("mobilno").equalTo(mobileEt.getText().toString());

            quary.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Log.e("Mobile NO snapahot","IF>");
                        mobileEt.setError("such user exist!");
                        mobileEt.requestFocus();
                        //d.dismiss();
                    } else {
                        // Storedata in Realtime Database
                        ProgressDialog dialog = new ProgressDialog(BloodDonateDetails.this);
                        dialog.setTitle("Submitting");
                        dialog.setMessage("Wait...");
                        dialog.show();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference root = db.getReference("BloodDonateRequest");


                                BloodDonateRequestUser bloodDonateRequestUser = new BloodDonateRequestUser(fullname, mobilno, email, ageP, Genderbutton, bloodgroup, user);
                                root.child(user).setValue(bloodDonateRequestUser);


                                Toast.makeText(BloodDonateDetails.this, "Requested", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(BloodDonateDetails.this,HomeScreen.class);
                                startActivity(intent);
                                finish();
                                dialog.dismiss();
                            }
                        }, 2500);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private boolean Validate() {

        boolean result = false;

        String fullname = fullnameEt.getText().toString().trim();
        String mobileno = mobileEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String ageB = age.getText().toString().trim();
        String bloodgroup = bloodgroupET.getText().toString().trim();

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
        else if(ageB.isEmpty())
        {
            age.setError("Enter Age");
            age.requestFocus();
            return false;
        }
        else if (GenderButtonGroup.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(getApplicationContext(),"Select Your Gender",Toast.LENGTH_LONG).show();
        }
        else if(bloodgroup.isEmpty())
        {
            bloodgroupET.setError("Enter bloodGroup");
            bloodgroupET.requestFocus();
            return false;
        }
        else {
            result = true;
        }
        return result;
    }


    public void onBackPressed(){
        Intent i = new Intent(BloodDonateDetails.this,HomeScreen.class);
        startActivity(i);
        finish();
    }

}