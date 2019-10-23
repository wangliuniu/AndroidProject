package com.example.myapplication.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.alibaba.fastjson.JSON;
import com.example.helloworld.R;
import com.example.myapplication.Util.IOUtils;
import com.example.myapplication.Util.SharedUtils;
import com.example.myapplication.adapter.CourseRecyclerAdapter;
import com.example.myapplication.adapter.VideoAdapter;
import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.Records;
import com.example.myapplication.entity.Video;
import com.example.myapplication.service.impl.RecordService;
import com.example.myapplication.service.impl.RecordServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CourseVideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private ImageView ivVideo;
    private TextView tvIntro;
    private RecyclerView rvVideo;

    private Course course;
    private List<Video> videos;
    private VideoAdapter adapter;

    private MediaController controller;  //多媒体播放进度条控制
    private MediaPlayer mediaPlayer;
    private String title;


    private Records records;
    private RecordService recordService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        //1.接收从上一个界面传递的Bundle对象

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            course =(Course)bundle.getSerializable("course");
        }

        title = course.getTitle();
        initData();
        initView();
        loadFirstFrame();
    }
    // 播放视频
    private void play() {
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.video101;
        videoView.setVideoPath(uri);
        videoView.start();
    }
    private void initView() {
        videoView =findViewById(R.id.video_view);
        controller=new MediaController(this);
        videoView.setMediaController(controller);

        ivVideo=findViewById(R.id.iv_video);
        tvIntro=findViewById(R.id.tv_intro);
        rvVideo=findViewById(R.id.rv_video);

        tvIntro.setText(course.getIntro());

        adapter = new VideoAdapter(videos);
        rvVideo.setLayoutManager(new LinearLayoutManager(this));
        rvVideo.setAdapter(adapter);




        adapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //设置选中项，并通过notifyDataSetChanged()更新UI
                adapter.setSelected(position);
                adapter.notifyDataSetChanged();//更新UI


                recordService = new RecordServiceImpl(CourseVideoActivity.this);
                records = new Records();
                records.setUsername(readLoginInfo());
                records.setTitle(title);
                recordService.save(records);


                //获取Video对象的数据，并初始化VideoView
                Video video=videos.get(position);
                if(videoView.isPlaying()) {
                    videoView.stopPlayback();
                }

                if(TextUtils.isEmpty(video.getVideoPath())) {
                    Toast.makeText(CourseVideoActivity.this, "本地没有此视频，暂时无法播放", Toast.LENGTH_SHORT).show();
                    return;
                }
                videoView.setVisibility(View.VISIBLE);
                ivVideo.setVisibility(View.GONE);
                String uri = "android.resource://" + getPackageName() + "/" +R.raw.video101;
                videoView.setVideoPath(uri);
                play();

//                if(SharedUtils.isLogin(CourseVideoActivity.this, "isLogin")) {
//                    String username = SharedUtils.readValue(CourseVideoActivity.this, "loginUser");
//                    PlayListDao.getInstance(CourseVideoActivity.this).save(video, username);
//                }
            }
        });




    }

    private void initData() {
        // 接收上一个界面的数据
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            course = (Course) bundle.getSerializable("course");
            //2.从json文件中获取视频的描述数据
            videos =new ArrayList<>();
            try{
                //2.1获取json文件中的所有数据集合
                InputStream is =getResources().getAssets().open("course.json");
                String json= IOUtils.convert(is, StandardCharsets.UTF_8);
                videos= JSON.parseArray(json,Video.class);

                //2.2筛选出course的chapterID对应的视频集合
                Iterator<Video> it=videos.iterator();
                while (it.hasNext()){
                    Video video=it.next();
                    if (video.getChapterId()!=course.getId()){
                        it.remove();
                    }
                }
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }



    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView != null) {
            videoView.stopPlayback();
            videoView = null;
        }
    }
    //加载视频的首帧图像
    private void  loadFirstFrame() {
        Bitmap bitmap=null;
        Uri uri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video101);
        MediaMetadataRetriever retriever=new MediaMetadataRetriever();
        retriever.setDataSource(this,uri);
        bitmap=retriever.getFrameAtTime();
        ivVideo.setImageBitmap(bitmap);

    }

    private String readLoginInfo() {
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        return sp.getString("loginUser", "");
    }



}
