package com.example.hospitalhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VideoFullScreen extends AppCompatActivity {
    WebView fullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_full_screen);

        fullscreen = findViewById(R.id.fullscreenvideo);

        String link = getIntent().getStringExtra("link");
        fullscreen.loadUrl(link);

        fullscreen.setWebViewClient(new WebViewClient());
        fullscreen.setWebChromeClient(new WebChromeClient());
        fullscreen.getSettings().setJavaScriptEnabled(true);
    }
}