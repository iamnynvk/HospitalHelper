package com.example.hospitalhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.hospitalhelper.Adapter.SliderAdapter;
import com.example.hospitalhelper.Data_Holder.ImageSliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    // for sliderImage
    SliderView sliderView;
    private SliderAdapter adapter;
    List<ImageSliderItem> bannerSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        sliderView = findViewById(R.id.imageSlider);
        // Test Git
        //A host-to-network configuration is analogous to connecting a computer to a local area network. This type provides access to an enterprise network, such as an intranet. This may be employed for telecommuting workers who need access to private resources, or to enable a mobile worker to access important tools without exposing them to the public Internet.

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