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
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.kyleduo.switchbutton.SwitchButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
    private boolean isEmpty=false;
    private SwitchButton switchButton;
    private Spinner spinner;

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
        mTabLayout.addTab(mTabLayout.newTab().setText("1학년"));
        mTabLayout.addTab(mTabLayout.newTab().setText("2학년"));
        mTabLayout.addTab(mTabLayout.newTab().setText("3학년"));
        mTabLayout.addTab(mTabLayout.newTab().setText("4학년"));

       mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
           @Override //하단 탭 리스너 설정
           public void onTabSelected(TabLayout.Tab tab) {
               // tab의 상태가 선택 상태로 변경.
               gradeNumber = tab.getPosition() ;
               //어댑터 달기
               mAdapter = new MyAdapter(classDataset,Integer.toString(gradeNumber),isEmpty);
               recyclerView.setAdapter(mAdapter);
               switchButton.setChecked(false);

           }
           public void onTabUnselected(TabLayout.Tab tab) {
               // tab의 상태가 선택 상태로 변경.
           }
           public void onTabReselected(TabLayout.Tab tab) {
               // 이미 선택된 tab이 다시
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

        //스위치 설정
        final TextView optionState = (TextView)view.findViewById(R.id.textView_switch);

        // 스위치 버튼입니다.
        switchButton = (SwitchButton) view.findViewById(R.id.sb_use_listener);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치 버튼이 체크되었는지 검사하여 텍스트뷰에 각 경우에 맞게 출력합니다.
                if (isChecked){
                    isEmpty=true;
                    optionState.setText("남은 강의만");
                    ArrayList<classData> cloneDataset = new ArrayList<classData>();
                    cloneDataset.addAll(classDataset);
                    Iterator<classData> iterator = cloneDataset.iterator();
                    while(iterator.hasNext()){
                        classData tmp = iterator.next();
                        switch (gradeNumber){
                            case 0:
                                if((Integer.parseInt(tmp.getCurrent())-Integer.parseInt(tmp.getEmpty()))<1) { //인원이 0명이면
                                    iterator.remove();
                                }
                                break;
                            case 1://1학년
                                if((Integer.parseInt(tmp.getGradeCurrent().get(0))-Integer.parseInt(tmp.getGradeEmpty().get(0)))<1) { //인원이 0명이면
                                    iterator.remove();
                                }
                                break;
                            case 2://2학년
                                if((Integer.parseInt(tmp.getGradeCurrent().get(1))-Integer.parseInt(tmp.getGradeEmpty().get(1)))<1) { //인원이 0명이면
                                    iterator.remove();
                                }
                                break;
                            case 3://3학년
                                if((Integer.parseInt(tmp.getGradeCurrent().get(2))-Integer.parseInt(tmp.getGradeEmpty().get(2)))<1) { //인원이 0명이면
                                    iterator.remove();
                                }
                                break;
                            case 4://4학년
                                if((Integer.parseInt(tmp.getGradeCurrent().get(3))-Integer.parseInt(tmp.getGradeEmpty().get(3)))<1) { //인원이 0명이면
                                    iterator.remove();
                                }
                                break;
                            default: return;
                        }

                    //어댑터 달기
                    mAdapter = new MyAdapter(cloneDataset,Integer.toString(gradeNumber),isEmpty);
                    }
                }

                else{ //전체 강의
                    isEmpty=false;
                    optionState.setText("전체 강의");
                    //어댑터 달기
                    mAdapter = new MyAdapter(classDataset,Integer.toString(gradeNumber),isEmpty);
                    //System.out.println("전체강의수"+classDataset.size());
                }

                recyclerView.setAdapter(mAdapter);
            }
        });

        //스피너
        spinner = (Spinner) view.findViewById(R.id.spinner_major);
        //TODO


        return view;
    }

    public void getData() throws IOException, ExecutionException, InterruptedException {
        //데이터 넣기
        classDataset = new parseData().execute(url,Integer.toString(gradeNumber)).get();
        //어댑터 달기
        mAdapter = new MyAdapter(classDataset,Integer.toString(gradeNumber),isEmpty);
        recyclerView.setAdapter(mAdapter);
    }

}