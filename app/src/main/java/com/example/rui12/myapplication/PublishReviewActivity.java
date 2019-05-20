package com.example.rui12.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui12.myapplication.model.ReviewModel;
import com.example.rui12.myapplication.utils.CommonUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PublishReviewActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView publish;
    private EditText toSb;
    private EditText content;
    private Toolbar toolbar;

    String dreamID;
    String toSb_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_review);

        Bundle bundle = getIntent().getExtras();
        dreamID = bundle.getString("DreamID");
        toSb_s = bundle.getString("toSb");

        init();
        setOnCliskListener();
    }

    private void init(){

        CommonUtils commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);

        //设置toolbar上的返回键
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        publish = findViewById(R.id.tv_publish);
        toSb = findViewById(R.id.et_toSb);
        content = findViewById(R.id.et_content);

        if(toSb_s == null){
            toSb.setVisibility(View.GONE);
        }else{
            toSb.setVisibility(View.VISIBLE);
        }
    }

    private void setOnCliskListener(){
        publish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_publish:
                String content_s = content.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("local_user",0);
                String username_s = sharedPreferences.getString("username",null);

                ReviewModel reviewModel = new ReviewModel(username_s,toSb_s,dreamID,0,content_s);
                reviewModel.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e == null){
                            Toast.makeText(getApplicationContext(),"评论已发布",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("result","init");
                            setResult(1,intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"评论发布失败",Toast.LENGTH_SHORT).show();
                            Log.d("评论", "done: "+e.toString());
                        }
                    }
                });
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
