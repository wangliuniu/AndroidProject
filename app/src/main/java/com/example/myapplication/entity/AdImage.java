package com.example.myapplication.entity;

import java.io.Serializable;

public class AdImage implements Serializable {
    private int id;
    private String img;  //图片名称
    private String desc;//图片的描述


    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AdImage{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    public AdImage() {
    }

    public AdImage(int id, String img, String desc) {
        this.id = id;
        this.img = img;
        this.desc = desc;
    }
}
