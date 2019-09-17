package com.example.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;
import com.example.myapplication.Util.StatusUtils;

public class SettingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView psdLayout;
    private TextView mibaoLayout;
    private TextView exitLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initToolbar();
        initView();
    }
    private void initView(){
        psdLayout=findViewById(R.id.modifyPassword);
        mibaoLayout=findViewById(R.id.setSecret);
        exitLayout=findViewById(R.id.exitLogin);
        psdLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        mibaoLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this,ProtectPasswordActivity.class);
                startActivity(intent);
            }
        });
        exitLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this,"退出登录",Toast.LENGTH_SHORT);
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("退出")
                        .setMessage("确认退出登录？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                     StatusUtils.clearLoginInfo(SettingActivity.this);

                                //返回我的界面
                             Intent intent=new Intent();
                             intent.putExtra("isLogin",false);
                             setResult(RESULT_OK,intent);
                             SettingActivity.this.finish();

                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .show();
            }
        });

    }
    private void initToolbar(){
        toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回键
//            actionBar.setHomeButtonEnabled(true);//设置是否是首页
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });
    }
}

