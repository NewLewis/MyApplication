package com.example.rui12.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui12.myapplication.model.UserModel;
import com.example.rui12.myapplication.utils.CommonUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class DreamManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_process;
    private TextView tv_process;
    private Toolbar toolbar;
    int yourChoice = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_manager);

        init();
        setOnClickListener();
    }

    private void init(){

        CommonUtils commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);

        rl_process = findViewById(R.id.rl_process);
        tv_process = findViewById(R.id.tv_process);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void setOnClickListener(){
        rl_process.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_process:
                final String[] items = { "完成","失败","在路上" };
                AlertDialog.Builder singleChoiceDialog =
                        new AlertDialog.Builder(DreamManagerActivity.this);
                singleChoiceDialog.setTitle("更改心愿完成进度");
                // 第二个参数是默认选项，此处设置为0
                singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
                singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
//                            Toast.makeText(EditInfoActivity.this, "你选择了" + items[yourChoice], Toast.LENGTH_SHORT).show();
                            if(which == 0){//男
                                tv_process.setText("完成");
                            }else if(which == 1){//女
                                tv_process.setText("失败");
                            }else{
                                tv_process.setText("在路上");
                            }
                        }
                    }
                });
                singleChoiceDialog.show();
        }
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
}
