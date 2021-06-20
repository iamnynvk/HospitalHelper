package com.example.hospitalhelper.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hospitalhelper.Data_Holder.DoctorListHolder;
import com.example.hospitalhelper.Doctor_Detail;
import com.example.hospitalhelper.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorListAdapter extends FirebaseRecyclerAdapter<DoctorListHolder,DoctorListAdapter.myviewholder> {

    public DoctorListAdapter(@NonNull FirebaseRecyclerOptions<DoctorListHolder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder myviewholder, int i, @NonNull DoctorListHolder doctorListHolder) {

        myviewholder.doctorName.setText(doctorListHolder.getDoctorname());
        myviewholder.doctorQualification.setText(doctorListHolder.getQualification());
        myviewholder.doctorType.setText(doctorListHolder.getType());
        myviewholder.doctorTime.setText(doctorListHolder.getTime());
        myviewholder.doctorDays.setText(doctorListHolder.getDays());

        Glide.with(myviewholder.doctorImage.getContext()).load(doctorListHolder.getDoctorurl()).into(myviewholder.doctorImage);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_view,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        CardView cardview;
        RelativeLayout doctorRelative;
        CircleImageView doctorImage;
        TextView doctorName,doctorQualification,doctorType,doctorTime,doctorDays;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            cardview = (CardView)itemView.findViewById(R.id.cardView);
            doctorRelative = (RelativeLayout)itemView.findViewById(R.id.doctor_list_relative);
            doctorImage = (CircleImageView)itemView.findViewById(R.id.doctor_image);
            doctorName = (TextView)itemView.findViewById(R.id.doctor_name);
            doctorQualification = (TextView)itemView.findViewById(R.id.doctor_qualification);
            doctorType = (TextView)itemView.findViewById(R.id.doctor_type);
            doctorTime = (TextView)itemView.findViewById(R.id.doctor_time);
            doctorDays = (TextView)itemView.findViewById(R.id.doctor_day_available);
        }
    }
}
