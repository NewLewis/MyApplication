package com.example.rui12.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.rui12.myapplication.utils.CommonUtils;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView edit_info;
    private TextView advice;

    private CommonUtils commonUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
        setOnClickListener();
    }

    private void init(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);

        edit_info = findViewById(R.id.tv_edit_info);
        advice = findViewById(R.id.tv_advice);
    }

    private void setOnClickListener(){
        edit_info.setOnClickListener(this);
        advice.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_edit_info:
                Intent intent = new Intent(this,EditInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_advice:
                Intent intent1 = new Intent(this,AdviceActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
