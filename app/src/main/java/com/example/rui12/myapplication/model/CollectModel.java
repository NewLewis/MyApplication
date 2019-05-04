package com.example.rui12.myapplication.model;

import cn.bmob.v3.BmobObject;

public class CollectModel extends BmobObject {
    private String username;
    private String dreamID;

    public CollectModel(String username, String dreamID) {
        this.username = username;
        this.dreamID = dreamID;
    }

    public CollectModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDreamID() {
        return dreamID;
    }

    public void setDreamID(String dreamID) {
        this.dreamID = dreamID;
    }
}
