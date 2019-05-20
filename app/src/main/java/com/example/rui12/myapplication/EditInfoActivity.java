package com.example.rui12.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui12.myapplication.model.UserModel;
import com.example.rui12.myapplication.utils.CommonUtils;
import com.example.rui12.myapplication.utils.FileUtils;
import com.example.rui12.myapplication.utils.PictureUtil;
import com.example.rui12.myapplication.view.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class EditInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rl_header;
    private CircleImageView civ_header;
    private RelativeLayout rl_username;
    private TextView tv_username;
    private RelativeLayout rl_sex;
    private TextView tv_sex;
    private RelativeLayout rl_phone;
    private TextView tv_phone;
    private RelativeLayout rl_pass;
    private Toolbar toolbar;

    private CommonUtils commonUtils;
    private String id;
    private int yourChoice = -1;

    public static final int RC_CHOOSE_PHOTO = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        init();
        setOnClickListener();
    }

    private void init(){

        SharedPreferences local_user = getSharedPreferences("local_user", 0);
        id = local_user.getString("id",null);

        commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        rl_header = findViewById(R.id.rl_change_header);
        rl_username = findViewById(R.id.rl_change_username);
        rl_phone = findViewById(R.id.rl_change_phone);
        rl_sex = findViewById(R.id.rl_change_sex);
        rl_pass = findViewById(R.id.rl_change_pass);
        civ_header = findViewById(R.id.civ_header);
        tv_username = findViewById(R.id.tv_username);
        tv_phone = findViewById(R.id.tv_phone);
        tv_sex = findViewById(R.id.tv_sex);

        BmobQuery<UserModel> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("objectId",id).findObjects(new FindListener<UserModel>() {
            @Override
            public void done(List<UserModel> list, BmobException e) {
                if(e == null){
                    if(list.size() == 1){
                        Picasso
                                .with(getApplicationContext())
                                .load(list.get(0).getAvatar())
                                .placeholder(R.drawable.bk_gray)
                                .into(civ_header);

                        if(list.get(0).getSex()){
                            tv_sex.setText("女");
                        }else{
                            tv_sex.setText("男");
                        }

                        String phone_s = commonUtils.encryp_phone(list.get(0).getPhone());
                        tv_phone.setText(phone_s);

                        tv_username.setText(list.get(0).getUsername());
                    }
                }
            }
        });
    }

    private void setOnClickListener(){
        rl_header.setOnClickListener(this);
        rl_pass.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_username.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_change_header:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_CHOOSE_PHOTO);
                } else {
                    //已授权，获取照片
                    choosePhoto();
                }
                break;
            case R.id.rl_change_sex:
                final String[] items = { "男","女" };
                AlertDialog.Builder singleChoiceDialog =
                        new AlertDialog.Builder(EditInfoActivity.this);
                singleChoiceDialog.setTitle("选择更改的性别");
                // 第二个参数是默认选项，此处设置为0
                singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
                singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
//                            Toast.makeText(EditInfoActivity.this, "你选择了" + items[yourChoice], Toast.LENGTH_SHORT).show();
                            UserModel userModel = new UserModel();
                            if(which == 0){//男
                                userModel.setSex(false);
                                tv_sex.setText("男");
                            }else if(which == 1){//女
                                userModel.setSex(true);
                                tv_sex.setText("女");
                            }

                            userModel.update(id, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    Toast.makeText(getApplicationContext(),"性别更新成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                singleChoiceDialog.show();
        }
    }

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, RC_CHOOSE_PHOTO);
    }

    /**
     权限申请结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_CHOOSE_PHOTO:   //相册选择照片权限申请返回
                choosePhoto();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_CHOOSE_PHOTO:
                if(data == null){
                    System.out.println("空");
                    break;
                }
                Uri uri = data.getData();
                String filePath = FileUtils.getFilePathByUri(this, uri);

                System.out.println("头像url：" + uri);
                System.out.println("头像filepaths：" + filePath);

                if (filePath != null && !TextUtils.isEmpty(filePath)) {
//                    RequestOptions requestOptions1 = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
                    //将照片显示在 ivImage上
//                    Glide.with(this).load(filePath).apply(requestOptions1).into(ivImage);
                    PictureUtil pictureUtil = new PictureUtil();
                    final String[] filepaths = new String[1];

                    //亚索图片质量，并返回压缩后图片的储存地址信息
                    pictureUtil.saveTmpPicture(pictureUtil.getSmallBitmap(filePath),EditInfoActivity.this,filepaths,0);
                    System.out.println("filepaths:" + filepaths[0]);
                    BmobFile.uploadBatch(filepaths, new UploadBatchListener() {
                        @Override
                        public void onSuccess(List<BmobFile> list, List<String> urls) {
                            if (urls.size() == filepaths.length) {//如果数量相等，则代表文件全部上传完成
                                UserModel userModel = new UserModel();
                                userModel.setAvatar(urls.get(0));
                                userModel.update(id, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        Toast.makeText(getApplicationContext(),"头像更新成功",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                            //1、curIndex--表示当前第几个文件正在上传
                            //2、curPercent--表示当前上传文件的进度值（百分比）
                            //3、total--表示总的上传文件数
                            //4、totalPercent--表示总的上传进度（百分比）
//                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
