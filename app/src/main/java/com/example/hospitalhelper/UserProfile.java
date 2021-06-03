package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {

    Button Editprofile,Logout;
    ImageView BackButton,profileImage;
    ProgressDialog dialog;

    TextView username,mobileid,firstname,lastname,mobileno,birthdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Editprofile = findViewById(R.id.edit_profile_button);
        Logout = findViewById(R.id.logout_button);
        BackButton = findViewById(R.id.back_button_profile);
        dialog = new ProgressDialog(this);

        profileImage = findViewById(R.id.userphoto_profile);
        username = findViewById(R.id.username_profile);
        mobileid = findViewById(R.id.mobileno_profile);
        firstname = findViewById(R.id.firstname_profile_text);
        lastname = findViewById(R.id.lastname_profile_text);
        mobileno = findViewById(R.id.mobile_profile_text);
        birthdate = findViewById(R.id.birthdate_profile_text);

        Editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this,ProfileEdit.class));
                finish();
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.setIsLogin(UserProfile.this,"");
                startActivity(new Intent(UserProfile.this,LogInScreen.class));
                finish();
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DataShow();

    }

    private void DataShow() {
        //UserID
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //FirebaseDatabase Connection
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        DatabaseReference childR = reference.child("ProfileEditData").child(userid);

        childR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Set the User name in TextView
                String UserName = String.valueOf(snapshot.child("firstname").getValue());
                username.setText(UserName);

                String MobileID = String.valueOf(snapshot.child("mobileno").getValue());
                mobileid.setText(MobileID);

                String FirstName = String.valueOf(snapshot.child("firstname").getValue());
                firstname.setText(FirstName);

                String LastName = String.valueOf(snapshot.child("lastname").getValue());
                lastname.setText(LastName);

                String MobileNo = String.valueOf(snapshot.child("mobileno").getValue());
                mobileno.setText(MobileNo);

                String BirthDate = String.valueOf(snapshot.child("birthdate").getValue());
                birthdate.setText(BirthDate);

                //set profile in circleImageview
                String url = snapshot.child("profileimg").getValue().toString();
                Picasso.get().load(url).into(profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onBackPressed(){
        Intent i = new Intent(UserProfile.this,HomeScreen.class);
        startActivity(i);
        finish();
    }
}