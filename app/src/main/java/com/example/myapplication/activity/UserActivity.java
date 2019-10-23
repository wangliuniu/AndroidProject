package com.example.myapplication.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.helloworld.R;
import com.example.myapplication.Util.SharedUtils;
import com.example.myapplication.Util.StatusUtils;
import com.example.myapplication.entity.User;
import com.example.myapplication.service.impl.UserService;
import com.example.myapplication.service.impl.UserServiceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView tvUsername,tvNickname,tvSignature,tvSex;
    private LinearLayout nicknameLayout,signatureLayout,sexLayout;
    private String spUsername;
    private User user;
    private UserService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initToolbar();
        initDate();
        initView();
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("个人信息");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回键
//            actionBar.setHomeButtonEnabled(true);//设置是否是首页
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               UserActivity.this.finish();
            }
        });
    }
//    private String readLoginInfo(){
//        SharedPreferences sp=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
//        return sp.getString("loginUser","");
//    }


    private void initDate(){
        spUsername = SharedUtils.readValue(this,"loginUser");
//        spUsername=readLoginInfo();
       service =new UserServiceImpl(this);
       user=service.get(spUsername);//从数据库读取
       user=readFromInternal();//从内部存储读取
       user=readPrivatExStorage();//从外部私有存储读取
       user=readPublicExStorage();//从外部共有存储读取
       if(user==null){
           user=new User();
           user.setUsername(spUsername);
           user.setNickname("课程助手");
           user.setSignature("课程助手");
           user.setSex("女");
           service.save(user);
           savaToInternal(user);
           savaPrivateExStorage(user);
           savaPublicExStorage(user);
       }

    }
    private void initView(){
        tvUsername=findViewById(R.id.yonghuming);
        tvNickname=findViewById(R.id.nicheng);
        tvSex=findViewById(R.id.sex);
        tvSignature=findViewById(R.id.qianming);

        nicknameLayout=findViewById(R.id.rl_nickname);
        sexLayout=findViewById(R.id.rl_sex);
        signatureLayout=findViewById(R.id.rl_signature);

        //设置数据库获取的数据
        tvUsername.setText(user.getUsername());
        tvNickname.setText(user.getNickname());
        tvSex.setText(user.getSex());
        tvSignature.setText(user.getSignature());


        //设置监听器
        nicknameLayout.setOnClickListener(this);
        sexLayout.setOnClickListener(this);
        signatureLayout.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        //根据id分别进行事件处理
      switch (v.getId()){
          case R.id.rl_nickname:
              //将昵称，性别等信息传给ChangeUserInfoActivity进行修改保存并返回
              modifyNickname();
              break;
          case R.id.rl_sex:
              //通过对话框修改
              modifySex();
              break;
          case R.id.rl_signature:
              //将昵称，性别等信息传给ChangeUserActivity进行修改保存并返回
              modifySignature();
              break;
      }
    }
    public void modifyNickname(){
        //获取已有的内容
    String nickname=tvNickname.getText().toString();
    Intent intent=new Intent(UserActivity.this,ChangeUserActivity.class);
    Bundle bundle=new Bundle();
    bundle.putString("title","设置昵称");//标题栏的标题
    bundle.putString("value",nickname);//内容
    bundle.putInt("flag",1);//用于区分修改昵称还是签名
    intent.putExtras(bundle);
    //启动下一个界面
    startActivityForResult(intent,1);

    }
    public void modifySex(){
        //单选框
        final String[] datas={"男","女"};
        String sex=tvSex.getText().toString();
        final List<String> sexs= Arrays.asList(datas);
        int selected=sexs.indexOf(sex);

        new AlertDialog.Builder(this).setTitle("性别").setSingleChoiceItems(datas, selected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sex=datas[which];
                tvSex.setText(sex);
                user.setSex(sex);
                service.modify(user);
                dialog.dismiss();
            }
        }).show();

    }
    public void modifySignature(){
        //获取已有的内容
        String signature=tvSignature.getText().toString();
        Intent intent=new Intent(UserActivity.this,ChangeUserActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("title","设置签名");//标题栏的标题
        bundle.putString("value",signature);//内容
        bundle.putInt("flag",2);//用于区分修改昵称还是签名
        intent.putExtras(bundle);
        //启动下一个界面
        startActivityForResult(intent,2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //对空数据，返回异常做判断
        if(data==null || resultCode != RESULT_OK){
            Toast.makeText(this,"未知错误",Toast.LENGTH_SHORT).show();
            return;
        }
        //根据requestCode进行对应的保存
        //获取data数据
        if(requestCode==1){
            //设置user对应的属性
            String value=data.getStringExtra("nickname");
            tvNickname.setText(value);
            user.setNickname(value);
            service.modify(user);
            savaToInternal(user);

        }else if(requestCode==2){
            String value=data.getStringExtra("signature");
            tvSignature.setText(value);
            user.setSignature(value);
            service.modify(user);
            savaToInternal(user);

        }
        //保存到数据库
        service.modify(user);
    }
    private  static final String FILE_NAME="userinfo.txt";
    //读取
    private User readFromInternal(){
        User user=null;
        try{
            FileInputStream in=this.openFileInput(FILE_NAME);
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            String data=reader.readLine();
            user= JSON.parseObject(data,User.class);
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return user;
    }
    //保存
    private void savaToInternal(User user){
        try{
            //打开文件输出流
            FileOutputStream out=this.openFileOutput(FILE_NAME,Context.MODE_PRIVATE);
            //创建对象
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
            //写入数据
            writer.write(JSON.toJSONString(user));
            //关闭输出流
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //外部私有存储
    private void savaPrivateExStorage(User user){
        try{
            //打开文件输出流
           File file=new File(getExternalFilesDir(""),FILE_NAME);
           FileOutputStream out=new FileOutputStream(file);
            //创建对象
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
            //写入数据
            writer.write(JSON.toJSONString(user));
            //关闭输出流
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private User readPrivatExStorage(){
        User user=null;
        try{
            File file=new File(getExternalFilesDir(""),FILE_NAME);
            FileInputStream in=new FileInputStream(file);
            if(!file.exists()){
                return null;
            }
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            String data=reader.readLine();
            user= JSON.parseObject(data,User.class);
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return user;
    }
    private static  final int REQUEST_WRITE_USERINFO=101;
    private static  final int REQUEST_READ_USERINFO=102;
    //公有权限存储
    private void savaPublicExStorage(User user){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_USERINFO);
            return ;
            }
        saveUserInfo(user);
        }
//        try{
//            //打开文件输出流
//            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
//            FileOutputStream out=new FileOutputStream(file);
//            //创建对象
//            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
//            //写入数据
//            writer.write(JSON.toJSONString(user));
//            //关闭输出流
//            writer.close();
//        }catch (IOException e){
//            e.printStackTrace();
//
//    }
    }
    private User readPublicExStorage(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_READ_USERINFO);
                return null;
            }
        }
//        try{
//            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
//            FileInputStream in=new FileInputStream(file);
//            if(!file.exists()){
//                return null;
//            }
//            FileInputStream in=new FileInputStream(file);
//            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
//            String data=reader.readLine();
//            user= JSON.parseObject(data,User.class);
//            reader.close();
//            in.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        return readUserInfo();
    }
//权限请求回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length ==0||grantResults[0] !=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"申请权限被拒绝，无法执行操作",Toast.LENGTH_SHORT).show();
            return;
        }
        if(requestCode==REQUEST_READ_USERINFO) {
//            try {
//                File file = new File(Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_DOWNLOADS), FILE_NAME);
//                FileInputStream in = new FileInputStream(file);
//                if (!file.exists()) {
//                    return;
//                }
//                FileInputStream in = new FileInputStream(file);
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                String data = reader.readLine();
//                user = JSON.parseObject(data, User.class);
//                reader.close();
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            user=readUserInfo();
        }else if (requestCode==REQUEST_WRITE_USERINFO){
//
//            try{
//                //打开文件输出流
//                File file=new File(Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
//                FileOutputStream out=new FileOutputStream(file);
//                //创建对象
//                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
//                //写入数据
//                writer.write(JSON.toJSONString(user));
//                //关闭输出流
//                writer.close();
//                out.close();
//            }catch (IOException e){
//                e.printStackTrace();
//
//            }
            saveUserInfo(user);
        }
        }
  private void saveUserInfo(User user){
      try{
          //打开文件输出流
          File file=new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
          FileOutputStream out=new FileOutputStream(file);
          //创建对象
          BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
          //写入数据
          writer.write(JSON.toJSONString(user));
          //关闭输出流
          writer.close();
          out.close();
      }catch (IOException e){
          e.printStackTrace();

      }

  }
  private User readUserInfo(){
        User user=null;
      File file=new File(Environment.getExternalStoragePublicDirectory(
              Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
      try{
          FileInputStream in=new FileInputStream(file);
          int length=in.available();
          byte[] data=new byte[length];
          int len=in.read(data);
          user=JSON.parseObject(data,User.class);
      }catch (IOException e){
          e.printStackTrace();
          Toast.makeText(this,"读取失败",Toast.LENGTH_SHORT).show();
      }
      return  user;
  }


}

