package com.example.rui12.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.adapter.RecyclerViewPagerAdapter;
import com.example.rui12.myapplication.model.DreamModel;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class RecyclerViewFragment extends Fragment {
    public static Fragment newInstance(){return  new RecyclerViewFragment();}
    final List<DreamModel> items = new ArrayList<>();
    static final int ITEMS = 9;

    private RecyclerView mRecyclerView;
    private RefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycleview, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化recyclerView
        initRecycleView(view);
        //初始化refreshLayout
        initRefreshLayout(view);
    }

    private void initRefreshLayout(View view){
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                Toast.makeText(getContext(),"我正在下拉刷新",Toast.LENGTH_SHORT).show();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                Toast.makeText(getContext(),"我正在上拉加载",Toast.LENGTH_SHORT).show();
                refreshlayout.finishLoadMore(2000);//传入false表示加载失败
            }
        });
    }

    //初始化recyclerView
    private void initRecycleView(View view){
        mRecyclerView=view.findViewById(R.id.recyclerView);
        for (int i=0;i<ITEMS;i++){
            items.add(new DreamModel());
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        //初始化一个adapter
        RecyclerViewPagerAdapter recyclerViewPagerAdapter = new RecyclerViewPagerAdapter(getActivity(),items,2);
        //设置adapter的点击事件
        recyclerViewPagerAdapter.setmOnItemClickListener(new RecyclerViewPagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(),"点击了item:" + position,Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onItemClick: 点击了item:" + position);
            }

            @Override
            public void onItemLongClick(int position) {
                Toast.makeText(getContext(),"长按了item:" + position,Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onItemLongClick: 长按了item:" + position);
            }
        });
        mRecyclerView.setAdapter(recyclerViewPagerAdapter);
    }
}