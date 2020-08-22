package com.example.tutorial1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.kyleduo.switchbutton.SwitchButton;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class Fragment_5 extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Spinner spinner;

    private ArrayList<String> majorName = new ArrayList<String>(); //학과
    private ArrayList<String> majorUrl = new ArrayList<String>(); //학과url
    private int majorPosition=0;
    private String url=null; //학과 정보 받아 올 url

    ProgressDialog dialog;

    //어댑터에 주기적으로 교체
    public static Fragment_5 newInstance(){
        Fragment_5 fragment_1 = new Fragment_5();
        return fragment_1;
    }

    public Fragment_5() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //fragment_1.xml이랑 연동
        view = inflater.inflate(R.layout.fragment_5, container, false);
        //학과 이름 정보 받아오기
        try { parseMajor(); } catch (ExecutionException | InterruptedException e) { e.printStackTrace(); }
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
                //학과 이름, URL 받아오기

                    if(majorPosition!=0){ //스피너 설정 해야함
                        //페이지 띄우기
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getActivity(), "학과를 선택하세요", Toast.LENGTH_SHORT).show();

            }//Onclick 끝
        });

        //스피너
        spinner = (Spinner) view.findViewById(R.id.spinner_major);
        final TextView t = view.findViewById(R.id.testView_major);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,majorName); //스피너 목록 이름 생성
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelected(false); //스피너 초기 선택X 위해
        spinner.setSelection(0,true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            int i=0;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                majorPosition=position;
                url = majorUrl.get(majorPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        return view;
    }

    public void parseMajor() throws ExecutionException, InterruptedException {
        ArrayList<ArrayList<String>> nameUrl = new parseListProff().execute().get();
        majorName.addAll(nameUrl.get(0));
        majorUrl.addAll(nameUrl.get(1));
    }


    private void showProgressDialog(){
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("강의 검색중");
        dialog.show();
    }
}