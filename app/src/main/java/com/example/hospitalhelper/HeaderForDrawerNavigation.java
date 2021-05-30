package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HeaderForDrawerNavigation extends AppCompatActivity {

    TextView username_header,mobileno_header;
    CircleImageView userimage_header;
    //FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_for_drawer_navigation);

        username_header = findViewById(R.id.username_for_header);
        mobileno_header = findViewById(R.id.mobileno_for_header);
        userimage_header = findViewById(R.id.userprofile_for_header);

        ProfileAndData();
    }

    private void ProfileAndData() {
        //UserID
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //FirebaseDatabase Connection
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference nood = db.getReference();
        DatabaseReference childR = nood.child("Patients").child(userid);

        childR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Set the User name in TextView
                String UserNameHeader = String.valueOf(snapshot.child("firstname").getValue());
                username_header.setText(UserNameHeader);

                //Set the Mobileno in TextView
                String Mobile_user = String.valueOf(snapshot.child("mobileno").getValue());
                mobileno_header.setText(Mobile_user);

                //set profile in circleImageview
                String url_profile = snapshot.child("profileimg").getValue().toString();
                Picasso.get().load(url_profile).into(userimage_header);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}