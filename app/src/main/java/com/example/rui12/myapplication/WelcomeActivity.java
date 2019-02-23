package com.example.rui12.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rui12.myapplication.utils.CommonUtils;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //设置状态栏
        CommonUtils commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);

        //设置欢迎界面持续3s后自动转跳
        handler.sendEmptyMessageDelayed(0,3000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            super.handleMessage(msg);
        }
    };
}
