package com.example.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class AdViewPageAdapter extends PagerAdapter {
   private List<ImageView> imageViews;

    public AdViewPageAdapter(List<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    public AdViewPageAdapter() {
        this(null);
        imageViews =new ArrayList<>();
    }



    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    //防止刷新时显示缓存数据
    @Override
    public int getItemPosition(@NonNull Object object){
        return POSITION_NONE;
    }
    //返回数据集的真实容量大小
    public int getSize(){
        return imageViews.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
   @NonNull
   @Override
    public Object instantiateItem(@NonNull ViewGroup container,int position){
        //container 容器相当于用来存放imageView

       //从集合中获取图片
        ImageView imageView=imageViews.get(position%imageViews.size());

        //检查imageview是否已经添加到容器中
       ViewParent parent =imageView.getParent();
       if (parent !=null){
           ((ViewGroup) parent).removeView(imageView);
       }
       //图片添加到container
       container.addView(imageView);
       //把图片返回给框架，用来缓存
       return  imageView;
   }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
