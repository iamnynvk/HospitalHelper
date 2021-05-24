package com.example.hospitalhelper.Data_Holder;

public class ImageSliderItem {
    private String description;
    private String imageUrl;
    private int imageID;

    public ImageSliderItem(String description, String imageUrl, int imageID) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.imageID = imageID;
    }

    public ImageSliderItem() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
