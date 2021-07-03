package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospitalhelper.Adapter.DoctorListAdapter;
import com.example.hospitalhelper.Adapter.SliderAdapter;
import com.example.hospitalhelper.Data_Holder.DoctorListHolder;
import com.example.hospitalhelper.Data_Holder.EditProfileData;
import com.example.hospitalhelper.Data_Holder.ImageSliderItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView greetingTime,userName,verify_email_tv;
    Button verify_email_button;
    RelativeLayout verifyemailrelative;
    CircleImageView profileImg;
    Button profileButton,bloodRequestButton,bloodDonateButton;

    // for sliderImage
    SliderView sliderView;
    private SliderAdapter adapter;
    List<ImageSliderItem> bannerSlider;

    //DrawerLayout set
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    // Firebase
    FirebaseAuth mAuth;

    // Backpressed
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        sliderView = findViewById(R.id.imageSlider);
        greetingTime = findViewById(R.id.greeting_time_textview);
        profileImg = findViewById(R.id.profileView);
        profileButton = findViewById(R.id.profile_button);
        bloodRequestButton = findViewById(R.id.blood_request_button);
        bloodDonateButton = findViewById(R.id.blood_donate_button);
        userName = findViewById(R.id.username_textview);

        // verify Email's
        verify_email_tv = findViewById(R.id.verifyemailtv);
        verify_email_button = findViewById(R.id.verifyemailbutton);
        verifyemailrelative = findViewById(R.id.verifyemailrelative);

        //Drawerlayout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        SetUpToolbar();

        //Action Here
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this,UserProfile.class);
                startActivity(i);
                overridePendingTransition(0,0);
                finish();
            }
        });

        bloodRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this,BloodRequest.class);
                startActivity(i);
                overridePendingTransition(0,0);
                finish();
            }
        });

        bloodDonateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this,BloodDonate.class);
                startActivity(i);
                overridePendingTransition(0,0);
                finish();
            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this, WatchProfileImage.class);
                startActivity(i);
                overridePendingTransition(0,0);
                finish();
            }
        });

        // Set ImageSlider
        ImageSliderSection();

        //Greeting Textview Set
        GreetingTextSet();

        // Access Data From Firebase
        ProfileAndData();

        // Email Verification Send from Here...
        mAuth = FirebaseAuth.getInstance();
        VerifyEmailID();

        //DrawerNavigation
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void ProfileAndData() {

        //UserID
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //FirebaseDatabase Connection
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference nood = db.getReference();
        DatabaseReference childR = nood.child("ProfileEditData").child(userid);

        childR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Set the User name in TextView
                String UserName = String.valueOf(snapshot.child("firstname").getValue());
                userName.setText(UserName);

                //set profile in circleImageview
                String url = snapshot.child("profileimg").getValue().toString();
                Picasso.get().load(url).into(profileImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void GreetingTextSet() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            greetingTime.setText("Good Morning");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            greetingTime.setText("Good Afternoon");
        }else if(timeOfDay >= 16 && timeOfDay < 24) {
            greetingTime.setText("Good Evening");
        }
    }

    private void VerifyEmailID() {
        final FirebaseUser verifybutton = mAuth.getCurrentUser();
        if (!verifybutton.isEmailVerified()){
            verify_email_tv.setVisibility(View.VISIBLE);
            verify_email_button.setVisibility(View.VISIBLE);

            verify_email_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    verifybutton.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(HomeScreen.this,"Varification Email has been send",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Email not send !","===>"+ e.getMessage());
                        }
                    });
                }
            });
        }
    }

    private void ImageSliderSection() {

        bannerSlider = new ArrayList<>();
        ImageSliderItem sliderItem = new ImageSliderItem();

        for (int i = 0; i <= 3; i++) {
            sliderItem.setDescription("");
            sliderItem.setImageUrl("https://firebasestorage.googleapis.com/v0/b/hospitalhelper07.appspot.com/o/ImageSlider_Images%2Fambulance.jpg?alt=media&token=b941bfef-e18e-4725-b844-a3c03158adcf");
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

    private void SetUpToolbar() {
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(HomeScreen.this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.title_color));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                return true;

            case R.id.profile:
                Intent i2 = new Intent(getApplicationContext(), UserProfile.class);
                startActivity(i2);
                overridePendingTransition(0,0);
                finish();
                return true;

            case R.id.doctor:
                Intent i3 = new Intent(getApplicationContext(), Doctor.class);
                startActivity(i3);
                overridePendingTransition(0,0);
                finish();
                return true;

            case R.id.blood_request:
                Intent i4 = new Intent(getApplicationContext(), BloodRequest.class);
                startActivity(i4);
                overridePendingTransition(0,0);
                finish();
                return true;

            case R.id.blood_donate:
                Intent i5 = new Intent(getApplicationContext(),BloodDonate.class);
                startActivity(i5);
                overridePendingTransition(0,0);
                finish();
                return true;

            case R.id.videos:
                Intent i7 = new Intent(getApplicationContext(),Videos.class);
                startActivity(i7);
                overridePendingTransition(0,0);
                finish();
                return true;

            case R.id.contact_us:
                Intent i8 = new Intent(getApplicationContext(),ContactUs.class);
                startActivity(i8);
                overridePendingTransition(0,0);
                finish();
                return true;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        counter++;
        if (counter == 1){
            Toast.makeText(this,"double backpress exit the apps",Toast.LENGTH_LONG).show();
        }
        else if (counter == 3){
            super.onBackPressed();
        }
    }
}