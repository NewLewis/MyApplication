package com.example.rui12.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui12.myapplication.model.DreamModel;
import com.example.rui12.myapplication.utils.CommonUtils;
import com.example.rui12.myapplication.utils.PictureUtil;
import com.suke.widget.SwitchButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PublishPostActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,View.OnClickListener,BGASortableNinePhotoLayout.Delegate{

    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    private static final String EXTRA_MOMENT = "EXTRA_MOMENT";

    private CommonUtils commonUtils;
    private Toolbar toolbar;
    private TextView publish;
    private EditText title;
    private EditText content;
    private SwitchButton bt_set_private;
    private SwitchButton bt_diary;
    private SwitchButton bt_account;
    private SwitchButton bt_clock;
    private ProgressBar progressBar;

    //拖拽排序九宫格控件
    private BGASortableNinePhotoLayout mPhotosSnpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_post);
        init();
        setOnClickListener();
    }

    private void init(){
        //设置顶部状态栏
        commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);
        //初始化每个控件
        mPhotosSnpl = findViewById(R.id.snpl_moment_add_photos);
        toolbar = findViewById(R.id.toolbar);
        publish = findViewById(R.id.tv_publish);
        title = findViewById(R.id.et_title);
        content = findViewById(R.id.et_content);
        bt_set_private = findViewById(R.id.bt_set_private);
        bt_account = findViewById(R.id.bt_account);
        bt_clock = findViewById(R.id.bt_clock);
        bt_diary = findViewById(R.id.bt_diary);
        progressBar = findViewById(R.id.progressBar);

        //设置toolbar上的返回键
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void setOnClickListener(){
        mPhotosSnpl.setOnClickListener(this);
        mPhotosSnpl.setDelegate(this);
        publish.setOnClickListener(this);
    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {
        Toast.makeText(this, "排序发生变化", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.snpl_moment_add_photos:
                Toast.makeText(getApplicationContext(),"点击了图片选择",Toast.LENGTH_SHORT).show();
                choicePhotoWrapper();
                break;
            case R.id.tv_publish:
                //发布dream
                SharedPreferences local_user= getSharedPreferences("local_user", 0);
                final String user = local_user.getString("username","");

                final String title_s = title.getText().toString();
                final String content_s = content.getText().toString();

                //将图片文件传送到bmob云服务器
                List<String> images = mPhotosSnpl.getData();

                final String[] filepaths = new String[images.size()];
                PictureUtil pictureUtil = new PictureUtil();

                //亚索图片质量，并返回压缩后图片的储存地址信息
                for(int i=0;i<images.size();i++){
                    pictureUtil.saveTmpPicture(pictureUtil.getSmallBitmap(images.get(i)),PublishPostActivity.this,filepaths,i);
                }

//                for(String s: filepaths){
//                    System.out.println(s);
//                }

                if(images.size() != 0){
                    BmobFile.uploadBatch(filepaths, new UploadBatchListener() {
                        @Override
                        public void onSuccess(List<BmobFile> list, List<String> urls) {
                            if(urls.size()==filepaths.length){//如果数量相等，则代表文件全部上传完成
                                //do something
                                Toast.makeText(getApplicationContext(),"图片上传成功",Toast.LENGTH_SHORT).show();
//                                DreamModel dreamModel = new DreamModel(urls,user,title_s,content_s,switchButton.isChecked(),0);

                                //删除临时图片文件
                                for (String s:filepaths){
                                    commonUtils.delete(s);
                                }

                                //新建post信息
                                DreamModel dreamModel = new DreamModel(urls,user,title_s,content_s,bt_set_private.isChecked(),0,getToolsNum(),0,0);
                                dreamModel.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if(e == null){
                                            Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });
                                progressBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                            //1、curIndex--表示当前第几个文件正在上传
                            //2、curPercent--表示当前上传文件的进度值（百分比）
                            //3、total--表示总的上传文件数
                            //4、totalPercent--表示总的上传进度（百分比）
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                }else{
                    DreamModel dreamModel = new DreamModel(null,user,title_s,content_s,bt_set_private.isChecked(),0);
                    dreamModel.setProcess(0);
                    dreamModel.setNum_of_laud(0);
                    dreamModel.setNum_of_review(0);
                    dreamModel.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e == null){
                                Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
//            if (mSingleChoiceCb.isChecked()) {
//                mPhotosSnpl.setData(BGAPhotoPickerActivity.getSelectedPhotos(data));
//            } else {
                mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
//            }
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }
    }

    private int getToolsNum(){
        int a = bt_account.isChecked() ? 4 : 0;
        int b = bt_clock.isChecked() ? 2 : 0;
        int c = bt_diary.isChecked() ? 1 : 0;
        return a + b + c;
    }


}
