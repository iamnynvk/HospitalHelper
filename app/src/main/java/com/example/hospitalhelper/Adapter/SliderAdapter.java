package com.example.hospitalhelper.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.hospitalhelper.Banner_One;
import com.example.hospitalhelper.Banner_Three;
import com.example.hospitalhelper.Banner_Two;
import com.example.hospitalhelper.Data_Holder.ImageSliderItem;
import com.example.hospitalhelper.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Activity activity;
    private List<ImageSliderItem> imageSliderItems = new ArrayList<>();

    public SliderAdapter(Activity activity, List<ImageSliderItem> imageSliderItems) {
        this.activity = activity;
        this.imageSliderItems = imageSliderItems;
    }

    public void deleteItem(int position){
        this.imageSliderItems.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item,null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapter.SliderAdapterVH viewHolder, int position) {
        ImageSliderItem imageSliderItem = imageSliderItems.get(position);

        viewHolder.textViewDescription.setText(imageSliderItem.getDescription());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);

        if (position == 0) {
            Glide.with(viewHolder.itemView)
                    .load("https://firebasestorage.googleapis.com/v0/b/hospitalhelper07.appspot.com/o/ImageSlider_Images%2Fvaccine.jpg?alt=media&token=564b45a6-f175-4d0d-8e9b-d341c62542a0")
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);
        }
        else if (position == 1){
            Glide.with(viewHolder.itemView)
                    .load("https://firebasestorage.googleapis.com/v0/b/hospitalhelper07.appspot.com/o/ImageSlider_Images%2Fhospitalbeds.jpg?alt=media&token=bd7ca0b5-4e31-4bbf-8fd6-8886428c3c2d")
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);
        }
        else if (position == 2){
            Glide.with(viewHolder.itemView)
                    .load("https://firebasestorage.googleapis.com/v0/b/hospitalhelper07.appspot.com/o/ImageSlider_Images%2Ffood.jpg?alt=media&token=5505e481-5320-4f88-a882-5df7c2b71f30")
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);
        }
        else {
            Glide.with(viewHolder.itemView)
                    .load(imageSliderItem.getImageUrl())
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0){
                    Intent i = new Intent(activity,Banner_One.class);
                    activity.startActivity(i);
                }
                else if(position == 1){
                    Intent i = new Intent(activity,Banner_Two.class);
                    activity.startActivity(i);
                }
                else if(position == 2){
                    Intent i = new Intent(activity,Banner_Three.class);
                    activity.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getCount() {
        return imageSliderItems.size();
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
