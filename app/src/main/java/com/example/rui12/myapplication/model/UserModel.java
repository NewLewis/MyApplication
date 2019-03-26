package com.example.rui12.myapplication.model;

import cn.bmob.v3.BmobObject;

public class UserModel extends BmobObject {
    private String username;
    private String phone;
    private String password;

    public UserModel(String username, String phone, String password) {
        this.username = username;
        this.phone = phone;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
