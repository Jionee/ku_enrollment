package com.example.tutorial1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class Fragment_1 extends Fragment {
    String url = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy=2020&ltShtm=B01012&pobtDiv=B04045&openSust=127114";

    //1학기 B01011 2학기 B01012 하계계절학기 B01014 동계계절학기 B01015
    String base = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy=2020&ltShtm=B01012";
    String pobtDiv = "&pobtDiv="; // B04044:전필, B04045:지필, B04043:지교, B0404P:기교, B04054:심교, B04047:교직, B04046:일선, B04054:심교, ALL:전체
    String cultCorsFld = "&cultCorsFld="; //기교선택
    String openSust = "&openSust="; //학과

    private View view;
    private ArrayList<classData> classDataset; //수업정보

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TabLayout mTabLayout; //전체,1,2,3,4
    private int gradeNumber=0;

    //어댑터에 주기적으로 교체
    public static Fragment_1 newInstance(){
        Fragment_1 fragment_1 = new Fragment_1();
        return fragment_1;
    }

    public Fragment_1() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //fragment_1.xml이랑 연동
        view = inflater.inflate(R.layout.fragment_1, container, false);

        mTabLayout=(TabLayout) view.findViewById(R.id.layout_tab);
        mTabLayout.addTab(mTabLayout.newTab().setText("전체"));
        mTabLayout.addTab(mTabLayout.newTab().setText("1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("3"));
        mTabLayout.addTab(mTabLayout.newTab().setText("4"));

       mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               // TODO : tab의 상태가 선택 상태로 변경.
               gradeNumber = tab.getPosition() ;
                   //어댑터 달기
                   mAdapter = new MyAdapter(classDataset,Integer.toString(gradeNumber));
                   recyclerView.setAdapter(mAdapter);
           }
           public void onTabUnselected(TabLayout.Tab tab) {
               // TODO : tab의 상태가 선택 상태로 변경.
           }
           public void onTabReselected(TabLayout.Tab tab) {
               // TODO : 이미 선택된 tab이 다시
           }
       });

        //recyclerView 설정
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        try {
            getData();
        } catch (ExecutionException | InterruptedException | IOException e) { e.printStackTrace();
        }

        return view;
    }

    public void getData() throws IOException, ExecutionException, InterruptedException {
        //데이터 넣기
        classDataset = new parseData().execute(url,Integer.toString(gradeNumber)).get();
        //어댑터 달기
        mAdapter = new MyAdapter(classDataset,Integer.toString(gradeNumber));
        recyclerView.setAdapter(mAdapter);
    }

}