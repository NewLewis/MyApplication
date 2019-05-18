package com.example.rui12.myapplication.model;

import cn.bmob.v3.BmobObject;

public class ReviewModel extends BmobObject {
    private String username;
    private String toSb;
    private String dreamID;
    private Integer num_of_laud;
    private String content;

    public ReviewModel(String username, String toSb, String dreamID, Integer num_of_laud, String content) {
        this.username = username;
        this.toSb = toSb;
        this.dreamID = dreamID;
        this.num_of_laud = num_of_laud;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToSb() {
        return toSb;
    }

    public void setToSb(String toSb) {
        this.toSb = toSb;
    }

    public String getDreamID() {
        return dreamID;
    }

    public void setDreamID(String dreamID) {
        this.dreamID = dreamID;
    }

    public Integer getNum_of_laud() {
        return num_of_laud;
    }

    public void setNum_of_laud(Integer num_of_laud) {
        this.num_of_laud = num_of_laud;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
