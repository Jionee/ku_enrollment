package com.example.tutorial1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private int mPageCount;

    public MyViewPagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.mPageCount = pageCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Fragment_1.newInstance();
            case 1:
                return Fragment_2.newInstance();
            case 2:
                return Fragment_3.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPageCount;
    }

    //상단 탭 레이아웃 인디케이터 쪽에 텍스트 선언해주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "전공";
            case 1:
                return "심교";
            case 2:
                return "기교";
            default:
                return null;
        }
    }
}
