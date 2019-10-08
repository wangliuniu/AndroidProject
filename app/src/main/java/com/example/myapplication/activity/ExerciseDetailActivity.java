package com.example.myapplication.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.adapter.ExerciseDetailAdapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helloworld.R;

import com.example.myapplication.Util.IOUtils;
import com.example.myapplication.adapter.ExerciseDetailAdapter;
import com.example.myapplication.entity.ExerciseDetail;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDetailActivity extends AppCompatActivity implements ExerciseDetailAdapter.OnSelectListener {
   private int id;
   private String title;
   private List<ExerciseDetail> details;

   private RecyclerView lvDetails;
   private ExerciseDetailAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recycler_view);
        initData();
        initView();
    }
public void initData(){
      id =getIntent().getIntExtra("id",0);
      title=getIntent().getStringExtra("title");

      details=new ArrayList<>();
      try{
          InputStream is=getResources().getAssets().open("chapter"+id+".xml");
          details= IOUtils.getXmlContents(is);
          is.close();
      }catch (Exception e){
          e.printStackTrace();
      }

}
public void initView(){
    lvDetails=findViewById(R.id.recycle_view);
    LinearLayoutManager manager=new LinearLayoutManager(this);
    lvDetails.setLayoutManager(manager);
    adapter=new ExerciseDetailAdapter(details,this);
    lvDetails.setAdapter(adapter);

    //增加标题
//    TextView title=new TextView(this);
//    title.setTextColor(Color.parseColor("#000000"));
//    title.setText("一，选择题");
//    title.setPadding(10,15,0,0);
//    lvExercises.addHeaderView(title);

}
public void onSelectA(int position, ImageView ivA,ImageView ivB,ImageView ivC,ImageView ivD){
   ExerciseDetail detail=details.get(position);
   if(detail.getAnswer() !=1){
       detail.setSelect(1);
   }else{
       detail.setSelect(0);
   }
   switch (detail.getAnswer()){
       case 1:
           ivA.setImageResource(R.drawable.ic_exercise_answer_right);
           break;
       case 2:
           ivB.setImageResource(R.drawable.ic_exercise_answer_error);
           ivA.setImageResource(R.drawable.ic_exercise_answer_right);
           break;
       case 3:
           ivC.setImageResource(R.drawable.ic_exercise_answer_error);
           ivA.setImageResource(R.drawable.ic_exercise_answer_right);
           break;
       case 4:
           ivD.setImageResource(R.drawable.ic_exercise_answer_error);
           ivA.setImageResource(R.drawable.ic_exercise_answer_right);
           break;
   }

}

    @Override
    public void onSelectB(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        ExerciseDetail detail=details.get(position);
        if(detail.getAnswer() !=2){
            detail.setSelect(2);
        }else{
            detail.setSelect(0);
        }
        switch (detail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 2:
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 3:
                ivC.setImageResource(R.drawable.ic_exercise_answer_error);
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 4:
                ivD.setImageResource(R.drawable.ic_exercise_answer_error);
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
        }
    }

    @Override
    public void onSelectC(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        ExerciseDetail detail=details.get(position);
        if(detail.getAnswer() !=3){
            detail.setSelect(3);
        }else{
            detail.setSelect(0);
        }
        switch (detail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.ic_exercise_answer_right);
                ivB.setImageResource(R.drawable.ic_exercise_answer_error);
                break;
            case 2:
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                ivC.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 3:
                ivC.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 4:
                ivD.setImageResource(R.drawable.ic_exercise_answer_error);
                ivC.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
        }
    }

    @Override
    public void onSelectD(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        ExerciseDetail detail=details.get(position);
        if(detail.getAnswer() !=4){
            detail.setSelect(4);
        }else{
            detail.setSelect(0);
        }
        switch (detail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 2:
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 3:
                ivB.setImageResource(R.drawable.ic_exercise_answer_error);
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 4:
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
        }
    }
}
