package com.example.rui12.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;


import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.ShowPostActivity;
import com.example.rui12.myapplication.adapter.DreamPostAdapter;
import com.example.rui12.myapplication.model.DreamModel;
import com.example.rui12.myapplication.model.LaudModel;
import com.example.rui12.myapplication.utils.CommonUtils;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SquareFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SquareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SquareFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static int hasLoaded = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RefreshLayout refreshLayout;
    private CommonUtils commonUtils;

    final List<DreamModel> items = new ArrayList<>();
    DreamPostAdapter dreamPostAdapter;
    static final int ITEMS = 9;

    private OnFragmentInteractionListener mListener;

    public SquareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SquareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SquareFragment newInstance(String param1, String param2) {
        SquareFragment fragment = new SquareFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        commonUtils = new CommonUtils();
        initRefreshLayout(view);
        initRecycleView(view);
        return view;
    }

    //初始化recyclerView
    private void initRecycleView(View view){
        mRecyclerView=view.findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        //初始化一个adapter
        dreamPostAdapter = new DreamPostAdapter(getActivity(),items,2);

        //设置adapter的点击事件
        dreamPostAdapter.setmOnItemClickListener(new DreamPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, DreamPostAdapter.ViewName viewName, final int position) {
                Toast.makeText(getContext(),"switch外点击了Item", Toast.LENGTH_SHORT).show();
                switch (view.getId()){
//                    case R.id.civ_header:
//                        Toast.makeText(getContext(),"点击了头像:" + position,Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "onItemClick: 点击了头像:" + position);
//                        break;
//                    case R.id.ib_like:
//                        //找到用户名
//                        final SharedPreferences local_user = getActivity().getSharedPreferences("local_user", 0);
//                        final String username = local_user.getString("username",null);
//
//                        //查询是否已经被点赞
//                        BmobQuery<LaudModel> bmobQuery = new BmobQuery<>();
//                        bmobQuery.addWhereEqualTo("username",username).addWhereEqualTo("dreamID",items.get(position).getObjectId()).findObjects(new FindListener<LaudModel>() {
//                            @Override
//                            public void done(List<LaudModel> list, BmobException e) {
//                                if(list.isEmpty()){
//                                    view.setBackground(commonUtils.toDrawable(getActivity(),R.drawable.like_orange));
//                                    LaudModel laudModel = new LaudModel(username,items.get(position).getObjectId());
//                                    laudModel.save(new SaveListener<String>() {
//                                        @Override
//                                        public void done(String s, BmobException e) {
//                                            Toast.makeText(getContext(),"点赞成功",Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }else{
//                                    view.setBackground(commonUtils.toDrawable(getActivity(),R.drawable.like_black));
//                                    LaudModel laudModel = new LaudModel();
//                                    laudModel.setObjectId(list.get(0).getObjectId());
//                                    laudModel.delete(new UpdateListener() {
//                                        @Override
//                                        public void done(BmobException e) {
//                                            Toast.makeText(getContext(),"取消点赞",Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                                    DreamModel dreamModel = new DreamModel();
//                                    final int t = Integer.valueOf(num_like.getText().toString()) - 1;
//                                    dreamModel.setNum_of_laud(t);
//
//                                    dreamModel.update(dreamID, new UpdateListener() {
//                                        @Override
//                                        public void done(BmobException e) {
//                                            if(e == null){
//                                                num_like.setText(t);
//                                            }else{
//                                                Toast.makeText(getApplicationContext(),"更新失败",Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                                }
//                            }
//                        });
//                        break;
                    default:
                        Intent intent = new Intent(getActivity(),ShowPostActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id",items.get(position).getObjectId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onItemLongClick(int position) {
                Toast.makeText(getContext(),"长按了item:" + position,Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onItemLongClick: 长按了item:" + position);
            }
        });
        mRecyclerView.setAdapter(dreamPostAdapter);

        BmobQuery<DreamModel> query = new BmobQuery<>();
        query.setLimit(20).setSkip(hasLoaded).order("-createdAt").findObjects(new FindListener<DreamModel>() {
            @Override
            public void done(List<DreamModel> list, BmobException e) {
                if(e == null){
                    Toast.makeText(getContext(),"查询到"+list.size()+"条心愿",Toast.LENGTH_SHORT).show();
                    items.addAll(list);
                    dreamPostAdapter.notifyDataSetChanged();
                    hasLoaded += list.size();
                }else{
                    Log.d("Bmob","查询数据失败");
                }
            }
        });
    }

    //下拉刷新和上拉加载
    private void initRefreshLayout(View view){
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
//                Toast.makeText(getContext(),"我正在下拉刷新",Toast.LENGTH_SHORT).show();

                BmobQuery<DreamModel> query = new BmobQuery<>();
                query.setLimit(20).setSkip(0).order("-createdAt").findObjects(new FindListener<DreamModel>() {
                    @Override
                    public void done(List<DreamModel> list, BmobException e) {
                        if(e == null){
                            Toast.makeText(getContext(),"查询到"+list.size()+"条心愿",Toast.LENGTH_SHORT).show();
                            items.clear();
                            items.addAll(list);
                            dreamPostAdapter.notifyDataSetChanged();
                            hasLoaded = list.size();
                        }else{
                            Log.d("Bmob","查询数据失败");
                        }
                    }
                });
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
//                Toast.makeText(getContext(),"我正在上拉加载",Toast.LENGTH_SHORT).show();

                BmobQuery<DreamModel> query = new BmobQuery<>();
                query.setLimit(20).setSkip(hasLoaded).order("-createdAt").findObjects(new FindListener<DreamModel>() {
                    @Override
                    public void done(List<DreamModel> list, BmobException e) {
                        if(e == null){
                            Toast.makeText(getContext(),"查询到"+list.size()+"条心愿",Toast.LENGTH_SHORT).show();
                            items.addAll(list);
                            dreamPostAdapter.notifyDataSetChanged();
                            hasLoaded += list.size();
                        }else{
                            Log.d("Bmob","查询数据失败");
                        }
                    }
                });
                refreshlayout.finishLoadMore(2000);//传入false表示加载失败
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
