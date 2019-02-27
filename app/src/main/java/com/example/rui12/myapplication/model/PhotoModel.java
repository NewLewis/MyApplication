package com.example.rui12.myapplication.model;

public class PhotoModel {
    private String url;
    private int localRes;

    public PhotoModel(String url, int localRes) {
        this.url = url;
        this.localRes = localRes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLocalRes() {
        return localRes;
    }

    public void setLocalRes(int localRes) {
        this.localRes = localRes;
    }
}
