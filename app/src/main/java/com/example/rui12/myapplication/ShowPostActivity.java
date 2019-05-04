package com.example.rui12.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui12.myapplication.adapter.ReviewAdapter;
import com.example.rui12.myapplication.model.CollectModel;
import com.example.rui12.myapplication.model.DreamModel;
import com.example.rui12.myapplication.model.LaudModel;
import com.example.rui12.myapplication.model.PhotoModel;
import com.example.rui12.myapplication.model.ReviewModel;
import com.example.rui12.myapplication.utils.CommonUtils;
import com.example.rui12.myapplication.utils.RecycleViewDivider;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ShowPostActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private TextView username;
    private ImageView imageHeader;
    private ImageView circle;
    private TextView status;
    private TextView postTime;
    private TextView postTitle;
    private TextView postContent;
    private TextView num_review1; //post底部的评论数量
    private TextView num_review2; //评论区的评论数量
    private TextView num_like;
    private ImageButton like;
    private ImageButton collect;
    private ImageButton review;
    private NineGridImageView<PhotoModel> nineGridImageView;
    private RecyclerView recyclerView;
    private CommonUtils commonUtils;
    private SmartRefreshLayout smartRefreshLayout;
    private List<PhotoModel> photoModelList = new ArrayList<>();

    private String dreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        Bundle bundle = getIntent().getExtras();
        dreamID = bundle.getString("id",null);

        init();
        initDreamPost();
        initNinePhoto();
        initReviewRecycleView();
        initRefreshLayout();
        setOnClickListener();
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
        circle = findViewById(R.id.iv_circle);
        status = findViewById(R.id.tv_status);
        num_review1 = findViewById(R.id.tv_review);
        num_review2 = findViewById(R.id.num_review);
        num_like = findViewById(R.id.tv_like);
        like = findViewById(R.id.ib_like);
        collect = findViewById(R.id.ib_collect);
        review = findViewById(R.id.ib_review);

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
    }

    private void initDreamPost(){
        BmobQuery<DreamModel> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("objectId",dreamID).findObjects(new FindListener<DreamModel>() {
            @Override
            public void done(List<DreamModel> list, BmobException e) {
//                Toast.makeText(getApplicationContext(),"查询到了"+list.size()+"条数据",Toast.LENGTH_SHORT).show();
                if(e == null){
                    DreamModel dreamModel = list.get(0);
                    SharedPreferences local_user = getSharedPreferences("local_user", 0);
                    final String username_s = local_user.getString("username",null);

                    username.setText(username_s);
                    postTime.setText(dreamModel.getCreatedAt());
                    postContent.setText(dreamModel.getContent());
                    postTitle.setText(dreamModel.getTitle());
                    for(String s:dreamModel.getImages()){
                        photoModelList.add(new PhotoModel(s,R.drawable.bk_gray));
                    }
                    if(!photoModelList.isEmpty()){
                        nineGridImageView.setVisibility(View.VISIBLE);
                    }else{
                        nineGridImageView.setVisibility(View.GONE);
                    }
                    if(dreamModel.getProcess() == 0){
                        circle.setBackground(commonUtils.toDrawable(ShowPostActivity.this,R.drawable.circle_orange));
                        status.setText("在路上");
                        status.setTextColor(commonUtils.colorToInt(ShowPostActivity.this,R.color.colorOrange));
                    }else if(dreamModel.getProcess() == 1){
                        circle.setBackground(commonUtils.toDrawable(ShowPostActivity.this,R.drawable.circle_green));
                        status.setText("完成");
                        status.setTextColor(commonUtils.colorToInt(ShowPostActivity.this,R.color.green_teal));
                    }else{
                        circle.setBackground(commonUtils.toDrawable(ShowPostActivity.this,R.drawable.circle_red));
                        status.setText("失败");
                        status.setTextColor(commonUtils.colorToInt(ShowPostActivity.this,R.color.red));
                    }
                    num_review1.setText(""+dreamModel.getNum_of_review());
                    num_review2.setText("评论("+dreamModel.getNum_of_review()+")");
                    num_like.setText(""+dreamModel.getNum_of_laud());

                    BmobQuery<LaudModel> query = new BmobQuery<>();
                    query.addWhereEqualTo("username",username_s).addWhereEqualTo("dreamID",dreamID).findObjects(new FindListener<LaudModel>() {
                        @Override
                        public void done(List<LaudModel> list, BmobException e) {
//                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            Log.d("点赞", "done: "+ list.size());
                            if(e == null && list.size() == 1){
                                like.setBackground(commonUtils.toDrawable(ShowPostActivity.this,R.drawable.like_orange));
                            }else{
                                like.setBackground(commonUtils.toDrawable(ShowPostActivity.this,R.drawable.like_black));
                            }
                        }
                    });

                    BmobQuery<CollectModel> query1 = new BmobQuery<>();
                    query1.addWhereEqualTo("username",username_s).addWhereEqualTo("dreamID",dreamID).findObjects(new FindListener<CollectModel>() {
                        @Override
                        public void done(List<CollectModel> list, BmobException e) {
                            Log.d("点赞", "done: "+ username_s);
                            if(e == null && list.size() == 1){
                                collect.setBackground(commonUtils.toDrawable(ShowPostActivity.this,R.drawable.collect_orange));
                            }else{
                                collect.setBackground(commonUtils.toDrawable(ShowPostActivity.this,R.drawable.collect_black));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    Log.d("查询", "done: "+e.toString());
                }
            }
        });
    }

    //初始化九宫格图片
    private void initNinePhoto(){
        //初始化nineGridImageView
        //设置nineGridImageView的adapter
        nineGridImageView.setAdapter(mAdapter);
        //设置nineGridImageView的图片资源

//        for(int i=0;i<9;i++){
//            photoModelList.add(new PhotoModel("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1552967370&di=6ebcae298c8566760e18dc9efe750f92&src=http://i0.hdslb.com/bfs/article/d2c8ffd1fa0b36da92c11abea2ddc83e576fcf29.jpg",R.drawable.bg2));
//        }
        nineGridImageView.setImagesData(photoModelList);
    }

    //初始化评论区的recyclerView
    private void initReviewRecycleView(){
        //初始化评论区
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ReviewModel> reviewModelList = new ArrayList<>();
        for(int i=0;i<5;i++){
            reviewModelList.add(new ReviewModel("奥利奥","卧槽，666，你是真的牛鼻",null,50));
        }
        recyclerView.setAdapter(new ReviewAdapter(this,reviewModelList));
        recyclerView.setHasFixedSize(true);
//        recyclerView.setNestedScrollingEnabled(false);
        //设置分界线
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
    }

    //初始化上拉加载
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void setOnClickListener(){
        like.setOnClickListener(ShowPostActivity.this);
        collect.setOnClickListener(ShowPostActivity.this);
        review.setOnClickListener(ShowPostActivity.this);
    }

    @Override
    public void onClick(View v) {
        //找到用户名
        final SharedPreferences local_user = getSharedPreferences("local_user", 0);
        final String username = local_user.getString("username",null);

        switch (v.getId()){
            case R.id.ib_like :
                //查询是否已经被点赞
                BmobQuery<LaudModel> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("username",username).addWhereEqualTo("dreamID",dreamID).findObjects(new FindListener<LaudModel>() {
                    @Override
                    public void done(List<LaudModel> list, BmobException e) {
                        if(list.isEmpty()){
                            like.setBackground(commonUtils.toDrawable(ShowPostActivity.this,R.drawable.like_orange));
                            LaudModel laudModel = new LaudModel(username,dreamID);
                            laudModel.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    Toast.makeText(getApplicationContext(),"点赞成功",Toast.LENGTH_SHORT).show();
                                }
                            });

                            DreamModel dreamModel = new DreamModel();
                            final int t = Integer.valueOf(num_like.getText().toString()) + 1;
                            dreamModel.setNum_of_laud(t);

                            dreamModel.update(dreamID, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e == null){
                                        num_like.setText(""+t);
                                    }else{
                                        Toast.makeText(getApplicationContext(),"更新失败",Toast.LENGTH_SHORT).show();
                                        Log.d("点赞", "done: "+e.toString());
                                    }
                                }
                            });
                        }else{
                            like.setBackground(commonUtils.toDrawable(ShowPostActivity.this,R.drawable.like_black));
                            LaudModel laudModel = new LaudModel();
                            laudModel.setObjectId(list.get(0).getObjectId());
                            laudModel.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    Toast.makeText(getApplicationContext(),"取消点赞",Toast.LENGTH_SHORT).show();
                                }
                            });

                            DreamModel dreamModel = new DreamModel();
                            final int t = Integer.valueOf(num_like.getText().toString()) - 1;
                            dreamModel.setNum_of_laud(t);

                            dreamModel.update(dreamID, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e == null){
                                        num_like.setText(""+t);
                                    }else{
                                        Toast.makeText(getApplicationContext(),"更新失败",Toast.LENGTH_SHORT).show();
                                        Log.d("点赞", "done: "+e.toString());
                                    }
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.ib_collect:
                //查询是否已经被收藏
                BmobQuery<CollectModel> bmobQuery1 = new BmobQuery<>();
                bmobQuery1.addWhereEqualTo("username",username).addWhereEqualTo("dreamID",dreamID).findObjects(new FindListener<CollectModel>() {
                    @Override
                    public void done(List<CollectModel> list, BmobException e) {
                        if(list.isEmpty()){
                            collect.setBackground(commonUtils.toDrawable(ShowPostActivity.this,R.drawable.collect_orange));
                            CollectModel collectModel = new CollectModel(username,dreamID);
                            collectModel.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    Toast.makeText(getApplicationContext(),"点赞成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            collect.setBackground(commonUtils.toDrawable(ShowPostActivity.this,R.drawable.collect_black));
                            CollectModel collectModel = new CollectModel();
                            collectModel.setObjectId(list.get(0).getObjectId());
                            collectModel.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    Toast.makeText(getApplicationContext(),"取消点赞",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.ib_review:

        }
    }
}
