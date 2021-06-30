package com.example.hospitalhelper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalhelper.Data_Holder.VideoDataHolder;
import com.example.hospitalhelper.R;
import com.example.hospitalhelper.VideoFullScreen;

import java.util.ArrayList;

public class VideoDataAdapter extends RecyclerView.Adapter<VideoDataAdapter.ViewHolder> {

    ArrayList<VideoDataHolder> videolink;
    Context context;

    public VideoDataAdapter(ArrayList<VideoDataHolder> videolink, Context context) {
        this.videolink = videolink;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_setter_layout,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoDataHolder current = videolink.get(position);

        holder.webView.loadUrl(current.getLink());
        holder.videoText.setText(videolink.get(position).getName());

        /*holder.bar.setVisibility(View.VISIBLE);*/
        holder.fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, VideoFullScreen.class);
                i.putExtra("link",current.getLink());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videolink.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        WebView webView;
        ImageView fullscreen;
        TextView videoText;

        public ViewHolder(@NonNull View itemView,Context cn) {
            super(itemView);

            context = cn;
            webView = itemView.findViewById(R.id.webview);
            fullscreen = itemView.findViewById(R.id.fullscreenbutton);
            videoText = itemView.findViewById(R.id.videoText);
            /*bar = itemView.findViewById(R.id.progress);*/
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new WebChromeClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
    }
}
