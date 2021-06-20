package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class WatchProfileImage extends AppCompatActivity {

    ImageView backButton;
    ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_profile_image);

        backButton = findViewById(R.id.back_button);
        userImage = findViewById(R.id.user_image_full);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DataShow();

    }

    private void DataShow() {
        // get UserID
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //FirebaseDatabase Connection
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        DatabaseReference childR = reference.child("ProfileEditData").child(userid);

        childR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //set profile in circleImageview
                String url = snapshot.child("profileimg").getValue().toString();
                /*Picasso.get().load(url).into(userImage);*/
                Glide.with(WatchProfileImage.this).load(url).into(userImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onBackPressed(){
        Intent i = new Intent(WatchProfileImage.this,HomeScreen.class);
        startActivity(i);
        finish();
    }
}