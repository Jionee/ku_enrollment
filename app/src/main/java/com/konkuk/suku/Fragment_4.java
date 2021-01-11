package com.konkuk.suku;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konkuk.suku.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class Fragment_4 extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TabLayout mTabLayout; //전체,1,2,3,4
    private EditText editText;
    private String edit_classNum=null;
    private int tmpGradeNumber=0;
    private int gradeNumber=0;
    private classData classes = null;
    private ArrayList<classData> classArr = new ArrayList<>();
    private classData reloadClasses = null;
    //공유변수
    private ArrayList<String> sharedClassNum = new ArrayList<>();
    private ArrayList<String> sharedGradeNum = new ArrayList<>();

    Context thisContext = null;

    ProgressDialog dialog;
    Animation anim;

    //어댑터에 주기적으로 교체
    public static Fragment_4 newInstance() throws ExecutionException, InterruptedException {
        Fragment_4 fragment_4 = new Fragment_4();
        return fragment_4;
    }

    //생성자
    public Fragment_4() throws ExecutionException, InterruptedException {
        onAttach(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //초기 데이터 가져오기

        view = inflater.inflate(R.layout.fragment_4, container, false);
        //recyclerView 설정
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //저장된 데이터 로딩
        loadData();
        mAdapter = new addAdapter(classArr,gradeNumber);
        recyclerView.setAdapter(mAdapter);

        //과목 번호 입력받기
        editText = (EditText) view.findViewById(R.id.editText);

        //검색 버튼 설정
        final TextView textview_search = view.findViewById(R.id.textView_search);
        textview_search.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View view) {
                gradeNumber = tmpGradeNumber;
                showProgressDialog();
                String input = editText.getText().toString();
                if ( input.replace(" ", "").equals("")) { //공백일때
                    Toast.makeText(getActivity(), "과목 번호를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    edit_classNum = editText.getText().toString(); //과목 번호 받아오기
                    try {  //파싱
                        classes = new parseSearch().execute(edit_classNum, Integer.toString(gradeNumber)).get(); //검색
                        if(classes==null){
                            Toast.makeText(getActivity(), "검색한 내역이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            setView();
                        }
                    } catch (ExecutionException|InterruptedException e) { e.printStackTrace(); }
                } //else 끝
                dialog.dismiss();
            }
        });

        //강의 추가 버튼
        TextView textview_add = view.findViewById(R.id.textview_add);
        textview_add.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean isExist = false;
                //중복 제거
                if(classes!=null){ //검색했을때만

                    if(classArr!=null){ //강의번호로 중복되는지 검색
                        Iterator<classData> it = classArr.iterator();
                        while(it.hasNext()){
                            classData itNext = it.next();
                            if(itNext.getNumbers().equals(edit_classNum) && itNext.getGradeNumber()==gradeNumber){
                                isExist = true;
                                break;
                            }
                        }
                    }

                    if(isExist==false){ //중복된 강의 체크
                        //강의 추가
                        classArr.add(classes);
                        saveData(thisContext,classArr);
                        //추가될때마다 갱신
                        mAdapter = new addAdapter(classArr,gradeNumber);
                        recyclerView.setAdapter(mAdapter);
                    }
                    else{ Toast.makeText(getActivity(), "(과목번호&학년이) 중복된 강의입니다", Toast.LENGTH_SHORT).show(); }
                }
                else{ Toast.makeText(getActivity(), "검색을 먼저 해주세요", Toast.LENGTH_SHORT).show(); }
            }
        });

        anim = new AlphaAnimation(0.0f,1.0f);
        anim.setDuration(100);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(2);

        //강의 삭제 버튼
        TextView textview_delete = view.findViewById(R.id.textview_delete);
        textview_delete.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View view) {
                //삭제

                boolean isExist = false;
                //중복 제거
                if(classes!=null){ //검색했을때만
                    if(classArr!=null){ //강의번호로 존재하는지 검색 후 삭제
                        Iterator<classData> it = classArr.iterator();
                        while(it.hasNext()){
                            classData itNext = it.next();
                            if(itNext.getNumbers().equals(edit_classNum) && itNext.getGradeNumber()==gradeNumber){
                                it.remove(); //삭제
                                saveData(thisContext,classArr);
                                isExist = true;
                                break;
                            }
                        }
                    }
                    if(isExist==true){
                        //삭제될때마다 갱신
                        mAdapter = new addAdapter(classArr,gradeNumber);
                        recyclerView.setAdapter(mAdapter);
                    }
                    else{ Toast.makeText(getActivity(), "추가되지 않은 강의입니다", Toast.LENGTH_SHORT).show();
                        textview_search.startAnimation(anim);
                    }
                }
                else{ Toast.makeText(getActivity(), "검색을 먼저 해주세요", Toast.LENGTH_SHORT).show();
                    textview_search.startAnimation(anim);}
            }
        });

        //새로고침버튼
        final TextView textview_reload = view.findViewById(R.id.textview_reload);
        textview_reload.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                //새로고침버튼 누르면 갱신
                if(classArr.size()>0){
                    ArrayList<classData> reloadArr = new ArrayList<>();
                    //parseSearch 해서 갱신해주기
                    Iterator<classData> it = classArr.iterator();
                    while(it.hasNext()){
                        classData itNext = it.next();
                        try {
                            reloadClasses = new parseSearch().execute(itNext.getNumbers(), Integer.toString(itNext.getGradeNumber())).get(); //검색
                            reloadArr.add(reloadClasses);
                        } catch (ExecutionException|InterruptedException e) { e.printStackTrace(); }
                    }
                    classArr = new ArrayList<>();
                    classArr.addAll(reloadArr);

                    Toast.makeText(getActivity(), "새로 고침 완료", Toast.LENGTH_SHORT).show();
                    mAdapter = new addAdapter(classArr,gradeNumber);
                    recyclerView.setAdapter(mAdapter);
                }
                else{ Toast.makeText(getActivity(), "강의를 먼저 추가 해주세요", Toast.LENGTH_SHORT).show(); }
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
                tmpGradeNumber = tab.getPosition() ;
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
        TextView textview_grade = (TextView) view.findViewById(R.id.textview_grade); //학년
        TextView textview_name = (TextView) view.findViewById(R.id.textview_name);  //과목이름
        TextView textview_time = (TextView) view.findViewById(R.id.textview_time);//시간
        TextView textView_number = (TextView) view.findViewById(R.id.textView_number);//과목 번호
        TextView textview_basket = (TextView) view.findViewById(R.id.textview_basket);//수강바구니인원
        TextView textview_entire = (TextView) view.findViewById(R.id.textview_entire);//전체인원
        TextView textview_current = (TextView) view.findViewById(R.id.textview_current);//현재 신청 인원
        TextView textView_empty = (TextView) view.findViewById(R.id.textView_empty);//남은 인원
        TextView textview_1 = view.findViewById(R.id.textview_1);
        TextView textview_2 = view.findViewById(R.id.textview_2);
        TextView textview_3 = view.findViewById(R.id.textview_3);
        TextView textview_4 = view.findViewById(R.id.textview_4);

        textview_name.setText(classes.getName().substring(0,classes.getName().indexOf("("))+"("+classes.getProfessor()+")");
        textview_time.setText(classes.getTime());
        textView_number.setText(classes.getNumbers());
        textview_basket.setText(classes.getBasket());

        int empt=0; int curr=0;
        if(gradeNumber==0){
            textview_grade.setText("전체");
            textview_current.setText(classes.getEmpty());
            textview_entire.setText(classes.getCurrent());
            empt = Integer.parseInt(classes.getEmpty());
            curr = Integer.parseInt(classes.getCurrent());
        }
        else{
            textview_grade.setText(classes.getGradeNumber()+"학년");
            textview_current.setText(classes.getGradeEmpty());
            textview_entire.setText(classes.getGradeCurrent());
            empt = Integer.parseInt(classes.getGradeEmpty());
            curr = Integer.parseInt(classes.getGradeCurrent());
        }
        textview_1.setText("수강바구니");
        textview_2.setText("전체 인원");
        textview_3.setText("현재 신청 인원");
        textview_4.setText("남은 인원");

        SpannableString spannableString = new SpannableString(Integer.toString(curr-empt));
        SpannableString spannableString2 = new SpannableString(classes.getName().substring(0,classes.getName().indexOf("("))+"("+classes.getProfessor()+")");
        if(curr-empt < 1){ //0이하면 빨간색으로
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#e0704e")), 0, spannableString.length(), 0);
            spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#e0704e")), 0, spannableString2.length(), 0);
        }
        else{ //남으면 초록색
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4caf50")), 0, spannableString.length(), 0);
            spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#4caf50")), 0, spannableString2.length(), 0);
        }
        textview_name.setText(spannableString2);
        textView_empty.setText(spannableString); //남은인원
    }

    public static void saveData(Context thisContext,ArrayList<classData> classArr) {
        SharedPreferences sharedPreferences = thisContext.getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(classArr);
        editor.putString("classArr",json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = thisContext.getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("classArr",null);
        Type type = new TypeToken<ArrayList<classData>>() {}.getType();
        classArr = gson.fromJson(json, type);
        if(classArr == null){
            classArr = new ArrayList<>();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        thisContext = activity;
    }
}