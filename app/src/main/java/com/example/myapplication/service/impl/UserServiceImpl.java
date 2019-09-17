package com.example.myapplication.service.impl;

import android.content.Context;

import com.example.myapplication.dao.UserInfoDao;
import com.example.myapplication.entity.User;

public class UserServiceImpl implements UserService {
    private UserInfoDao dao;
    public UserServiceImpl(Context context){
        dao=new UserInfoDaoImpl(context);
    }
    @Override
    public User get(String username) {
        return dao.select(username);
    }

    @Override
    public void save(User user) {
    dao.insert(user);
    }

    @Override
    public void modify(User user) {
    dao.update(user);
    }
}
