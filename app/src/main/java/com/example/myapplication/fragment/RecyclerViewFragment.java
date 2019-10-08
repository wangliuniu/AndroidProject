package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.helloworld.R;
import com.example.myapplication.activity.ExerciseDetailActivity;
import com.example.myapplication.adapter.ExerciseAdapter;

import com.example.myapplication.adapter.RecyclerViewAdapter;
import com.example.myapplication.entity.Exercise;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecyclerViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";


    private List<Exercise> exercises;
    // TODO: Rename and change types of parameters
    private String mParam;


    private OnFragmentInteractionListener mListener;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }


    public static RecyclerViewFragment newInstance(String param) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //1.初始化数据
        initData();
        //2.获取控件
        View view = inflater.inflate(R.layout.fragment_recycler_view,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        //3.设置布局和分割线
        LinearLayoutManager manager = new LinearLayoutManager(container.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(container.getContext(),
                DividerItemDecoration.VERTICAL));
        //4.创建适配器
        RecyclerViewAdapter adapter =new RecyclerViewAdapter(exercises);
        //5.设置适配器
        recyclerView.setAdapter(adapter);
        //6.设置监听器
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickLisrener() {
            @Override
            public void onItemClick(View view, int position) {
                Exercise exercise=exercises.get(position);
                //跳转相应的章节习题
                Intent intent=new Intent(getContext(), ExerciseDetailActivity.class);
                intent.putExtra("id",exercise.getId());//用于识别是哪个xml文件
                intent.putExtra("title",exercise.getTitle());//用于设置标题栏
                getContext().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int positon) {

            }
        });
        return view;
    }

    private void initData() {
        exercises=new ArrayList<>();
        try{
            //1.从assets目录中获取资源的输入流
            InputStream input = getResources().getAssets().open("exercise_title.json");

        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i=0;i<15;i++){
            Exercise exercise=new Exercise();
            exercise.setId(i+1);
            switch (i){
                case 0:
                    exercise.setTitle("第一章 安卓基础入门");
                    exercise.setSubTitle("共计5题");
                    exercise.setBackground(R.drawable.circle);
                    break;
                case 1:
                    exercise.setTitle("第二章 安卓基础入门");
                    exercise.setSubTitle("共计5题");
                    exercise.setBackground(R.drawable.circle);
                    break;
                case 2:
                    exercise.setTitle("第三章 安卓基础入门");
                    exercise.setSubTitle("共计5题");
                    exercise.setBackground(R.drawable.circle);
                    break;
                default:
                    exercise.setTitle("第四章 安卓基础入门");
                    exercise.setSubTitle("共计5题");
                    exercise.setBackground(R.drawable.circle);
                    break;
            }
            exercises.add(exercise);
        }
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
