package com.example.myapplication.service.impl;

import com.example.myapplication.entity.User;

public interface UserService {

User get(String username);
void save(User user);
void modify(User user);




}
