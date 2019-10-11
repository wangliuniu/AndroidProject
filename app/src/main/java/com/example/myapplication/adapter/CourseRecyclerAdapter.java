package com.example.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helloworld.R;
import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.Exercise;

import java.util.ArrayList;
import java.util.List;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> {
    private List<Course> courses;
    private  List<Integer> imgIds;

    public CourseRecyclerAdapter(List<Course> courses) {
      this.courses=courses;
      setImgIds();
    }

    private void setImgIds() {
      imgIds= new ArrayList<>();
      imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);
        imgIds.add(R.drawable.img_chapter_1);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder,final int i) {
       Course course =courses.get(i);
       viewHolder.ivImg.setImageResource(imgIds.get(i));
       viewHolder.tvTitle.setText(course.getTitle());

       if (onItemClickListenner !=null){
           viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onItemClickListenner.onItemClick(viewHolder.itemView,i);
               }
           });
       }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }



    static class  ViewHolder extends RecyclerView.ViewHolder{
      ImageView ivImg;
      TextView tvTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImg=itemView.findViewById(R.id.iv_img);
            tvTitle=itemView.findViewById(R.id.tv_title);
        }
    }
    private OnItemClickListenner onItemClickListenner;
   public void setOnItemClickListenner(OnItemClickListenner onItemClickListenner ){
        this.onItemClickListenner = (OnItemClickListenner) onItemClickListenner;
   }
  public interface OnItemClickListenner{
        void onItemClick(View view,int position);
  }


}
