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

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class RecyclerViewFragment extends Fragment {
    public static Fragment newInstance(){return  new RecyclerViewFragment();}
    final List<DreamModel> items = new ArrayList<>();
    static final int ITEMS = 9;

    RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycleview, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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