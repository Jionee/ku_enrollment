package com.example.tutorial1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //****
    private ArrayList<classData> classDataset; //수업정보
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TabLayout mTabLayout; //상단바
    private Context mContext;

    private ViewPager mViewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=getApplicationContext();

        //탭 설정
        mTabLayout=(TabLayout) findViewById(R.id.layout_tab);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("전공")));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("심교")));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("기교")));
        mTabLayout.getTabAt(0).setIcon(R.drawable.basket);

        //슬라이드 viewPager 설정
        mViewPager = (ViewPager) findViewById(R.id.pager_content);
        fragmentPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),mTabLayout.getTabCount());
        mViewPager.setAdapter(fragmentPagerAdapter);

        //탭, viewPager 연결
        mTabLayout.setupWithViewPager(mViewPager);
    }

    //탭 커스터마이징
    private View createTabView(String tabName){
        View tabView = LayoutInflater.from(mContext).inflate(R.layout.custom_tab,null);
        TextView txt_name = (TextView) tabView.findViewById(R.id.txt_name);
        txt_name.setText(tabName);
        return tabView;
    }

}