package com.ilyjava.android.searchyourbook.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Created by Никита on 30.05.2018.
 */

public class Item {

    @SerializedName("volumeInfo")
    @Expose
    private VolumeInfo volumeInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    class ImageLinks {

        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

    }

    class VolumeInfo {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("authors")
        @Expose
        private ArrayList<String> authors = null;
        @SerializedName("imageLinks")
        @Expose
        private ImageLinks imageLinks;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<String> getAuthors() {
            return authors;
        }

        public ImageLinks getImageLinks() {
            return imageLinks;
        }



    }
}
