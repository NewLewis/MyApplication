package com.example.rui12.myapplication;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.rui12.myapplication.fragments.SquareFragment;
import com.example.rui12.myapplication.utils.CommonUtils;



public class MainActivity extends AppCompatActivity implements SquareFragment.OnFragmentInteractionListener {

    private SquareFragment squareFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        squareFragment = new SquareFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,squareFragment).commitAllowingStateLoss();

        CommonUtils commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
