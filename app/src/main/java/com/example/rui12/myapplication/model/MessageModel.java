package com.example.rui12.myapplication.model;

public class MessageModel {
    private String header_url;
    private String user_name;
    private String latest_msg;

    public MessageModel(String header_url, String user_name, String latest_msg) {
        this.header_url = header_url;
        this.user_name = user_name;
        this.latest_msg = latest_msg;
    }

    public String getHeader_url() {
        return header_url;
    }

    public void setHeader_url(String header_url) {
        this.header_url = header_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getLatest_msg() {
        return latest_msg;
    }

    public void setLatest_msg(String latest_msg) {
        this.latest_msg = latest_msg;
    }
}
