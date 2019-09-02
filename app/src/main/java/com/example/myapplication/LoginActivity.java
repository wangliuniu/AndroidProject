package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.helloworld.R;

public class LoginActivity extends AppCompatActivity {

    private Button buttonRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonRegist = findViewById(R.id.register_login);
        buttonRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 给bnt1添加点击响应事件
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                //启动
                startActivity(intent);
            }
        });
    }
}
