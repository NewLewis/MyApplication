package com.example.rui12.myapplication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui12.myapplication.utils.CommonUtils;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView username;
    private ImageButton takephoto;
    private ImageButton commit;
    private EditText editText;
    private Toolbar toolbar;

    private CommonUtils commonUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        init();
        setOnClickListener();
    }

    private void init(){

        commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        username = findViewById(R.id.tv_username);
        takephoto = findViewById(R.id.ib_take_photo);
        commit = findViewById(R.id.ib_commit);
        editText = findViewById(R.id.et_edit);
    }

    private void setOnClickListener(){
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_commit:
                if(!editText.getText().toString().isEmpty()){

                }else{
                    Toast.makeText(getApplicationContext(),"不能发送非空消息",Toast.LENGTH_SHORT).show();
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
