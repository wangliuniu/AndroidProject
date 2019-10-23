package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.Util.DBHelp2;
import com.example.myapplication.entity.Records;

import java.util.ArrayList;
import java.util.List;

public class RecordInfoDaoImpl implements RecordInfoDao {
    private DBHelp2 helper;
    private SQLiteDatabase db;

    public RecordInfoDaoImpl(Context context){
        helper = DBHelp2.getInstance(context);
    }

    @Override
    public List<Records> select(String username) {
        List<Records> recordsList = new ArrayList<Records>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(DBHelp2.TBL_NAME_RECORD,null,"user_name=?",
                new String[]{username},null,null,null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            username = cursor.getString(cursor.getColumnIndex("user_name"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            Records records = new Records();
            records.setId(id);
            records.setUsername(username);
            records.setTitle(title);
            recordsList.add(records);
        }
        db.close();
        return recordsList;
    }

    @Override
    public void insert(Records records) {

        db = helper.getWritableDatabase();
        String sql = "insert into tb_record(user_name,title) values(?,?)";
        db.execSQL(sql, new String[]{records.getUsername(),records.getTitle()});
        db.close();
    }

    @Override
    public void update(Records records) {
        ContentValues values = new ContentValues();
        values.put("user_name",records.getUsername());
        values.put("title",records.getTitle());

        db = helper.getWritableDatabase();
        db.update(DBHelp2.TBL_NAME_RECORD,values,"user_name = ?",new String[]{records.getUsername()});
        db.close();
    }

    @Override
    public void delete(Records records) {
        db = helper.getWritableDatabase();
        db.execSQL("delete from tb_record where  id = ? ",new String[]{String.valueOf(records.getId())});
        db.close();
        Log.i("RecordInfoDaoImpl", "已删除");
    }

    @Override
    public void deleteAll(Records records) {
        db = helper.getWritableDatabase();
        db.execSQL("delete from tb_record where  user_name = ? ",new String[]{records.getUsername()});
        db.close();
        Log.i("RecordInfoDaoImpl", "已删除");
    }
}