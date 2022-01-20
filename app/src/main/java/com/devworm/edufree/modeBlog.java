package com.devworm.edufree;

import com.google.firebase.Timestamp;

public class modeBlog {
    private String witeBAlog,title,ImageUrl,Id;
    Timestamp timestamp;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public modeBlog(String witeBAlog, String title, String imageUrl, String id, Timestamp timestamp) {
        this.witeBAlog = witeBAlog;
        this.title = title;
        ImageUrl = imageUrl;
        Id = id;
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public modeBlog(String witeBAlog, String title, String imageUrl, Timestamp timestamp) {
        this.witeBAlog = witeBAlog;
        this.title = title;
        ImageUrl = imageUrl;
        this.timestamp = timestamp;
    }

    public modeBlog() {
    }

    public String getWiteBAlog() {
        return witeBAlog;
    }

    public void setWiteBAlog(String witeBAlog) {
        this.witeBAlog = witeBAlog;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public modeBlog(String witeBAlog, String title, String imageUrl) {
        this.witeBAlog = witeBAlog;
        this.title = title;
        ImageUrl = imageUrl;
    }
}
