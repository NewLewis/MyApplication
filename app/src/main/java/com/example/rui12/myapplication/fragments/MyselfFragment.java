package com.example.rui12.myapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.adapter.PostAdapter;
import com.example.rui12.myapplication.model.PostModel;
import com.example.rui12.myapplication.utils.AppBarStateChangeListener;
import com.example.rui12.myapplication.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyselfFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyselfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyselfFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private AppBarLayout appBarLayout;
    private TextView title;
    private ImageButton ib_setting;
    private CommonUtils commonUtils = new CommonUtils();

    private OnFragmentInteractionListener mListener;

    public MyselfFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyselfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyselfFragment newInstance(String param1, String param2) {
        MyselfFragment fragment = new MyselfFragment();
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
        View view = inflater.inflate(R.layout.fragment_myself, container, false);
        init(view);
        return view;
    }


    private void init(View view){
        //绑定控件
        recyclerView = view.findViewById(R.id.recyclerView);
        appBarLayout = view.findViewById(R.id.appbarLayout);
        ib_setting = view.findViewById(R.id.ib_setting);
//        collapsingToolbarLayout = view.findViewById(R.id.toolbar_layout);
        title = view.findViewById(R.id.myself_title);

        //设置recyclerView的adapter
        List<PostModel> postModelList = new ArrayList<>();
        for(int i=1;i<=9;i++){
            postModelList.add(new PostModel("插本广美成功^_^","2019-02-0" + i,i%3));
        }
        PostAdapter postAdapter = new PostAdapter(getActivity(),postModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(postAdapter);

        //设置appbarLayout的监听事件
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if( state == State.EXPANDED ) {
                    //展开状态
                    //展开状态的时候设置setting图标为白色
                    ib_setting.setBackground(commonUtils.toDrawable(getActivity(),R.drawable.setting_white));
//                    collapsingToolbarLayout.setTitle("");
                    title.setVisibility(View.INVISIBLE);
                }else if(state == State.COLLAPSED){
                    //折叠状态
                    //设置setting图标为黑色
                    ib_setting.setBackground(commonUtils.toDrawable(getActivity(),R.drawable.setting_black));
//                    collapsingToolbarLayout.setTitle("个人中心");
                    title.setText("个人中心");
                    title.setVisibility(View.VISIBLE);
                }else {
                    //中间状态
                    ib_setting.setBackground(commonUtils.toDrawable(getActivity(),R.drawable.setting_white));
//                    collapsingToolbarLayout.setTitle("");
                    title.setVisibility(View.INVISIBLE);
                }
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
