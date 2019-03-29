package com.example.rui12.myapplication.model;

import com.example.rui12.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobObject;

public class DreamModel1 extends BmobObject {
    private List<String> images;
    private String user;
    private String title;
    private String content;
    private Boolean status;
    private Integer process;

    public DreamModel1(List<String> images, String user, String title, String content, Boolean status, Integer process) {
        this.images = images;
        this.user = user;
        this.title = title;
        this.content = content;
        this.status = status;
        this.process = process;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }
}
