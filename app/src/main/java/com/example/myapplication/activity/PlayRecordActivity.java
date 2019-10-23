package com.example.myapplication.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.helloworld.R;
import com.example.myapplication.adapter.RecordRecyclerAdapter;
import com.example.myapplication.entity.Records;
import com.example.myapplication.service.impl.RecordService;
import com.example.myapplication.service.impl.RecordServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class PlayRecordActivity extends AppCompatActivity {
    private List<Records> recordsList = new ArrayList<>();
    private Records records;
    private RecordService recordService;
    private String username;
    private int mPosition;

    private LinearLayout nullContent;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private RecordRecyclerAdapter adapter;

    public RecordRecyclerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecordRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_record);
        initToolbar();

        initView();
        initData(recordsList);
//        initRecord();
    }

//    private void initRecord() {
//        recyclerView.setVisibility(View.VISIBLE);
//        nullContent.setVisibility(View.GONE);
//        Records records1 = new Records(1,"123","484848");
//        recordsList.add(records1);
//        Records records2 = new Records(2,"456","454654");
//        recordsList.add(records2);
//    }

    private void initView() {
        nullContent = findViewById(R.id.null_content);
        recyclerView = findViewById(R.id.rv_record);
        registerForContextMenu(recyclerView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("name");
        }
        recordService = new RecordServiceImpl(PlayRecordActivity.this);
        recordsList = recordService.get(username);
        adapter = new RecordRecyclerAdapter(recordsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecordRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                mPosition = position;
                recyclerView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                        getMenuInflater().inflate(R.menu.context, contextMenu);
                    }
                });
            }
        });
    }

    public boolean onContextItemSelected(MenuItem item) {

        switch ((item.getItemId())) {
            case R.id.item_add:
                Toast.makeText(PlayRecordActivity.this, "增加", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_record:
                Toast.makeText(PlayRecordActivity.this, "记录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_remove:
                AlertDialog.Builder dialog = new AlertDialog.Builder(PlayRecordActivity.this);
                dialog.setTitle("提示框");
                dialog.setMessage("是否要删除这条播放记录");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!recordsList.isEmpty()) {
                            Log.i("playRecord", "id号：" + recordsList.get(mPosition).getId());
                            recordService = new RecordServiceImpl(PlayRecordActivity.this);
                            recordService.remove(recordsList.get(mPosition));
                            recordsList.remove(mPosition);
                            adapter.notifyItemRemoved(mPosition);
                            adapter.notifyItemRangeChanged(mPosition, recordsList.size() - mPosition);
                        }
                        List<Records> newrecordsList = new ArrayList<>();
                        newrecordsList = recordService.get(readLoginInfo());
                        initData(newrecordsList);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(PlayRecordActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_clear:
                clear();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clear() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PlayRecordActivity.this);
        dialog.setTitle("提示框");
        dialog.setMessage("是否要删除全部的播放记录");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!recordsList.isEmpty()) {
                    recordService.removeAll(recordsList.get(mPosition));
                }
                List<Records> newrecordsList1 = new ArrayList<>();
                newrecordsList1 = recordService.get(readLoginInfo());
                initData(newrecordsList1);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(PlayRecordActivity.this, "已取消", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void initData(List<Records> records) {
        if (records.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            nullContent.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            nullContent.setVisibility(View.GONE);
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("播放记录");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayRecordActivity.this.finish();
            }
        });

    }

    private String readLoginInfo() {
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        return sp.getString("loginUser", "");
    }
}