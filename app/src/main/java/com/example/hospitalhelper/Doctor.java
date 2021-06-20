package com.example.hospitalhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.hospitalhelper.Adapter.DoctorListAdapter;
import com.example.hospitalhelper.Data_Holder.DoctorListHolder;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Doctor extends AppCompatActivity {

    RecyclerView recyclerview;
    DoctorListAdapter adapter;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        backButton = findViewById(R.id.back_button);
        recyclerview = findViewById(R.id.recyclerView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        // Init Firebase Recycler options
        FirebaseRecyclerOptions<DoctorListHolder> options =
                new FirebaseRecyclerOptions.Builder<DoctorListHolder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctor_List"), DoctorListHolder.class)
                        .build();

        adapter = new DoctorListAdapter(options);
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void onBackPressed(){
        Intent i = new Intent(Doctor.this,HomeScreen.class);
        startActivity(i);
        finish();
    }
}