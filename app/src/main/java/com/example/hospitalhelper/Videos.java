package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hospitalhelper.Adapter.VideoDataAdapter;
import com.example.hospitalhelper.Data_Holder.VideoDataHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Videos extends AppCompatActivity {

    ImageView backButton;
    RecyclerView recyclerView_view;

    ArrayList<VideoDataHolder> video_link;

    DatabaseReference mdatabaseref;
    StorageReference mstorageref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        recyclerView_view = findViewById(R.id.videosetrecycleview_croping);
        backButton = findViewById(R.id.back_button);

        recyclerView_view.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_view.setHasFixedSize(true);

        mdatabaseref = FirebaseDatabase.getInstance().getReference();
        mstorageref = FirebaseStorage.getInstance().getReference();
        video_link = new ArrayList<VideoDataHolder>();

        DoctorVideos();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void DoctorVideos() {
        final Query query = mdatabaseref.child("DoctorVideosLinks");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    VideoDataHolder datao = new VideoDataHolder();

                    datao.setLink(dataSnapshot.child("link").getValue().toString());
                    datao.setName(dataSnapshot.child("name").getValue().toString());

                    video_link.add(datao);

                }
                VideoDataAdapter dataHolder = new VideoDataAdapter(video_link,getApplicationContext());
                recyclerView_view.setAdapter(dataHolder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onBackPressed(){
        Intent i = new Intent(Videos.this,HomeScreen.class);
        startActivity(i);
        overridePendingTransition(0,0);
        finish();
    }
}