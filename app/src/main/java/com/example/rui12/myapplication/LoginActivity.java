package com.example.rui12.myapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui12.myapplication.utils.CommonUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView msg_login;
    private TextView forget_password;
    private TextView hint;
    private Button bt_login;
    private LinearLayout login_content;
    private LinearLayout register_content;
    private EditText user_name;
    private EditText user_password;

    static int LOGIN_STATUS = 1;//初始化登录状态为1，1表示账号密码登录，2表示短信登录/注册
    private long exitTime = 0; //再按一次退出的间隔时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setOnClickListener();
    }

    private void init(){
        //将变量关联到xml布局文件
        msg_login = findViewById(R.id.msg_login);
        forget_password = findViewById(R.id.forget_password);
        bt_login = findViewById(R.id.bt_login);
        login_content = findViewById(R.id.login_content);
        register_content = findViewById(R.id.register_content);
        user_name = findViewById(R.id.user_name);
        user_password = findViewById(R.id.user_password);
        hint = findViewById(R.id.hint);

        //初始化登录界面为账号密码登录
        login_content.setVisibility(View.VISIBLE);
        register_content.setVisibility(View.GONE);
        //错误提示信息为不可见
        hint.setVisibility(View.INVISIBLE);

        //设置状态栏为白色
        CommonUtils commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);
    }

    private void setOnClickListener(){
        msg_login.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        login_content.setOnClickListener(this);
        register_content.setOnClickListener(this);
        user_name.setOnClickListener(this);
        user_password.setOnClickListener(this);
        hint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.msg_login:
                if(LOGIN_STATUS == 1){
                    login_content.setVisibility(View.GONE);
                    register_content.setVisibility(View.VISIBLE);
                    LOGIN_STATUS = 2;
                }else{
                    login_content.setVisibility(View.VISIBLE);
                    register_content.setVisibility(View.GONE);
                    LOGIN_STATUS = 1;
                }
                break;
            case R.id.bt_login:
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
//                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
