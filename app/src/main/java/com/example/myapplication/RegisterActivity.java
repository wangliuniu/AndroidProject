package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.helloworld.R;
import com.example.myapplication.Util.MD5Utils;

public class RegisterActivity extends AppCompatActivity {
   //1.获取界面控件
    //2.button的点击事件监听
    //3.处理点击事件
    //3.1 获取控件值
    //3.2 检查数据的有效性
    //3.3 将注册信息存储
    //3.4 跳转到登录界面
    private EditText etUsername, etPassword,etPwdAgain;
    private Button btnRegister;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //  1.获取界面上的控件
        initView();
        initToolbar();
        //  2.
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //  3.1
                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();
                String pwdAgain=etPwdAgain.getText().toString();
                //  3.2
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(pwdAgain)){
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                } else if (!password.equals(pwdAgain)){
                    Toast.makeText(RegisterActivity.this,"两次密码必须一致",Toast.LENGTH_SHORT).show();
                } else if (isExist(username)){
                    Toast.makeText(RegisterActivity.this,"此用户名已存在",Toast.LENGTH_SHORT).show();
                } else{
                    //注册成功之后
                    savePref(username, MD5Utils.md5(password));
                    Intent intent = new Intent();
                    intent.putExtra("username" , username);
                  setResult(RESULT_OK,intent);
                  finish();
                }
            }
        });
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回键
//            actionBar.setHomeButtonEnabled(true);//设置是否是首页
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
    }

    /**
     *
     * @param username
     * @param password
     */
    private void savePref(String username , String password) {
        SharedPreferences sp =getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
//        editor.putString("username",username);
//        editor.putString("password",password);
        editor.putString(username,password);
        editor.apply();
    }

    private void initView(){
        etUsername = findViewById(R.id.name);
        etPassword = findViewById(R.id.password);
        etPwdAgain = findViewById(R.id.password2);
        btnRegister = findViewById(R.id.register);
    }
    private boolean isExist(String username) {
      SharedPreferences sp =getSharedPreferences("userInfo",MODE_WORLD_READABLE);
      String pwd=sp.getString(username,"");
      return !TextUtils.isEmpty(pwd);


    }


}
