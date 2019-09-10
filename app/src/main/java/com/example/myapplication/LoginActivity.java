package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.helloworld.R;
import com.example.myapplication.Util.MD5Utils;

public class LoginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etUsername, etPassword;
    private Button btnLogin , btnRegister;
    private TextView tUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar();
        initView();
//        initData();
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String name = pref.getString("username", "");
                String pwd = pref.getString("password", "");
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
//                } else if (!username.equals(name)) {
//                    Toast.makeText(LoginActivity.this, "用户名错误"+username+","+name , Toast.LENGTH_SHORT).show();
                } else if (!password.equals(password)) {
                    Toast.makeText(LoginActivity.this, "密码错误"+password, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    // 给bnt1添加点击响应事件
                    saveLoginStatus(username,true);
                    //跳转到我的页面
                    Intent intent = new Intent();
                    intent.putExtra("isLogin",true);
                    intent.putExtra("loginUser",true);
                   setResult(RESULT_OK,intent);
                    LoginActivity.this.finish();

                }
            }
        });
    }
    private void saveLoginStatus(String username , boolean isLogin){
        getSharedPreferences("userInfo" , MODE_PRIVATE)
                .edit()
                .putString("loginUser",username)
                .putBoolean("isLogin" , isLogin)
                .apply();
    }
    private void initToolbar(){
        toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回键
//            actionBar.setHomeButtonEnabled(true);//设置是否是首页
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
    }
    private void initView(){
        etUsername = findViewById(R.id.name);
        etPassword = findViewById(R.id.password);
        btnLogin =findViewById(R.id.login_button);
        TextView tvRegister=findViewById(R.id.login_button);
        tvRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        Button btnLogin = findViewById(R.id.register_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1 && resultCode == RESULT_OK && data !=null){
            String username=data.getStringExtra("username");
            etUsername.setText(username);
   //         etUsername.setSelection(username.length());
        }
    }

//    private void initData(){
    //  Intent intent =getIntent();
//        String username = intent().getStringExtra("username")
//        if (!TextUtils.isEmpty(username)){
//            etUsername.setText(username);
//        }
//    }
    private String readPwd(String username){
        SharedPreferences sp = getSharedPreferences("userInfo" , MODE_PRIVATE);
        return sp.getString(username , "");
    }
    private void getUserName() {
        Intent intent = getIntent();
        String username_register = intent.getStringExtra("username");
        if (username_register == null || username_register == "") {
            tUsername.setText("");
        } else {
            tUsername.setText(username_register);
        }
    }
}
