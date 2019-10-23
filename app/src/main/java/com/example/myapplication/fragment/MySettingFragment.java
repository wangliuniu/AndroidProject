package com.example.myapplication.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;
import com.example.myapplication.activity.PlayRecordActivity;
import com.example.myapplication.activity.ProtectPasswordActivity;
import com.example.myapplication.activity.UserActivity;
import com.example.myapplication.activity.LoginActivity;
import com.example.myapplication.activity.SettingActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MySettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MySettingFragment extends Fragment {
    private boolean isLogin;
    private TextView tvUsername;
    private Context mContext;
    private LinearLayout headLayout;
    private TextView historyLayout,settingLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MySettingFragment() { }
    public static MySettingFragment newInstance(){
        return new MySettingFragment();
    }
    private boolean checkLoginStatus(){
        SharedPreferences sp =mContext.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        return sp.getBoolean("isLogin",false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }
    //初始化Fragment的xml界面上的所有控件和数据，相当于Activity的onCreat()方法的作用
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //1 获取fragment的父Activity，以及目前的登录状态
        this.mContext=getContext();
        this.isLogin=checkLoginStatus();

        //2 获取fragment界面上需要处理的控件对象
        //Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_my_setting, container, false);
        tvUsername =view.findViewById(R.id.tv_username);
        setUsername(isLogin);

        headLayout=view.findViewById(R.id.ll_head);
        historyLayout=view.findViewById(R.id.rl_history);
        settingLayout=view.findViewById(R.id.rl_setting);

        //3 设置时间监听器
        headLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isLogin){
                    Intent intent=new Intent(mContext, UserActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent,1);
                }
            }
        });
        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin){
                    Intent intent = new Intent(getContext(), PlayRecordActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name",readLoginInfo());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Toast.makeText(mContext,"请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        settingLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isLogin){
                    Intent intent=new Intent(mContext,SettingActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(mContext,LoginActivity.class);
                    startActivityForResult(intent,1);
                }
            }
        });


        return view;
    }
    private void setUsername(boolean isLogin) {
        if(isLogin){
            tvUsername.setText(readLoginInfo());
        }else{
            tvUsername.setText("点击登录");
        }
    }
    private String readLoginInfo(){
        SharedPreferences sp=mContext.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        return sp.getString("loginUser","");
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==1&&resultCode== Activity.RESULT_OK&&data!=null){
            isLogin=data.getBooleanExtra("isLogin",false);
            setUsername(isLogin);
        }
    }
}
