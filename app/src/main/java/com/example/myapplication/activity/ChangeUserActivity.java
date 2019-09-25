package com.example.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.helloworld.R;

public class ChangeUserActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String title;
    private String value;
    private int flag;
    private EditText etContent;
    private ImageView ivDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);
        initToolbar();
        initDate();
        initView();


    }
    private void initToolbar(){
        toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("设置昵称");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回键
//            actionBar.setHomeButtonEnabled(true);//设置是否是首页
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ChangeUserActivity.this.finish();
            }
        });
    }

//加载选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user,menu);
        return true;
    }
  //菜单项的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save:
                save();
                break;
            case R.id.item_cancel:
                ChangeUserActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void initDate(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            title=bundle.getString("title");
            value=bundle.getString("value");
            flag=bundle.getInt("flag");

        }
    }
    private void initView(){
        etContent=findViewById(R.id.et_content);
        etContent.setText(value);
        ivDelete=findViewById(R.id.iv_delete);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setText("");
            }
        });
    }

    private void save(){
        Intent intent=new Intent();
        //获取输入的内容
        String value=etContent.getText().toString();
        switch (flag){
            case 1:
                if(TextUtils.isEmpty(value)){
                    Toast.makeText(ChangeUserActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    //将输入的内容返回给UserActivity
                    intent.putExtra("nickname",value);
                    setResult(RESULT_OK,intent);
                    Toast.makeText(ChangeUserActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    ChangeUserActivity.this.finish();
                }
                break;
            case 2:
                if(TextUtils.isEmpty(value)){
                    Toast.makeText(ChangeUserActivity.this,"签名不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    //将输入的内容返回给UserActivity
                    intent.putExtra("signature",value);
                    setResult(RESULT_OK,intent);
                    Toast.makeText(ChangeUserActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    ChangeUserActivity.this.finish();
                }
                break;

        }

    }
}
