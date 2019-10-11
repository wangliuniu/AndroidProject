package com.example.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.example.helloworld.R;
import com.example.myapplication.Util.IOUtils;
import com.example.myapplication.adapter.AdViewPageAdapter;

import com.example.myapplication.adapter.CourseRecyclerAdapter;
import com.example.myapplication.entity.AdImage;
import com.example.myapplication.entity.Course;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment implements ViewPager.OnPageChangeListener {
    public static final int MSG_AD_ID =1;

    private ViewPager viewPager;
    private TextView tvDesc; //图片的描述
    private LinearLayout llPoint; //指示器的布局

    private List<AdImage> adImages; //数据
    private List<ImageView> imageViews;//图片的集合
    private int lastPos;//之前的位置
   private AdHandler adhandler;


   private RecyclerView rvCourse;
    private List<Course> courses;



   private static class AdHandler extends Handler {
       private WeakReference<ViewPager> reference;




           public AdHandler(ViewPager viewPager) {
               reference = new WeakReference<>(viewPager);
           }
           @Override
           public void handleMessage(Message msg) {
               super.handleMessage(msg);
               ViewPager viewPager = reference.get();
               if (viewPager == null) {
                   return;
               }
               if (msg.what == MSG_AD_ID) {
                   viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                   sendEmptyMessageDelayed(MSG_AD_ID, 5000);
               }
           }
       }

    public CourseFragment(){

    }
    public static  CourseFragment fragment;
    public  static  CourseFragment newInstance(){
        if (fragment==null){
            fragment=new CourseFragment();
        }
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course,container,false);

        initAdData();
        initAdView(view);
        initIndicator(view);


        lastPos=0;
        llPoint.getChildAt(0).setEnabled(true);
        tvDesc.setText(adImages.get(0).getDesc());
        viewPager.setAdapter(new AdViewPageAdapter(imageViews));
        adhandler=new AdHandler(viewPager);
        adhandler.sendEmptyMessageDelayed(MSG_AD_ID,5000);
        initCourses();
        initCourseView(view);

        return view;


    }

    private void initCourses() {
        courses =new ArrayList<>();
        try{
            InputStream is=getResources().getAssets().open("chapter_intro.json");
            String json= IOUtils.convert(is, StandardCharsets.UTF_8);
            courses = JSON.parseArray(json, Course.class);
        }catch(IOException e){
            e.printStackTrace();
        }


    }

    private void initCourseView(View view) {
        rvCourse=view.findViewById(R.id.rv_courses);
        CourseRecyclerAdapter adapter=new CourseRecyclerAdapter(courses);
        rvCourse.setLayoutManager(new GridLayoutManager(getContext(),2));
        rvCourse.setAdapter(adapter);
        adapter.setOnItemClickListenner(new CourseRecyclerAdapter.OnItemClickListenner() {
            @Override
            public void onItemClick(View view, int position) {
                Course course=courses.get(position);
                Toast.makeText(getContext(),"点击了："+course.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initAdData() {
        adImages =new ArrayList<>();
        for(int i=0;i<3;i++){
           AdImage adImage=new AdImage();
           adImage.setId(i+1);
           switch (i){
               case 0:
                   adImage.setImg("banner_1");
                   adImage.setDesc("新一代Apple Watch发布");
                   break;
               case 1:
                   adImage.setImg("banner_2");
                   adImage.setDesc("寒武纪发布AI芯片");
                   break;
               case 2:
                   adImage.setImg("banner_3");
                   adImage.setDesc("Google发布AI语音助手");
                   break;
                   default:
                       break;
           }
           adImages.add(adImage);
        }
    }
    private void initAdView(View view){
    tvDesc=view.findViewById(R.id.tv_desc);
    viewPager =view.findViewById(R.id.vp_banner);
    viewPager.addOnPageChangeListener(this);

    imageViews=new ArrayList<>();
    for(int i=0;i<adImages.size();i++){
        AdImage adImage=adImages.get(i);

    ImageView iv=new ImageView(getContext());
    if("banner_1".equals(adImage.getImg())){
        iv.setBackgroundResource(R.drawable.banner_1);
    }else if("banner_2".equals(adImage.getImg())){
        iv.setBackgroundResource(R.drawable.banner_2);
    }else if("banner_3".equals(adImage.getImg())){
        iv.setBackgroundResource(R.drawable.banner_3);
    }
    imageViews.add(iv);
    }
    }
    private void initIndicator(View view){
    llPoint=view.findViewById(R.id.ll_point);
    View pointView;
    for(int i=0;i<adImages.size();i++){
        pointView=new View(getContext());
        pointView.setBackgroundResource(R.drawable.indicator_bg);
        pointView.setEnabled(false);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(16,16);
        if(i!=0){
            params.leftMargin=10;
        }
        llPoint.addView(pointView,params);
    }



    }
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }


    @Override
    public void onPageSelected(int i) {
    int currentPos=i %adImages.size();
     tvDesc.setText(adImages.get(currentPos).getDesc());

     llPoint.getChildAt(lastPos).setEnabled(false);
     llPoint.getChildAt(currentPos).setEnabled(true);
     lastPos=currentPos;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
    /*
     * 使用多线程实现广告自动切换
     * */
    private class AdSlideThread extends Thread{
        @Override
        public void run(){
            super.run();
            while (true){
                try {
                    sleep(5000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                if (adhandler !=null){
                    adhandler.sendEmptyMessage(MSG_AD_ID);
                }

            }
        }
    }

}


