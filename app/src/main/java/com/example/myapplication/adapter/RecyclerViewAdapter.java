package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.helloworld.R;
import com.example.myapplication.entity.Exercise;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Exercise> exercises;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvtvOder,tvTitle,tvSubTitle;

        public ViewHolder(View view){
            super(view);
            tvtvOder= view.findViewById(R.id.tv_order);
            tvTitle= view.findViewById(R.id.tv_title);
            tvSubTitle= view.findViewById(R.id.tv_sub_title);
        }
    }
    public RecyclerViewAdapter(List<Exercise> exerList){
        exercises=exerList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercise , parent ,false);
        ViewHolder holder = new ViewHolder(view);
        //设置Item点击的监听器
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Exercise exercise= exercises.get(position);
        holder.tvTitle.setText(exercise.getTitle());
        holder.tvtvOder.setText(String.valueOf(position+1));
        holder.tvSubTitle.setText(exercise.getSubTitle());
//        //设置圆角背景的颜色
//        GradientDrawable drawable=(GradientDrawable) holder.tvtvOder.getBackground();
//        drawable.setColor(Color.parseColor(exercise.getBgColor()));
        //设置监听
        if(itemClickLisrener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickLisrener.onItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemClickLisrener.onItemLongClick(holder.itemView,position);
                    return true;
                }
            });
        }


    }
    private OnItemClickLisrener itemClickLisrener;
    public void setOnItemClickListener(OnItemClickLisrener listener){
        this.itemClickLisrener=listener;
    }
    //回调事件的接口
    public interface  OnItemClickLisrener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int positon);
    }
    @Override
    public int getItemCount() {
        return exercises.size();
    }

}
