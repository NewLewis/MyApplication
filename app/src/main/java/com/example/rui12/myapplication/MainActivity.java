package com.example.rui12.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rui12.myapplication.utils.CommonUtils;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommonUtils commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);
    }
}
