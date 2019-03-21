package com.example.rui12.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rui12.myapplication.fragments.MessageFragment;
import com.example.rui12.myapplication.fragments.MyselfFragment;
import com.example.rui12.myapplication.fragments.SquareFragment;
import com.example.rui12.myapplication.utils.CommonUtils;



public class MainActivity extends AppCompatActivity implements
        SquareFragment.OnFragmentInteractionListener,
        MessageFragment.OnFragmentInteractionListener,
        MyselfFragment.OnFragmentInteractionListener,
        View.OnClickListener {

    private SquareFragment squareFragment;
    private MessageFragment messageFragment;
    private MyselfFragment myselfFragment;
    private LinearLayout ll_square;
    private LinearLayout ll_message;
    private LinearLayout ll_myself;
    private ImageView iv_square;
    private ImageView iv_message;
    private ImageView iv_myself;
    private TextView tv_square;
    private TextView tv_message;
    private TextView tv_myself;
    private CommonUtils commonUtils;
    private FragmentManager fm;
    private FloatingActionButton bt_float;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setOnClickListener();
    }

    private void init(){
        ll_square = findViewById(R.id.ll_square);
        ll_message = findViewById(R.id.ll_message);
        ll_myself = findViewById(R.id.ll_myself);
        iv_square = findViewById(R.id.iv_square);
        iv_message = findViewById(R.id.iv_message);
        iv_myself = findViewById(R.id.iv_myself);
        tv_square = findViewById(R.id.tv_square);
        tv_message = findViewById(R.id.tv_message);
        tv_myself = findViewById(R.id.tv_myself);
        bt_float = findViewById(R.id.bt_float);

        //设置顶部状态栏为白色
        commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);

        //装载第一个fragment
        fm = getSupportFragmentManager();
        showFragment(1);

        //初始化bottom第一个button
        iv_square.setBackground(commonUtils.toDrawable(MainActivity.this,R.drawable.square_orange));
        tv_square.setTextColor(commonUtils.colorToInt(MainActivity.this,R.color.colorOrange));
    }

    private void setOnClickListener(){
        ll_square.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        ll_myself.setOnClickListener(this);
        iv_square.setOnClickListener(this);
        iv_message.setOnClickListener(this);
        iv_myself.setOnClickListener(this);
        tv_square.setOnClickListener(this);
        tv_message.setOnClickListener(this);
        tv_myself.setOnClickListener(this);
        bt_float.setOnClickListener(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_square:
            case R.id.iv_square:
            case R.id.tv_square:
                showFragment(1);
                //标橙色
                iv_square.setBackground(commonUtils.toDrawable(MainActivity.this,R.drawable.square_orange));
                tv_square.setTextColor(commonUtils.colorToInt(MainActivity.this,R.color.colorOrange));
                //其他的都变白
                iv_message.setBackground(commonUtils.toDrawable(MainActivity.this,R.drawable.message));
                tv_message.setTextColor(commonUtils.colorToInt(MainActivity.this,R.color.colorBlack));
                iv_myself.setBackground(commonUtils.toDrawable(MainActivity.this,R.drawable.myself));
                tv_myself.setTextColor(commonUtils.colorToInt(MainActivity.this,R.color.colorBlack));
                break;
            case R.id.ll_message:
            case R.id.iv_message:
            case R.id.tv_message:
                showFragment(2);
                iv_message.setBackground(commonUtils.toDrawable(MainActivity.this,R.drawable.message_orange));
                tv_message.setTextColor(commonUtils.colorToInt(MainActivity.this,R.color.colorOrange));
                //其他的都变白
                iv_square.setBackground(commonUtils.toDrawable(MainActivity.this,R.drawable.square));
                tv_square.setTextColor(commonUtils.colorToInt(MainActivity.this,R.color.colorBlack));
                iv_myself.setBackground(commonUtils.toDrawable(MainActivity.this,R.drawable.myself));
                tv_myself.setTextColor(commonUtils.colorToInt(MainActivity.this,R.color.colorBlack));
                break;
            case R.id.ll_myself:
            case R.id.iv_myself:
            case R.id.tv_myself:
                showFragment(3);
                iv_myself.setBackground(commonUtils.toDrawable(MainActivity.this,R.drawable.myself_ogange));
                tv_myself.setTextColor(commonUtils.colorToInt(MainActivity.this,R.color.colorOrange));
                //其他的都变白
                iv_message.setBackground(commonUtils.toDrawable(MainActivity.this,R.drawable.message));
                tv_message.setTextColor(commonUtils.colorToInt(MainActivity.this,R.color.colorBlack));
                iv_square.setBackground(commonUtils.toDrawable(MainActivity.this,R.drawable.square));
                tv_square.setTextColor(commonUtils.colorToInt(MainActivity.this,R.color.colorBlack));
                break;
            case R.id.bt_float:
                Intent intent = new Intent(this,PublishPostActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void showFragment(int index) {
        FragmentTransaction ft = fm.beginTransaction();

        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ft);

        switch (index) {
            case 1:
                // 如果fragment1已经存在则将其显示出来
                if (squareFragment != null)
                    ft.show(squareFragment);
                    // 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                else {
                    squareFragment = new SquareFragment();
                    ft.add(R.id.frameLayout, squareFragment);
                }
                break;
            case 2:
                if (messageFragment != null)
                    ft.show(messageFragment);
                else {
                    messageFragment = new MessageFragment();
                    ft.add(R.id.frameLayout, messageFragment);
                }
                break;
            case 3:
                if (myselfFragment != null)
                    ft.show(myselfFragment);
                else {
                    myselfFragment = new MyselfFragment();
                    ft.add(R.id.frameLayout, myselfFragment);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }
    // 当fragment已被实例化，就隐藏起来
    public void hideFragments(FragmentTransaction ft) {
        if (squareFragment != null)
            ft.hide(squareFragment);
        if (messageFragment != null)
            ft.hide(messageFragment);
        if (myselfFragment != null)
            ft.hide(myselfFragment);
    }
}
