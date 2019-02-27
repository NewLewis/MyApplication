package com.example.rui12.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.adapter.RecyclerViewPagerAdapter;
import com.example.rui12.myapplication.model.DreamModel;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.ArrayList;
import java.util.List;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView=view.findViewById(R.id.recyclerView);
        for (int i=0;i<ITEMS;i++){
            items.add(new DreamModel());
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(new RecyclerViewPagerAdapter(getActivity(),items,2));
    }
}