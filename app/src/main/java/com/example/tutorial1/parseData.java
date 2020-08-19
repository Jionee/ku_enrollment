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
    private String basket;
    private String gradeEmpty;
    private String gradeCurrent;

    Elements document = null;
    Elements document2 = null;
    Elements document3 = null;
    Element element = null;
    Element element2 = null;
    Element element3 = null;
    String tmp= null;

    String h = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy=2020&ltShtm=B01012&pobtDiv=B04045&openSust=127114";
    String sbjBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourInwonInqTime.jsp?ltYy=2020&ltShtm=B01012&sbjtId=";
    String gradeBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourBasketInwonInq.jsp?ltYy=2020&ltShtm=B01012&fg=B&sbjtId=";

    protected void onPreExecute(){
        classDataset = new ArrayList<>();
    }
    protected void onProgressUpdate() {
        super.onProgressUpdate();
        //System.out.println(classDataset.size()+"번째 과목 로딩중");
    }

    protected void onPostExecute(){

    }

    protected ArrayList<classData> doInBackground(String... strings) {
        try { //strings[0] : 전달받은주소 ==> 강의이름,강의번호,시간,교수님
            document = Jsoup.connect(strings[0]).get().select(".table_bg_white");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<document.size();i++){

            element = document.get(i);

            //남은 인원, 전체 인원 가져오기
            String classNum = element.select("td").eq(3).text();
            String emptyUrl = sbjBase+classNum;
            String gradeEmptyUrl = gradeBase+classNum+"&promShyr=";

            try { //전체 현재인원,남은인원
                document2 = Jsoup.connect(emptyUrl).get().select("body");
            } catch (IOException e) {
                e.printStackTrace();
            }
            element2 = document2.get(0);

            //학년 현재인원,남은인원
            String finalGradeEmptyUrl = gradeEmptyUrl+strings[1];
            try {
                document3 = Jsoup.connect(finalGradeEmptyUrl).get().select("body");
            } catch (IOException e) {
                e.printStackTrace();
            }
            element3 = document3.get(0);
            tmp = element3.select("td").eq(1).text().trim(); // 현재/전체

            //3 : 과목번호, 4 : 과목이름, 8 : 시간, 9 : 교수님 || 현재 신청 인원, 전체인원 || 14 : 심교영역
            classes = new classData(element.select("td").eq(3).text(),element.select("td").eq(4).text(),
                    element.select("td").eq(9).text(),element.select("td").eq(8).text(),
                    element2.select("td").eq(3).text(),element2.select("td").eq(5).text(),
                    element3.select("td").eq(0).text().trim(),tmp.substring(0,tmp.indexOf("/")).trim(),tmp.substring(tmp.indexOf("/")+1).trim()
                    ,element.select("td").eq(14).text());

            classDataset.add(classes);
            System.out.println(classDataset.size()+"번째 과목" +element.select("td").eq(4).text()+ "로딩중");
        }
        classDataset.remove(0);
        return classDataset;
    }

}
