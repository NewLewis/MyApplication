package com.example.rui12.myapplication;

import android.content.ContentProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui12.myapplication.adapter.ConversationAdapter;
import com.example.rui12.myapplication.model.ConversationModel;
import com.example.rui12.myapplication.utils.CommonUtils;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView username;
    private ImageButton takephoto;
    private ImageButton commit;
    private EditText editText;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<ConversationModel> conversationModelList = new ArrayList<>();
    private ConversationAdapter conversationAdapter;
    private List<String> items = new ArrayList<>();

    private CommonUtils commonUtils;
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        init();
        setOnClickListener();
        initRecyclerView();
    }

    private void init(){

        commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        username = findViewById(R.id.tv_username);
        takephoto = findViewById(R.id.ib_take_photo);
        commit = findViewById(R.id.ib_commit);
        editText = findViewById(R.id.et_edit);
        recyclerView = findViewById(R.id.recyclerView);

        initMsg();
    }

    private void initRecyclerView(){
        conversationAdapter = new ConversationAdapter(this,conversationModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        recyclerView.setAdapter(conversationAdapter);
    }

    private void setOnClickListener(){
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_commit:
                if(!editText.getText().toString().isEmpty()){
                    String content = editText.getText().toString();
                    ConversationModel conversationModel = new ConversationModel(content,null,2);
                    conversationModelList.add(conversationModel);
                    conversationAdapter.notifyDataSetChanged();
                    try{
                        Thread.sleep(2000);
                    }catch (Exception e ){
                        e.printStackTrace();
                    }
                    sendMsg();
                }else{
                    Toast.makeText(getApplicationContext(),"不能发送非空消息",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initMsg(){
        items.add("你好呀");
        items.add("我也觉得，我觉得会有一个不错的会有一个不错的观影体验");
        items.add("没问题，晚上见");
    }

    private void sendMsg(){
        SharedPreferences local_user = getSharedPreferences("local_user", 0);
        String header = local_user.getString("header",null);
        ConversationModel conversationModel = new ConversationModel(items.get(status),header,1);
        conversationModelList.add(conversationModel);
        conversationAdapter.notifyDataSetChanged();
        status += 1;
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
