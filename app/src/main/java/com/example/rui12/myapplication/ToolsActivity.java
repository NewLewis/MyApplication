package com.example.rui12.myapplication;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.rui12.myapplication.utils.CommonUtils;

public class ToolsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageButton ib_clock_setting;
    private ImageButton ib_account_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        init();
        setOnClickListener();
    }

    private void init(){

        CommonUtils commonUtils = new CommonUtils();
        commonUtils.setStatusBar(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        toolbar = findViewById(R.id.toolbar);
        ib_clock_setting = findViewById(R.id.ib_clock_setting);
        ib_account_setting = findViewById(R.id.ib_account_setting);
    }

    private void setOnClickListener(){
        ib_clock_setting.setOnClickListener(this);
        ib_account_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_clock_setting:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    }
                },0,0,false);
                timePickerDialog.setTitle("pick");
                timePickerDialog.show();
                break;
            case R.id.ib_account_setting:
                final EditText editText = new EditText(ToolsActivity.this);
                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(ToolsActivity.this);
                inputDialog.setTitle("请输入目标金额").setView(editText);
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ToolsActivity.this,
                                        editText.getText().toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();
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
