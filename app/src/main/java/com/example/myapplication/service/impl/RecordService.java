package com.example.myapplication.service.impl;

import com.example.myapplication.entity.Records;

import java.util.List;

public interface RecordService {
    List<Records> get(String username);
    void save(Records records);
    void modify(Records records);
    void remove(Records records);
    void removeAll(Records records);
}