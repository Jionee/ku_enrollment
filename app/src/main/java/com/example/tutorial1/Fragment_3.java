package com.example.tutorial1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_3 extends Fragment {

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
        return inflater.inflate(R.layout.fragment_3, container, false);
    }
}