package com.example.myapplication.service.impl;

import android.content.Context;

import com.example.myapplication.dao.RecordInfoDao;
import com.example.myapplication.dao.RecordInfoDaoImpl;
import com.example.myapplication.entity.Records;

import java.util.List;

public class RecordServiceImpl implements RecordService {
    private RecordInfoDao dao;

    public RecordServiceImpl(Context context) {
        dao = new RecordInfoDaoImpl(context);
    }

    @Override
    public List<Records> get(String username) {
        return dao.select(username);
    }

    @Override
    public void save(Records records) {
        dao.insert(records);
    }

    @Override
    public void modify(Records records) {
        dao.update(records);
    }

    @Override
    public void remove(Records records) {
        dao.delete(records);
    }

    @Override
    public void removeAll(Records records) {
        dao.deleteAll(records);
    }
}