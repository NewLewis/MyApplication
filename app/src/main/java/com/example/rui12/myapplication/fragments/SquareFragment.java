package com.example.rui12.myapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.adapter.FragmentAdapter;
import com.example.rui12.myapplication.adapter.RecyclerViewPagerAdapter;
import com.jaeger.ninegridimageview.NineGridImageView;

import java.util.ArrayList;
import java.util.List;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    private TabLayout tabLayout;

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
        init(view);
        return view;
    }

    private void init(View view){
        viewPager = view.findViewById(R.id.viewpager);
        initViewPager(view);
    }

    private void initViewPager(View view){
        tabLayout = view.findViewById(R.id.tabLayout);
        List<String> titles = new ArrayList<>();
        titles.add("自力更生");
        titles.add("互帮互助");

        for(int i=0;i<titles.size();i++){
            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();
        for(int i=0;i<titles.size();i++){
            fragments.add(new RecyclerViewFragment());
        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager(),fragments,titles);
        //给viewpager设置适配器
        viewPager.setAdapter(fragmentAdapter);
        //将tabLayout和viewpager关联起来
        tabLayout.setupWithViewPager(viewPager);
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
