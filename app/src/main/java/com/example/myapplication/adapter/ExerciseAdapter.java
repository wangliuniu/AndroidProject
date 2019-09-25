package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.helloworld.R;
import com.example.myapplication.entity.Exercise;

import java.util.List;

public class ExerciseAdapter extends BaseAdapter {
    private List<Exercise> exercises;
    private Context context;
    public ExerciseAdapter(Context context,List<Exercise> exercises){
        this.context=context;
        this.exercises=exercises;
    }

   //list的总记录数，若为0的话，即使有数据也不会显示
    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Exercise getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
//根据记录数，加载每个view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //1.加载xml布局，只需要加载一次
        if(convertView ==null){
            holder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_exercise,null);
            //2.获取控件
            holder.tvtvOder=convertView.findViewById(R.id.tv_order);
            holder.tvTitle=convertView.findViewById(R.id.tv_title);
            holder.tvSubTitle=convertView.findViewById(R.id.tv_sub_title);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }


        //3.加载数据
        final  Exercise exercise=getItem(position);
        if(exercise!=null){
            holder.tvtvOder.setText(String.valueOf(position+1));
            holder.tvtvOder.setBackgroundResource(R.drawable.circle);
            holder.tvTitle.setText(exercise.getTitle());
            holder.tvSubTitle.setText(exercise.getSubTitle());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        return convertView;
    }
    //存放item_exeercise布局中所有的控件
    static class ViewHolder{
        TextView tvtvOder,tvTitle,tvSubTitle;
    }
}
