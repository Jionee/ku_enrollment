package com.konkuk.suku;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.konkuk.suku.R;
import com.google.android.material.tabs.TabLayout;
import com.kyleduo.switchbutton.SwitchButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class Fragment_3 extends Fragment {
    //String url = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy=2020&ltShtm=B01012&openSust=006751&pobtDiv=B0404P";//전체대학 기교 전체
    String url = null;

    //깃 브랜치 테스트
    //1학기 B01011 2학기 B01012 하계계절학기 B01014 동계계절학기 B01015
    //String base = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy=2020&ltShtm=B01015";
    /*String pobtDiv = "&pobtDiv="; // B04044:전필, B04045:전선, B04061:지필, B0404P:기교, B04054:심교, B04047:교직, B04046:일선, B04054:심교, ALL:전체
    String cultCorsFld = "&cultCorsFld="; //기교선택
    String openSust = "&openSust="; //학과*/

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
    private ArrayList<String> basicName = new ArrayList<String>();
    private ArrayList<String> basicNumber = new ArrayList<String>();

    private boolean isSearch = false;

    ProgressDialog dialog;

    //어댑터에 주기적으로 교체
    public static Fragment_3 newInstance(){
        Fragment_3 fragment_3 = new Fragment_3();
        return fragment_3;

    }

    public Fragment_3() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //fragment_1.xml이랑 연동
        view = inflater.inflate(R.layout.fragment_3, container, false);
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
                if(!basicNumber.get(0).equals("0")){ //학과 선택이 됐으면
                    try { getData(url); } catch (ExecutionException | InterruptedException | IOException e) { e.printStackTrace(); }
                    isSearch=true;
                    switchButton.setChecked(false);//전체강의로 스위치
                }
                else{
                    Toast.makeText(getActivity(), "학과를 선택하세요", Toast.LENGTH_SHORT).show();
                    System.out.println("정보를 모두 선택하세요");
                }
            }
        });


        //스피너
        spinner = (Spinner) view.findViewById(R.id.spinner_basic);
        final TextView t = view.findViewById(R.id.testView_basic);
        //기교 이름 정보 받아오기
        try { parseBasic(); } catch (ExecutionException | InterruptedException e) { e.printStackTrace(); }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,basicName); //스피너 목록 이름 생성
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelected(false); //스피너 초기 선택X 위해
        spinner.setSelection(0,true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            int i=0;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                basicNumber.set(0,"1");
                String basicNum = basicNumber.get(position);
                url = Constants.base + Constants.pobtDiv + Constants.giGyo + Constants.cultCorsFld + basicNum;
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
                switchButton.setChecked(false);
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
                            mAdapter = new MyAdapter(cloneDataset,Integer.toString(gradeNumber));
                        }
                    }
                    else{ //전체 강의
                        isEmpty=false;
                        optionState.setText("전체 강의");
                        //어댑터 달기
                        mAdapter = new MyAdapter(classDataset,Integer.toString(gradeNumber));
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
        new Thread() {
            public void run(){
                getActivity().runOnUiThread(new Runnable(){ //mainThread에서 UI변경 해야하기 때문에 큐로 넣어준다.
                    @Override
                    public void run() {
                        //데이터 넣기
                        try {
                            classDataset = new parseData().execute(url,Integer.toString(gradeNumber)).get();
                            if(classDataset.get(0).getName().equals("")){
                                classDataset.remove(0);
                            }
                        }
                        catch (ExecutionException|InterruptedException e) { e.printStackTrace(); }

                        if(classDataset.size()>0){
                            //어댑터 달기
                            mAdapter = new MyAdapter(classDataset,Integer.toString(gradeNumber));
                            recyclerView.setAdapter(mAdapter);
                        }
                        else{
                            Toast.makeText(getActivity(), "검색 내역이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.dismiss();
            }
        }.start();
    }

    public void parseBasic() throws ExecutionException, InterruptedException {
        ArrayList<ArrayList<String>> tmp;
        tmp = new parseList().execute().get();
        basicName = new ArrayList<String>();
        basicNumber = new ArrayList<String>();
        basicName.addAll(tmp.get(2));//기교 이름
        basicNumber.addAll(tmp.get(3));//기교 번호
        //선택 넣기
        basicName.add(0,"선택"); basicNumber.add(0,"0");
    }

    private void showProgressDialog(){
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("실시간 정보를 받아 강의 검색중입니다");
        dialog.show();
    }
}