package com.example.tutorial1;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.kyleduo.switchbutton.SwitchButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class Fragment_2 extends Fragment {

    String url = null;

    //1학기 B01011 2학기 B01012 하계계절학기 B01014 동계계절학기 B01015
    String base = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy=2020&ltShtm=B01012";
    String pobtDiv = "&pobtDiv="; // B04044:전필, B04045:전선, B04061:지필, B0404P:기교, B04054:심교, B04047:교직, B04046:일선, B04054:심교, ALL:전체
    String cultCorsFld = "&cultCorsFld="; //기교선택
    String openSust = "&openSust="; //학과

    private View view;
    private ArrayList<classData> classDataset; //수업정보
    private ArrayList<classData> allDataset; //모든 심교 저장

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TabLayout mTabLayout; //전체,1,2,3,4
    private int gradeNumber=0;
    private SwitchButton switchButton;
    private boolean isEmpty=false;

    private Spinner spinner;
    private ArrayList<String> cultureName = new ArrayList<String>();

    private boolean isSearch = false;
    private int culturePosition=9999; //0:전체 1: 학문소양및인성함양 2:글로벌인재양성 3:사고력증진

    ProgressDialog dialog;
    private String[] spinnerName= {"전체","학문소양및인성함양","글로벌인재양성","사고력증진"};



    //어댑터에 주기적으로 교체
    public static Fragment_2 newInstance(){
        Fragment_2 fragment_2 = new Fragment_2();
        return fragment_2;
    }

    public Fragment_2() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //fragment_2.xml이랑 연동
        view = inflater.inflate(R.layout.fragment_2, container, false);
        //recyclerView 설정
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //검색 버튼 설정
        TextView textview_search = view.findViewById(R.id.textView_search);
        textview_search.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(culturePosition!=9999){ //영역 선택이 안됐으면
                    try { getData(url); } catch (ExecutionException | InterruptedException | IOException e) { e.printStackTrace(); }
                    isSearch=true;
                }
                else{
                    Toast.makeText(getActivity(), "영역을 선택하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //스피너
        cultureName.add("전체");
        cultureName.add("학문소양및인성함양");
        cultureName.add("글로벌인재양성");
        cultureName.add("사고력증진");
        spinner = (Spinner) view.findViewById(R.id.spinner_culture);
        final TextView t = view.findViewById(R.id.textView_switch);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,cultureName); //스피너 목록 이름 생성
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelected(false); //스피너 초기 선택X 위해
        spinner.setSelection(0,true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            int i=0;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //0:전체 1: 학문소양및인성함양 2:글로벌인재양성 3:사고력증진

                culturePosition=position;
                url = base+pobtDiv+"B04054";
                /*ArrayList<classData> cloneDataset = new ArrayList<classData>();

                Iterator<classData> iterator = allDataset.iterator();
                while(iterator.hasNext()) {
                    classData tmp = iterator.next();
                    if(tmp.getField().equals((String)parent.getItemAtPosition(position))){
                        cloneDataset.add(tmp);
                    }
                }
                if(parent.getItemAtPosition(position).equals("전체")){
                    classDataset.clear();
                    classDataset.addAll(allDataset);
                }
                else{
                    classDataset.clear();
                    classDataset.addAll(cloneDataset);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //학년 별 탭 설정
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
            }
            public void onTabUnselected(TabLayout.Tab tab) {
                // tab의 상태가 선택 상태로 변경.
            }
            public void onTabReselected(TabLayout.Tab tab) {
                // 이미 선택된 tab이 다시
            }
        });

        //남는 강의 스위치 설정
        final TextView optionState = (TextView)view.findViewById(R.id.textView_switch);

        // 스위치 버튼입니다.
        switchButton = (SwitchButton) view.findViewById(R.id.sb_use_listener);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치 버튼이 체크되었는지 검사하여 텍스트뷰에 각 경우에 맞게 출력합니다.
                if(isSearch){
                    if (isChecked){
                        isEmpty=true;
                        optionState.setText("남은 강의만");
                        ArrayList<classData> cloneDataset = new ArrayList<classData>();
                        cloneDataset.addAll(classDataset);
                        Iterator<classData> iterator = cloneDataset.iterator();
                        while(iterator.hasNext()){
                            classData tmp = iterator.next();
                            if(gradeNumber==0){
                                if((Integer.parseInt(tmp.getCurrent())-Integer.parseInt(tmp.getEmpty()))<1) { //인원이 0명이면
                                    iterator.remove();
                                }
                            }
                            else {
                                if ((Integer.parseInt(tmp.getGradeCurrent()) - Integer.parseInt(tmp.getGradeEmpty())) < 1) { //인원이 0명이면
                                    iterator.remove();
                                }
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
                    }
                    recyclerView.setAdapter(mAdapter);
                }
                else{
                    System.out.println("스위치 버튼 전에 검색 버튼을 누르십시오");
                    Toast.makeText(getActivity(), "빈 강의를 확인하려면 먼저 강의를 검색하세요", Toast.LENGTH_SHORT).show();
                    switchButton.setChecked(false);
                }
            }
        });

        return view;
    }

    public void getData(String tmpUrl) throws IOException, ExecutionException, InterruptedException {
        showProgressDialog();
        //데이터 넣기
        new Thread() {
            public void run(){
                getActivity().runOnUiThread(new Runnable(){ //mainThread에서 UI변경 해야하기 때문에 큐로 넣어준다.
                    @Override
                    public void run() {
                        //데이터 넣기
                        try {
                            classDataset = new parseData().execute(url,Integer.toString(gradeNumber)).get();
                        }
                        catch (ExecutionException|InterruptedException e) { e.printStackTrace(); }

                        search();//심교 영역별로 골라내기

                        //어댑터 달기
                        mAdapter = new MyAdapter(classDataset,Integer.toString(gradeNumber),isEmpty);
                        recyclerView.setAdapter(mAdapter);
                    }
                });
                dialog.dismiss();
            }
        }.start();
    }
    private void showProgressDialog(){
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("강의 검색중");
        dialog.show();
    }

    private void search(){  //심교 영역별로 골라내기

        if(!spinnerName[0].equals(spinnerName[culturePosition])){
            Iterator<classData> iterator = classDataset.iterator();
            while(iterator.hasNext()) {
                classData tmp = iterator.next();
                if(!tmp.getField().equals(spinnerName[culturePosition])){
                    iterator.remove();
                }
            }
        }


    }
}