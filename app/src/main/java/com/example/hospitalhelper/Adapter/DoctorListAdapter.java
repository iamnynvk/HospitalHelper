package com.example.hospitalhelper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {

    private static final String TAG = "AddProduct_RecycleAdapter";

    Context mcontext;
    List<DoctorListHolder> DoctorList;

    public DoctorListAdapter(Context mcontext, List<DoctorListHolder> doctorList) {
        this.mcontext = mcontext;
        DoctorList = doctorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorListAdapter.ViewHolder holder, int position) {
        holder.doctorName.setText(DoctorList.get(position).getDoctorname());
        holder.doctorQualification.setText(DoctorList.get(position).getQualification());
        holder.doctorType.setText(DoctorList.get(position).getType());
        holder.doctorTime.setText(DoctorList.get(position).getTime());

        /* Picasso is Slower than Glide i don't know why?
        Picasso.get().load(DoctorList.get(position).getDoctorurl())
                .into(holder.doctorImage, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });*/

        // Glide is Faster Fatching Image on Server's
        Glide.with(holder.doctorImage.getContext()).load(DoctorList.get(position).getDoctorurl()).into(holder.doctorImage);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(v.getContext(), Doctor_Detail.class);

                in.putExtra("DoctorImage",DoctorList.get(position).getDoctorurl());
                in.putExtra("DoctorName",DoctorList.get(position).getDoctorname());
                in.putExtra("DoctorQualification",DoctorList.get(position).getQualification());
                in.putExtra("DoctorType",DoctorList.get(position).getType());
                in.putExtra("DoctorTime",DoctorList.get(position).getTime());

                v.getContext().startActivity(in);

            }
        });

    }

    @Override
    public int getItemCount() {
        return DoctorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardview;
        RelativeLayout doctorRelative;
        CircleImageView doctorImage;
        TextView doctorName,doctorQualification,doctorType,doctorTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardview = (CardView)itemView.findViewById(R.id.cardView);
            doctorRelative = (RelativeLayout)itemView.findViewById(R.id.doctor_list_relative);
            doctorImage = (CircleImageView)itemView.findViewById(R.id.doctor_image);
            doctorName = (TextView)itemView.findViewById(R.id.doctor_name);
            doctorQualification = (TextView)itemView.findViewById(R.id.doctor_qualification);
            doctorType = (TextView)itemView.findViewById(R.id.doctor_type);
            doctorTime = (TextView)itemView.findViewById(R.id.doctor_time);
        }
    }
}
