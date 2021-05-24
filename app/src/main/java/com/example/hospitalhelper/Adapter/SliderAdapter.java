package com.example.hospitalhelper.Adapter;

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

    private Context context;
    private List<ImageSliderItem> imageSliderItems = new ArrayList<>();

    public SliderAdapter(Context context, List<ImageSliderItem> imageSliderItems) {
        this.context = context;
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
                    .load("https://firebasestorage.googleapis.com/v0/b/hospitalhelper07.appspot.com/o/ImageSlider_Images%2FBanner1.jpg?alt=media&token=b29a2006-e16e-4490-87f9-b0ff5cecf3df")
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);
        }
        else if (position == 1){
            Glide.with(viewHolder.itemView)
                    .load("https://firebasestorage.googleapis.com/v0/b/hospitalhelper07.appspot.com/o/ImageSlider_Images%2FBanner2.jpg?alt=media&token=a28c2463-f39a-472e-8a91-7b0f7af76a71")
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
                    Intent i = new Intent(context,Banner_One.class);
                    context.startActivity(i);
                }
                else if(position == 1){
                    Intent i = new Intent(context,Banner_Two.class);
                    context.startActivity(i);
                }
                else if(position == 2){
                    Intent i = new Intent(context,Banner_Three.class);
                    context.startActivity(i);
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
