package com.devworm.edufree;

public class modeBlog {
    private String witeBAlog,title,ImageUrl;

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
