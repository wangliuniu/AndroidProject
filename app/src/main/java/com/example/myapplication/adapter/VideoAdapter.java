package com.example.myapplication.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import com.example.helloworld.R;

import com.example.myapplication.entity.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Video> videos;

    private int selected=-1;
    private OnItemClickListener itemClickListener;
    public VideoAdapter(List<Video> videos) {
        this.videos = videos;
        this.selected = -1;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_video,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Video video = videos.get(i);
        viewHolder.ivIcon.setImageResource(R.drawable.ic_play);
        viewHolder.tvTitle.setText(video.getVideoTitle());
        // 改变选中项的图标和文本颜色
        if(selected == i) {
            viewHolder.ivIcon.setImageResource(R.drawable.ic_video_play);
            viewHolder.tvTitle.setTextColor(Color.parseColor("#009958"));
        } else {
            viewHolder.ivIcon.setImageResource(R.drawable.ic_play);
            viewHolder.tvTitle.setTextColor(Color.parseColor("#333333"));
        }

        // 设置选项监听
        if (itemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(viewHolder.itemView, i);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivIcon = itemView.findViewById(R.id.iv_play);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}