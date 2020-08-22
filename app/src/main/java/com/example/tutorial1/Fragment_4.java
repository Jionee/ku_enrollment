package com.example.tutorial1;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.kyleduo.switchbutton.SwitchButton;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class Fragment_4 extends Fragment {

    private View view;
    private TabLayout mTabLayout; //전체,1,2,3,4
    private EditText editText;
    private String edit_classNum=null;
    private int gradeNumber=0;
    private classData classes = null;

    ProgressDialog dialog;

    //어댑터에 주기적으로 교체
    public static Fragment_4 newInstance(){
        Fragment_4 fragment_4 = new Fragment_4();
        return fragment_4;
    }

    public Fragment_4() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_4, container, false);

        //과목 번호 입력받기
        editText = (EditText) view.findViewById(R.id.editText);

        //검색 버튼 설정
        TextView textview_search = view.findViewById(R.id.textView_search);
        textview_search.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View view) {
                showProgressDialog();
                String input = editText.getText().toString();
                //############잘못된 입력 처리 필요 ex)0001
                if ( input.replace(" ", "").equals("")) { //공백일때
                        /*||( (1000 > Integer.parseInt(editText.getText().toString()) || Integer.parseInt(editText.getText().toString()) > 9999))){*/ //4자리가아닐때
                    Toast.makeText(getActivity(), "과목 번호를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    edit_classNum = editText.getText().toString(); //과목 번호 받아오기
                    //파싱
                    try {
                        classes = new parseSearch().execute(edit_classNum, Integer.toString(gradeNumber)).get();
                        setView();
                    } catch (ExecutionException|InterruptedException e) { e.printStackTrace(); }
                } //else 끝
                dialog.dismiss();
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
            @Override //탭 리스너 설정
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

        return view;
    }

    private void showProgressDialog(){
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("강의 검색중");
        dialog.show();
    }

    private void setView(){
        TextView textview_name = (TextView) view.findViewById(R.id.textview_name);  //과목이름
        TextView textview_time = (TextView) view.findViewById(R.id.textview_time);//시간
        TextView textView_number = (TextView) view.findViewById(R.id.textView_number);//과목 번호
        TextView textview_gradeNumber = (TextView) view.findViewById(R.id.textview_gradeNumber);//탭) 학년
        TextView textview_basket = (TextView) view.findViewById(R.id.textview_basket);//수강바구니인원
        TextView textview_entire = (TextView) view.findViewById(R.id.textview_entire);//전체인원
        TextView textview_current = (TextView) view.findViewById(R.id.textview_current);//현재 신청 인원
        TextView textView_empty = (TextView) view.findViewById(R.id.textView_empty);//남은 인원

        textview_name.setText(classes.getName().substring(0,classes.getName().indexOf(" ("))+"("+classes.getProfessor()+")");
        textview_time.setText(classes.getTime());
        textView_number.setText(classes.getNumbers());
        if(gradeNumber==0){
            textview_gradeNumber.setText("전체");
        }else{
            textview_gradeNumber.setText(gradeNumber+"학년");
        }
        textview_basket.setText(classes.getBasket());

        int empt=0; int curr=0;
        if(gradeNumber==0){
            textview_current.setText(classes.getEmpty());
            textview_entire.setText(classes.getCurrent());
            empt = Integer.parseInt(classes.getEmpty());
            curr = Integer.parseInt(classes.getCurrent());
        }
        else{
            textview_current.setText(classes.getGradeEmpty());
            textview_entire.setText(classes.getGradeCurrent());
            empt = Integer.parseInt(classes.getGradeEmpty());
            curr = Integer.parseInt(classes.getGradeCurrent());
        }

        SpannableString spannableString = new SpannableString(Integer.toString(curr-empt));
        if(curr-empt < 1){ //0이하면 빨간색으로
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#d41611")), 0, spannableString.length(), 0);
        }
        else{ //남으면 초록색
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#3c701c")), 0, spannableString.length(), 0);
        }
        textView_empty.setText(Integer.toString(curr-empt)); //남은인원
    }
}