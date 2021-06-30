package com.example.hospitalhelper.Data_Holder;

import android.content.Context;

import java.util.ArrayList;

public class VideoDataHolder {
    String link,name;

    public VideoDataHolder(String link, String name) {
        this.link = link;
        this.name = name;
    }

    public VideoDataHolder() {

    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
