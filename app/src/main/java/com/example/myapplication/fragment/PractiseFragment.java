package com.example.myapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.helloworld.R;
import com.example.myapplication.adapter.ExerciseAdapter;
import com.example.myapplication.entity.Exercise;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PractiseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PractiseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PractiseFragment extends Fragment {

    private static final String ARG_PARAM = "param";



    private String mParam;
    private  ListView lvExecise;
    private List<Exercise> exercises;


    private OnFragmentInteractionListener mListener;

    public PractiseFragment() {
        // Required empty public constructor
    }


    public static PractiseFragment newInstance(String param) {
        PractiseFragment fragment = new PractiseFragment();
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
    String[] data={"软件1721","软件1731","软件1741","软件1713","软件1761","软件1751"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_practise, container, false);
        //增加获取控件的语句，与Activity的onCreat()的功能类似
        initData();
        lvExecise=view.findViewById(R.id.list_view);

//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
//                getActivity(),android.R.layout.simple_list_item_1,data);
        ExerciseAdapter adapter=new ExerciseAdapter(getContext(),exercises);
        lvExecise.setAdapter(adapter);
        lvExecise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise exercise= (Exercise) parent.getItemAtPosition(position);
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
