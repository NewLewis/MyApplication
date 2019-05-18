package com.example.rui12.myapplication.model;


import com.example.rui12.myapplication.adapter.ConversationAdapter;

public class ConversationModel {
    private String content;
    private String header;
    private int type;

    public ConversationModel(String content, String header,int type) {
        this.content = content;
        this.header = header;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
