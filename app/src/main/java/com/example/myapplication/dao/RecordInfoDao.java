package com.example.myapplication.dao;

import com.example.myapplication.entity.Records;

import java.util.List;

public interface RecordInfoDao {
    List<Records> select(String username);
    void insert(Records records);
    void update(Records records);
    void delete(Records records);
    void deleteAll(Records records);
}