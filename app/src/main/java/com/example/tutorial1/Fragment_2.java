package com.example.tutorial1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_2 extends Fragment {

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
        return inflater.inflate(R.layout.fragment_2, container, false);
    }
}