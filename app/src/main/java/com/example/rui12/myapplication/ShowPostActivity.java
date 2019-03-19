package com.example.rui12.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui12.myapplication.adapter.RecyclerViewReviewAdapter;
import com.example.rui12.myapplication.model.PhotoModel;
import com.example.rui12.myapplication.model.ReviewModel;
import com.example.rui12.myapplication.utils.CommonUtils;
import com.example.rui12.myapplication.utils.RecycleViewDivider;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowPostActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private TextView username;
    private ImageView imageHeader;
    private TextView postTime;
    private TextView postTitle;
    private TextView postContent;
    private NineGridImageView<PhotoModel> nineGridImageView;
    private RecyclerView recyclerView;
    private CommonUtils commonUtils;
    private SmartRefreshLayout smartRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        init();
        setOnClickListener();
        initRefreshLayout();
    }

    private NineGridImageViewAdapter<PhotoModel> mAdapter = new NineGridImageViewAdapter<PhotoModel>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, PhotoModel photoModel) {
            Picasso
                    .with(context)
                    .load(photoModel.getUrl())
                    .placeholder(photoModel.getLocalRes())
                    .into(imageView);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }

        @Override
        protected boolean onItemImageLongClick(Context context, ImageView imageView, int index, List<PhotoModel> list) {
            Toast.makeText(context,"长按图片item", Toast.LENGTH_SHORT).show();
            return super.onItemImageLongClick(context, imageView, index, list);
        }

        @Override
        protected void onItemImageClick(Context context, ImageView imageView, int index, List<PhotoModel> list) {
            Toast.makeText(context,"点击图片item",Toast.LENGTH_SHORT).show();
            super.onItemImageClick(context, imageView, index, list);
        }
    };

    private void init(){
//        back = findViewById(R.id.ib_back);
        username = findViewById(R.id.user_name);
        imageHeader = findViewById(R.id.civ_header);
        postTime = findViewById(R.id.post_time);
        postContent = findViewById(R.id.tv_content);
        postTitle = findViewById(R.id.tv_title);
        nineGridImageView = findViewById(R.id.nineGridImageView);
        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);
        smartRefreshLayout = findViewById(R.id.refreshLayout);

        //设置toolbar上的返回键
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        //设置顶部状态栏为白色
        commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);

        //初始化nineGridImageView
        //设置nineGridImageView的adapter
        nineGridImageView.setAdapter(mAdapter);
        //设置nineGridImageView的图片资源
        List<PhotoModel> photoModelList = new ArrayList<>();
        for(int i=0;i<9;i++){
            photoModelList.add(new PhotoModel("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1552967370&di=6ebcae298c8566760e18dc9efe750f92&src=http://i0.hdslb.com/bfs/article/d2c8ffd1fa0b36da92c11abea2ddc83e576fcf29.jpg",R.drawable.bg2));
        }
        nineGridImageView.setImagesData(photoModelList);
        nineGridImageView.setVisibility(View.GONE);

        //初始化评论区
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);

        List<ReviewModel> reviewModelList = new ArrayList<>();
        for(int i=0;i<5;i++){
            reviewModelList.add(new ReviewModel("奥利奥","卧槽，666，你是真的牛鼻",null,50));
        }
        recyclerView.setAdapter(new RecyclerViewReviewAdapter(this,reviewModelList));
        recyclerView.setHasFixedSize(true);
//        recyclerView.setNestedScrollingEnabled(false);
        //设置分界线
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
    }

    private void initRefreshLayout(){
        smartRefreshLayout.setEnableRefresh(false);//是否启用下拉刷新功能
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                Toast.makeText(getApplicationContext(),"我正在上拉加载",Toast.LENGTH_SHORT).show();
                refreshlayout.finishLoadMore(2000);//传入false表示加载失败
            }
        });
    }

    private void setOnClickListener(){
//        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.ib_back:
//                finish();
//                break;
        }
    }
}
