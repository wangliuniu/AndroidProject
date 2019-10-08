package com.example.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.activity.ExerciseDetailActivity;
import com.example.myapplication.entity.Exercise;
import com.example.myapplication.entity.ExerciseDetail;

import com.example.helloworld.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDetailAdapter extends RecyclerView.Adapter<ExerciseDetailAdapter.ViewHolder> {
    private List<ExerciseDetail> details;
    private List<String> selectedPos;//记录点击位置
    private OnSelectListener onSelectListener;//坚挺选项的选择时间，用于修改相应的图标

    static class ViewHolder extends RecyclerView.ViewHolder{
     TextView subject,tvA,tvB,tvC,tvD;
     ImageView ivA,ivB,ivC,ivD;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject =itemView.findViewById(R.id.tv_subject);
            tvA=itemView.findViewById(R.id.tv_a);
            tvB=itemView.findViewById(R.id.tv_b);
            tvC=itemView.findViewById(R.id.tv_c);
            tvD=itemView.findViewById(R.id.tv_d);
            ivA=itemView.findViewById(R.id.iv_a);
            ivB=itemView.findViewById(R.id.iv_b);
            ivC=itemView.findViewById(R.id.iv_c);
            ivD=itemView.findViewById(R.id.iv_d);

        }
    }
    //
    public interface OnSelectListener{
        void onSelectA(int position,ImageView ivA,ImageView ivB,ImageView ivC,ImageView ivD);
        void onSelectB(int position,ImageView ivA,ImageView ivB,ImageView ivC,ImageView ivD);
        void onSelectC(int position,ImageView ivA,ImageView ivB,ImageView ivC,ImageView ivD);
        void onSelectD(int position,ImageView ivA,ImageView ivB,ImageView ivC,ImageView ivD);
    }
    //参数：习题的集合，监听器（由activity实现）
    public ExerciseDetailAdapter(List<ExerciseDetail> details,OnSelectListener onSelectListener){
        this.details=details;
        selectedPos=new ArrayList<>();
        this.onSelectListener=onSelectListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_exercise_detail,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExerciseDetailAdapter.ViewHolder viewHolder, final int positon) {
        ExerciseDetail detail = details.get(positon);
        viewHolder.subject.setText(detail.getSubject());
        viewHolder.tvA.setText(detail.getA());
        viewHolder.tvB.setText(detail.getB());
        viewHolder.tvC.setText(detail.getC());
        viewHolder.tvD.setText(detail.getD());

        viewHolder.ivA.setImageResource(R.drawable.ic_exercise_a);
        viewHolder.ivB.setImageResource(R.drawable.ic_exercise_b);
        viewHolder.ivC.setImageResource(R.drawable.ic_exercise_c);
        viewHolder.ivD.setImageResource(R.drawable.ic_exercise_d);

        viewHolder.ivA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String pos = String.valueOf(positon);
                if (selectedPos.contains(pos)) {
                    selectedPos.remove(pos);
                } else {
                    selectedPos.add(pos);
                }
                onSelectListener.onSelectA(positon, viewHolder.ivA, viewHolder.ivB, viewHolder.ivC, viewHolder.ivD);
            }
        });
        viewHolder.ivB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = String.valueOf(positon);
                if (selectedPos.contains(pos)) {
                    selectedPos.remove(pos);
                } else {
                    selectedPos.add(pos);
                }
                onSelectListener.onSelectB(positon, viewHolder.ivA, viewHolder.ivB, viewHolder.ivC, viewHolder.ivD);

            }
        });
        viewHolder.ivC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = String.valueOf(positon);
                if (selectedPos.contains(pos)) {
                    selectedPos.remove(pos);
                } else {
                    selectedPos.add(pos);
                }
                onSelectListener.onSelectC(positon, viewHolder.ivA, viewHolder.ivB, viewHolder.ivC, viewHolder.ivD);

            }
        });
        viewHolder.ivD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = String.valueOf(positon);
                if (selectedPos.contains(pos)) {
                    selectedPos.remove(pos);
                } else {
                    selectedPos.add(pos);
                }
                onSelectListener.onSelectD(positon, viewHolder.ivA, viewHolder.ivB, viewHolder.ivC, viewHolder.ivD);

            }
        });
    }
    @Override
    public int getItemCount() {

        return details.size();
    }
}
