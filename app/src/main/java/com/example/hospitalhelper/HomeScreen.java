package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hospitalhelper.Adapter.SliderAdapter;
import com.example.hospitalhelper.Data_Holder.ImageSliderItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreen extends AppCompatActivity {

    TextView greetingTimeTv;
    CircleImageView profileImg;
    Button profileBtn,bloodRequestBtn,bloodDonateBtn;

    // for sliderImage
    SliderView sliderView;
    private SliderAdapter adapter;
    List<ImageSliderItem> bannerSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        sliderView = findViewById(R.id.imageSlider);
        greetingTimeTv = findViewById(R.id.greeting_time_textview);
        profileImg = findViewById(R.id.profileView);
        profileBtn = findViewById(R.id.profile_button);
        bloodRequestBtn = findViewById(R.id.blood_request_button);
        bloodDonateBtn = findViewById(R.id.blood_donate_button);

        //TextView in set the time
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        if(formattedDate.equals("00:00:00")&&formattedDate.equals("12:00:00")){
            greetingTimeTv.setText("Good Morning");
        }else{
            greetingTimeTv.setText("Good Evening");
        }

        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference reference = FirebaseFirestore.getInstance().collection("User").document(userid);


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference nood = db.getReference();
        DatabaseReference childR = nood.child("Patients").child(userid).child("profileimg");


        childR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String url = snapshot.getValue().toString();
                Picasso.get().load(url).into(profileImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Buttons Listener
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this,ProfileView.class);
                startActivity(i);
                finish();
            }
        });

        bloodRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this,BloodRequest.class);
                startActivity(i);
                finish();
            }
        });

        bloodDonateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this,BloodDonate.class);
                startActivity(i);
                finish();
            }
        });


        // Set ImageSlider
        ImageSliderSection();


    }

    private void ImageSliderSection() {

        bannerSlider = new ArrayList<>();
        ImageSliderItem sliderItem = new ImageSliderItem();

        for (int i = 0; i <= 2; i++) {
            sliderItem.setDescription("");
            sliderItem.setImageUrl("https://firebasestorage.googleapis.com/v0/b/hospitalhelper07.appspot.com/o/ImageSlider_Images%2FBanner3.jpg?alt=media&token=73b2a863-e2f3-44f4-9842-43136658bcce");
            bannerSlider.add(sliderItem);
        }

        adapter = new SliderAdapter(HomeScreen.this, bannerSlider);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(2);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
    }
}