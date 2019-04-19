package com.example.user.myapplication.API;

public class ImageResponse {
    private String default_url;
    private String uploadcare_url;

    public String getDefault_url() {
        return default_url;
    }

    public void setDefault_url(String default_url) {
        this.default_url = default_url;
    }

    public String getUploadcare_url() {
        return uploadcare_url;
    }

    public void setUploadcare_url(String uploadcare_url) {
        this.uploadcare_url = uploadcare_url;
    }
}
