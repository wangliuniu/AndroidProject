package com.example.myapplication.entity;

import java.io.Serializable;

public class Records implements Serializable {
    private int id;
    private String username;
    private String title;

    public Records() {
    }

    public Records(int id, String username, String title) {
        this.id = id;
        this.username = username;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Records{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

}