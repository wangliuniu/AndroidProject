package com.example.myapplication.entity;

import java.io.Serializable;

public class Video implements Serializable {
    private int ChapterId;
    private int videoId;
    private String title;
    private String VideoTitle;
    private String VideoPath;

    public Video() {
    }



    @Override
    public String toString() {
        return "Video{" +
                "ChapterId=" + ChapterId +
                ", videoId=" + videoId +
                ", VideoPath='" + VideoPath + '\'' +
                ", title='" + title + '\'' +
                ", VideoTitle='" + VideoTitle + '\'' +
                '}';
    }

    public int getChapterId() {
        return ChapterId;
    }

    public void setChapterId(int chapterId) {
        ChapterId = chapterId;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getVideoPath() {
        return VideoPath;
    }

    public void setVideoPath(String videoPath) {
        VideoPath = videoPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        VideoTitle = videoTitle;
    }



    public Video(int chapterId, int videoId, String videoPath, String title, String videoTitle) {
        ChapterId = chapterId;
        this.videoId = videoId;
        VideoPath = videoPath;
        this.title = title;
        VideoTitle = videoTitle;
    }



}



