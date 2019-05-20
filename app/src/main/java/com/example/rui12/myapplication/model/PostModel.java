package com.example.rui12.myapplication.model;

public class PostModel {
    private String dream_name;
    private String publish_time;
    private int dream_status;
    public final int Success = 1;
    public final int Fail = -1;
    public final int Process = 0;

    public PostModel(){

    }

    public PostModel(String title, String publish_time, int status) {
        this.dream_name = title;
        this.publish_time = publish_time;
        this.dream_status = status;
    }

    public String getDream_name() {
        return dream_name;
    }

    public void setDream_name(String dream_name) {
        this.dream_name = dream_name;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public int getDream_status() {
        return dream_status;
    }

    public void setDream_status(int dream_status) {
        this.dream_status = dream_status;
    }
}
