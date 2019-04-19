package com.example.user.myapplication.API;

public class EventResponse {
    private int id;
    private String name;
    private String url;
    private ImageResponse poster_image;

    public ImageResponse getPoster_image() {
        return poster_image;
    }

    public void setPoster_image(ImageResponse poster_image) {
        this.poster_image = poster_image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
