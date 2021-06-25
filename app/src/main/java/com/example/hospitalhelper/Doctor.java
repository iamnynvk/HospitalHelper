package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.hospitalhelper.Adapter.DoctorListAdapter;
import com.example.hospitalhelper.Data_Holder.DoctorListHolder;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Doctor extends AppCompatActivity {

    RecyclerView recyclerview;
    private DoctorListAdapter DoctorAdapter;
    ImageView backButton;
    private ArrayList<DoctorListHolder> DoctorData;
    Context context;

    DatabaseReference mdatabaseref;
    StorageReference mstorageref;

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

        // Product Set Layout
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setHasFixedSize(true);

        mdatabaseref = FirebaseDatabase.getInstance().getReference();
        mstorageref = FirebaseStorage.getInstance().getReference();

        DoctorData = new ArrayList<>();

        product();

        /*recyclerview.setLayoutManager(new LinearLayoutManager(this));
        // Init Firebase Recycler options
        FirebaseRecyclerOptions<DoctorListHolder> options =
                new FirebaseRecyclerOptions.Builder<DoctorListHolder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctor_List"), DoctorListHolder.class)
                        .build();

        adapter = new DoctorListAdapter(options);
        recyclerview.setAdapter(adapter);*/
    }

    private void product() {
        final Query query = mdatabaseref.child("Doctor_List");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DoctorListHolder Holder = new DoctorListHolder();
                    Holder.setDoctorurl(dataSnapshot.child("doctorurl").getValue().toString());
                    Holder.setDoctorname(dataSnapshot.child("doctorname").getValue().toString());
                    Holder.setQualification(dataSnapshot.child("qualification").getValue().toString());
                    Holder.setType(dataSnapshot.child("type").getValue().toString());
                    Holder.setTime(dataSnapshot.child("time").getValue().toString());

                    DoctorData.add(Holder);
                }
                GridLayoutManager manager = new GridLayoutManager(context,1);
                recyclerview.setLayoutManager(manager);
                DoctorAdapter = new DoctorListAdapter(context,DoctorData);
                recyclerview.setAdapter(DoctorAdapter);
                DoctorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onBackPressed(){
        Intent i = new Intent(Doctor.this,HomeScreen.class);
        startActivity(i);
        overridePendingTransition(0,0);
        finish();
    }
}