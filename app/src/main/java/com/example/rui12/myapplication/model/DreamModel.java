package com.example.rui12.myapplication.model;

import com.example.rui12.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DreamModel {
    private List<PhotoModel> photoModelList;
    private String user_name;

    public DreamModel(List<PhotoModel> photoModelList, String user_name) {
        this.photoModelList = photoModelList;
        this.user_name = user_name;
    }

    public DreamModel(){
        this.photoModelList = new ArrayList<>();
        int num = (new Random()).nextInt(9);
        for(int i=0;i<num;i++){
            this.photoModelList.add(new PhotoModel("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551278455584&di=ee0d5c823701f0c34caff3210d40481b&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2017-10-25%2F59f082092c082.jpg", R.drawable.header));
        }
        this.user_name = "Oreo for test!";
    }

    public List<PhotoModel> getPhotoModelList() {
        return photoModelList;
    }

    public void setPhotoModelList(List<PhotoModel> photoModelList) {
        this.photoModelList = photoModelList;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
