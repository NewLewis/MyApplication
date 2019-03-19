package com.example.rui12.myapplication.model;

public class ReviewModel {
    private String username;
    private String reviewContent;
    private String toOther;
    private int num_zan;

    public ReviewModel(String username, String reviewContent, String toOther,int num_zan) {
        this.username = username;
        this.reviewContent = reviewContent;
        this.toOther = toOther;
        this.num_zan = num_zan;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public void setToOther(String toOther) {
        this.toOther = toOther;
    }

    public void setNum_zan(int num_zan) {
        this.num_zan = num_zan;
    }

    public String getUsername() {
        return username;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public String getToOther() {
        return toOther;
    }

    public int getNum_zan() {
        return num_zan;
    }
}
