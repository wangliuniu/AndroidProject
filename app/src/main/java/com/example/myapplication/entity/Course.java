package com.example.myapplication.entity;

import java.io.Serializable;

public class Course implements Serializable {
    private int id;
    private String imgTitle;//图片上的标题
    private String title; //章节标题
    private String intro;  //视频简介


    public Course() {
    }
    public Course(int id, String imgTitle, String title, String intro) {
        this.id = id;
        this.imgTitle = imgTitle;
        this.title = title;
        this.intro = intro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
