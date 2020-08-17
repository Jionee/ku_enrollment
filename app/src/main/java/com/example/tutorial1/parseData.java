package com.example.tutorial1;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class parseData extends AsyncTask <String,Void, ArrayList<classData>>{

    private ArrayList<classData> classDataset;
    private classData classes;
    private ArrayList<String> basket;
    private ArrayList<String> gradeEmpty;
    private ArrayList<String> gradeCurrent;
    protected ArrayList<classData> doInBackground(String... strings) {

        String h = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy=2020&ltShtm=B01012&pobtDiv=B04045&openSust=127114";
        String sbjBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourInwonInqTime.jsp?ltYy=2020&ltShtm=B01012&sbjtId=";
        String gradeBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourBasketInwonInq.jsp?ltYy=2020&ltShtm=B01012&fg=B&sbjtId=";


        Elements document = null;
        try { //strings[0] : 전달받은주소
            document = Jsoup.connect(strings[0]).get().select(".table_bg_white");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element element;
        classDataset = new ArrayList<>();

        for(int i=0;i<document.size();i++){
            basket = new ArrayList<String>();//학년별 수강바구니
            gradeEmpty = new ArrayList<String>();//학년별 수강인원
            gradeCurrent = new ArrayList<String>();//학년별 전체인원

            element = document.get(i);
            //Log.d("element",i+" "+element.select("td").eq(8).text());

            //남은 인원, 전체 인원 가져오기
            String classNum = element.select("td").eq(3).text();
            String emptyUrl = sbjBase+classNum;
            String gradeEmptyUrl = gradeBase+classNum+"&promShyr=";

            Elements document2 = null;
            Elements document3 = null;
            try {
                document2 = Jsoup.connect(emptyUrl).get().select("body");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Element element2;
            element2 = document2.get(0);
            /*Log.d("남은인원",element2.select("td").eq(3).text());
            Log.d("현재인원",element2.select("td").eq(5).text());*/
            Element element3;

            String tmp= null;
            for(int j=1;j<5;j++){
                String finalGradeEmptyUrl = gradeEmptyUrl+j; //j : 학년
                try {
                    document3 = Jsoup.connect(finalGradeEmptyUrl).get().select("body");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                element3 = document3.get(0);

                basket.add(element3.select("td").eq(0).text().trim()); //수강바구니 인원
                tmp = element3.select("td").eq(1).text().trim(); // 현재/전체
                gradeEmpty.add(tmp.substring(0,tmp.indexOf("/")).trim()); //학년별 현재 인원
                gradeCurrent.add(tmp.substring(tmp.indexOf("/")+1).trim()); //학년별 전체 인원

                /*Log.d("수강바구니",classNum+" "+j+"학년 ="+ element3.select("td").eq(0).text());
                Log.d("현재/전체",gradeEmptyUrl);
                Log.d("현재",classNum+" "+j+"학년 ="+ gradeEmpty.get(j-1));
                Log.d("현재2",tmp.substring(0,tmp.indexOf("/")).trim());
                Log.d("전체",classNum+" "+j+"학년 ="+ gradeCurrent.get(j-1));
                Log.d("전체2",tmp.substring(tmp.indexOf("/")+1).trim());*/
            }
            /*for(String a :basket){
                System.out.println(classNum+" "+a);
            }*/
            /*Log.d("수강바구니",element3.select("td").eq(0).text());
            Log.d("현재인원/전체인원",element3.select("td").eq(1).text());
            Log.d("현재",a);
            Log.d("전체",b);*/

            //3 : 과목번호, 4 : 과목이름, 8 : 시간, 9 : 교수님 || 현재 신청 인원, 전체인원 || 14 : 심교영역
            classes = new classData(element.select("td").eq(3).text(),element.select("td").eq(4).text(),
                    element.select("td").eq(9).text(),element.select("td").eq(8).text(),
                    element2.select("td").eq(3).text(),element2.select("td").eq(5).text(),basket,gradeEmpty,gradeCurrent
                    ,element.select("td").eq(14).text());

            classDataset.add(classes);
            //tmp[i] = element.select("td").eq(3).text();

        }
        classDataset.remove(0);
        return classDataset;
    }
}
