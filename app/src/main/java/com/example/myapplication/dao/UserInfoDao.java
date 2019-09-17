package com.example.myapplication.dao;

import java.util.List;
import com.example.myapplication.entity.User;

public interface UserInfoDao {
    List<User> select();
    User select(String name);
    void insert(User user);
    void update(User user);
    void delete(User user);

}
