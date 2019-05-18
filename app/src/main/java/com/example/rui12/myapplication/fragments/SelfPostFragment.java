package com.example.rui12.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.adapter.SelfPostAdapter;
import com.example.rui12.myapplication.model.PostModel;
import com.example.rui12.myapplication.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;


public class SelfPostFragment extends Fragment {

    private RecyclerView recyclerView;

    public SelfPostFragment() {
        // Required empty public constructor
    }

    public static SelfPostFragment newInstance(String param1, String param2) {
        return new SelfPostFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_self_post, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.recyclerView);

        //设置recyclerView的adapter
        List<PostModel> postModelList = new ArrayList<>();
        for(int i=1;i<=9;i++){
            postModelList.add(new PostModel("插本广美成功^_^","2019-02-01" + i,i%3));
        }
        SelfPostAdapter selfPostAdapter = new SelfPostAdapter(getActivity(),postModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(selfPostAdapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
    }

}
