package com.example.rui12.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
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

import com.example.rui12.myapplication.model.UserModel;
import com.example.rui12.myapplication.utils.CommonUtils;
import com.example.rui12.myapplication.utils.MD5Util;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView msg_login;
    private TextView forget_password;
    private TextView hint;
    private Button bt_login;
    private LinearLayout login_content;
    private LinearLayout register_content;
    private EditText user_name;
    private EditText user_password;
    private EditText user_phone;
    private EditText user_code;
    private Button get_code;
    private CommonUtils commonUtils = new CommonUtils();

    static int LOGIN_STATUS = 1;//初始化登录状态为1，1表示账号密码登录，2表示短信登录/注册
    static int REGISTED_STATUS = 0; //初始手机号注册状态为0,0表示没注册，1表示注册过了
    private long exitTime = 0; //再按一次退出的间隔时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bmob.initialize(this,commonUtils.BmobAppId);
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
        user_phone = findViewById(R.id.user_phone);
        user_code = findViewById(R.id.user_code);
        get_code = findViewById(R.id.get_code);

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
        get_code.setOnClickListener(this);
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
                    msg_login.setText("账号密码登录");
                    forget_password.setVisibility(View.GONE);
                    hint.setVisibility(View.INVISIBLE);
                    LOGIN_STATUS = 2;
                }else{
                    login_content.setVisibility(View.VISIBLE);
                    register_content.setVisibility(View.GONE);
                    msg_login.setText("短信快捷登录");
                    forget_password.setVisibility(View.VISIBLE);
                    hint.setVisibility(View.INVISIBLE);
                    LOGIN_STATUS = 1;
                }
                break;
            case R.id.bt_login:
                if(LOGIN_STATUS == 1){
                    //账号密码登录
                    String username_s = user_name.getText().toString();
                    String password_s = user_password.getText().toString();

                    //将密码进行md5加密
                    MD5Util md5Util = new MD5Util();
                    String pass_encode = md5Util.encode2hex(password_s);

                    //首先进行简单的判断
                    if(username_s.isEmpty() || password_s.isEmpty()){
                        hint.setText("账号/手机号和密码不能为空");
                        hint.setVisibility(View.VISIBLE);
                        break;
                    }
                    
                    BmobQuery<UserModel> bmobQuery = new BmobQuery<>();
                    if (commonUtils.isPhoneLegal(username_s)){
                        //手机号
                        bmobQuery.addWhereEqualTo("phone",username_s);
                    } else{
                        //用户名
                        bmobQuery.addWhereEqualTo("username",username_s);
                    }
                    bmobQuery.addWhereEqualTo("password",pass_encode);
                    bmobQuery.findObjects(new FindListener<UserModel>() {
                        @Override
                        public void done(List<UserModel> list, BmobException e) {
                            if(e == null){
                                Toast.makeText(getApplicationContext(),"共查询到"+list.size()+"条数据",Toast.LENGTH_SHORT).show();
                                if(list.size() == 1){
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    hint.setText("账号或密码错误");
                                    hint.setVisibility(View.VISIBLE);
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else if(LOGIN_STATUS == 2) {
                    final String phone_s = user_phone.getText().toString();
                    String code_s = user_code.getText().toString();

                    //首先进行简单判断
                    if(phone_s.isEmpty()){
                        hint.setText("手机号不能为空");
                        hint.setVisibility(View.VISIBLE);
                        break;
                    }

                    //进行短信验证码的验证
                    BmobSMS.verifySmsCode(phone_s, code_s, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                if (REGISTED_STATUS == 0) {
                                    //将密码进行md5加密
                                    MD5Util md5Util = new MD5Util();
                                    String pass_encode = md5Util.encode2hex("user" + phone_s);

                                    //将该手机号进行注册
                                    UserModel userModel = new UserModel("user" + phone_s, phone_s, pass_encode);
                                    userModel.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null) {
                                                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.get_code:
                Toast.makeText(getApplicationContext(),"点击了get_code",Toast.LENGTH_SHORT).show();
                final String phone_s = user_phone.getText().toString();

                BmobQuery<UserModel> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("phone",phone_s);
                bmobQuery.findObjects(new FindListener<UserModel>() {
                    @Override
                    public void done(List<UserModel> list, BmobException e) {
                        if(e == null){
                            //手机号没有注册
                            if(list.size() == 0){
                                REGISTED_STATUS = 0;
                                final AlertDialog.Builder normalDialog = new AlertDialog.Builder(LoginActivity.this);
                                normalDialog.setMessage("您的手机号尚未注册，点击确定将自动为您注册，默认用户名和密码均为user+您的手机号,登录后请及时修改用户名和密码");
                                normalDialog.setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                BmobSMS.requestSMSCode(phone_s, "dream_sms", new QueryListener<Integer>() {
                                                    @Override
                                                    public void done(Integer smsId, BmobException e) {
                                                        if (e == null) {
//                                                            mTvInfo.append("发送验证码成功，短信ID：" + smsId + "\n");
                                                        } else {
                                                            Toast.makeText(getApplicationContext(),"验证码发送失败",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                normalDialog.setNegativeButton("稍后",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //...To-do
                                            }
                                        });
                                // 显示
                                normalDialog.show();
                            }else{
                                REGISTED_STATUS = 1;
                                BmobSMS.requestSMSCode(phone_s, "dream_sms", new QueryListener<Integer>() {
                                    @Override
                                    public void done(Integer smsId, BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(getApplicationContext(),"验证码发送成功",Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(),"验证码发送失败",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //双击返回键退出应用
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
