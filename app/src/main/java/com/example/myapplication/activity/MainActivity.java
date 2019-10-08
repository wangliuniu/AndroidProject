package com.example.myapplication.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.helloworld.R;

import com.example.myapplication.entity.Exercise;
import com.example.myapplication.fragment.CourseFragment;
import com.example.myapplication.fragment.MySettingFragment;
import com.example.myapplication.fragment.PractiseFragment;
import com.example.myapplication.fragment.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RadioGroup group;
    private List<Exercise> exercises=new ArrayList<>();
    //定义标题的集合
    private SparseArray<String> titles;
    private SparseArray<Fragment> fragment;
    private void initFragment(){
        fragment=new SparseArray<>();
        fragment.put(R.id.btn_my, MySettingFragment.newInstance());
//        fragment.put(R.id.btn_execise, PractiseFragment.newInstance("Activity向Fragment传值"));
        fragment.put(R.id.btn_execise, RecyclerViewFragment.newInstance("Activity向Fragment传值"));
        fragment.put(R.id.btn_course, CourseFragment.newInstance());
        replaceFragment(fragment.get(R.id.btn_my));
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction ft=manager.beginTransaction();
        ft.replace(R.id.main_body,fragment);
        ft.addToBackStack(null);
        ft.commit();

    }
    private void initTitles(){
        titles = new SparseArray<>();
        titles.put(R.id.btn_course , "课程");
        titles.put(R.id.btn_execise , "练习");
        titles.put(R.id.btn_message , "资讯");
        titles.put(R.id.btn_my , "我的");
    }
    private void setToolbar(int checkedId) {
        if (checkedId == R.id.btn_my){
            toolbar.setVisibility(View.GONE);
        }else {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setTitle(titles.get(checkedId));
        }
    }

    private void initView(){
        group = findViewById(R.id.btn_group);
//        toolbar = findViewById(R.id.title_toolbar);
//        setToolbar(group.getCheckedRadioButtonId());

        // RadioGroup的选项改变事件的监听
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                Toast.makeText(MainActivity.this , titles.get(checkedId) , Toast.LENGTH_SHORT).show();
//                setToolbar(checkedId);
                replaceFragment(fragment.get(checkedId));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        initView();
//        initTitles();


    }

}