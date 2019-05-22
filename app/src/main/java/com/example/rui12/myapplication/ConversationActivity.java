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
                Log.d("发送", "onClick: " + editText.getText().toString());
                if(!editText.getText().toString().isEmpty()){
                    String content = editText.getText().toString();
                    ConversationModel conversationModel = new ConversationModel(content,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551290161697&di=b712005413d62e65be7c085ac8236573&imgtype=0&src=http%3A%2F%2Fpic.k73.com%2Fup%2Farticle%2F2017%2F0110%2F091942_18858530.jpg",2);
                    conversationModelList.add(conversationModel);
                    conversationAdapter.notifyDataSetChanged();

                    sendMsg();
                }else{
//                    Toast.makeText(getApplicationContext(),"不能发送非空消息",Toast.LENGTH_SHORT).show();
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
        try{
            Thread.sleep(2000);
        }catch (Exception e ){
            e.printStackTrace();
        }
//        SharedPreferences local_user = getSharedPreferences("local_user", 0);
//        String header = local_user.getString("header",null);
        Log.d("发送", "sendMsg: " + "已经到函数了1");
        ConversationModel conversationModel = new ConversationModel(items.get(status),"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551290161697&di=b712005413d62e65be7c085ac8236573&imgtype=0&src=http%3A%2F%2Fpic.k73.com%2Fup%2Farticle%2F2017%2F0110%2F091942_18858530.jpg",1);
        Log.d("发送", "sendMsg: " + "已经到函数了2");
        conversationModelList.add(conversationModel);
        Log.d("发送", "sendMsg: " + "已经到函数了3");
        conversationAdapter.notifyDataSetChanged();
        Log.d("发送", "sendMsg: " + "已经到函数了4");
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
