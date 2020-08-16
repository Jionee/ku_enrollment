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
    protected ArrayList<classData> doInBackground(String... strings) {

        String h = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy=2020&ltShtm=B01012&pobtDiv=B04045&openSust=127114";
        String sbjBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourInwonInqTime.jsp?ltYy=2020&ltShtm=B01012&sbjtId=";


        Elements document = null;
        try { //strings[0] : 전달받은주소
            document = Jsoup.connect(strings[0]).get().select(".table_bg_white");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element element;
        classDataset = new ArrayList<>();
        for(int i=0;i<document.size();i++){
            element = document.get(i);
            //Log.d("element",i+" "+element.select("td").eq(8).text());

            //남은 인원, 전체 인원 가져오기
            String classNum = element.select("td").eq(3).text();
            String url = sbjBase+classNum;
            Elements document2 = null;
            try {
                document2 = Jsoup.connect(url).get().select("body");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Element element2;
            element2 = document2.get(0);
            /*Log.d("남은인원",element2.select("td").eq(3).text());
            Log.d("현재인원",element2.select("td").eq(5).text());*/

            //3 : 과목번호, 4 : 과목이름, 8 : 시간, 9 : 교수님 || 현재 신청 인원, 전체인원
            classes = new classData(element.select("td").eq(3).text(),element.select("td").eq(4).text(),
                    element.select("td").eq(9).text(),element.select("td").eq(8).text(),
                    element2.select("td").eq(3).text(),element2.select("td").eq(5).text());

            classDataset.add(classes);
            //tmp[i] = element.select("td").eq(3).text();

        }
        classDataset.remove(0);
        return classDataset;
    }
}
