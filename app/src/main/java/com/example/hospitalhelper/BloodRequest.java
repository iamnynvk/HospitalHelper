package com.example.hospitalhelper;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hospitalhelper.Data_Holder.BloodRequestUser;
import com.example.hospitalhelper.Data_Holder.NewUserHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BloodRequest extends AppCompatActivity {
    EditText fullnameEt, mobileEt, emailEt, age, bloodgroupET;
    ImageView back_button;
    Button submitBtn;
    RadioGroup GenderButtonGroup;
    RadioButton GenderButton;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);

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
                BloodRequestData();
            }
        });
    }

    private void BloodRequestData() {
        if (Validate()) {

            int genderselecter = GenderButtonGroup.getCheckedRadioButtonId();
            GenderButton = findViewById(genderselecter);

            final String fullname = fullnameEt.getText().toString();
            final String mobilno = mobileEt.getText().toString();
            final String email = emailEt.getText().toString();
            final String ageP = age.getText().toString();
            final String bloodgroup = bloodgroupET.getText().toString();
            final String Genderbutton = GenderButton.getText().toString();

            Query quary = FirebaseDatabase.getInstance().getReference().child("BloodRequest").orderByChild("mobilno").equalTo(mobileEt.getText().toString());

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
                        ProgressDialog dialog = new ProgressDialog(BloodRequest.this);
                        dialog.setTitle("Submit");
                        dialog.show();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.setMessage("Wait...");
                                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference root = db.getReference("BloodRequest");


                                BloodRequestUser bloodRequestUser = new BloodRequestUser(fullname, mobilno, email, ageP, Genderbutton, bloodgroup, user);
                                root.child(user).setValue(bloodRequestUser);


                                Toast.makeText(BloodRequest.this, "Requested", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(BloodRequest.this,HomeScreen.class);
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

            if (fullname.length() == 0) {
                Toast.makeText(getApplicationContext(), "Please Enter Fullname", Toast.LENGTH_SHORT).show();
            }
            else if (mobileno.length() == 0 || mobileno.length() == 1 || mobileno.length() == 2 || mobileno.length() == 3 || mobileno.length() == 4 || mobileno.length() == 5 || mobileno.length() == 6 || mobileno.length() == 7 || mobileno.length() == 8 || mobileno.length() == 9) {
                Toast.makeText(getApplicationContext(), "Please Enter Valid Mobile No", Toast.LENGTH_SHORT).show();
            }
            else if (email.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter Email address ", Toast.LENGTH_SHORT).show();
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getApplicationContext(), "Please Enter Valid Email address", Toast.LENGTH_SHORT).show();
            }
            else if(ageB.length()==0){
                Toast.makeText(getApplicationContext(), "Please Enter Age", Toast.LENGTH_SHORT).show();
            }
            else if (GenderButtonGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_SHORT).show();
            }
            else if(bloodgroup.length()==0){
                Toast.makeText(getApplicationContext(), "Please Enter Blood Group ", Toast.LENGTH_SHORT).show();
            }
            else {
                result = true;
            }
            return result;
        }

    public void onBackPressed(){
        Intent i = new Intent(BloodRequest.this,HomeScreen.class);
        startActivity(i);
        finish();
    }
}

