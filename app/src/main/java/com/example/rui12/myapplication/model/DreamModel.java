package com.example.rui12.myapplication.model;

import android.content.Intent;

import com.example.rui12.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobObject;

public class DreamModel extends BmobObject {
    private List<String> images;
    private String user;
    private String title;
    private String content;
    private Boolean status;
    private Integer process;
    private Integer tools;
    private Integer num_of_laud;
    private Integer num_of_review;

    public DreamModel(List<String> images, String user, String title, String content, Boolean status, Integer tools) {
        this.images = images;
        this.user = user;
        this.title = title;
        this.content = content;
        this.status = status;
        this.tools = tools;
    }

    public DreamModel(List<String> images, String user, String title, String content, Boolean status, Integer process, Integer tools, Integer num_of_laud, Integer num_of_review) {
        this.images = images;
        this.user = user;
        this.title = title;
        this.content = content;
        this.status = status;
        this.process = process;
        this.tools = tools;
        this.num_of_laud = num_of_laud;
        this.num_of_review = num_of_review;
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

    public Integer getTools() {
        return tools;
    }

    public void setTools(Integer tools) {
        this.tools = tools;
    }

    public Integer getNum_of_laud() {
        return num_of_laud;
    }

    public void setNum_of_laud(Integer num_of_laud) {
        this.num_of_laud = num_of_laud;
    }

    public Integer getNum_of_review() {
        return num_of_review;
    }

    public void setNum_of_review(Integer num_of_review) {
        this.num_of_review = num_of_review;
    }
}
