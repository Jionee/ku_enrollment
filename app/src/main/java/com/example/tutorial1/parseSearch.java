package com.example.tutorial1;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class parseSearch extends AsyncTask <String,Void,classData>{

    private classData classes;
    private String classPlus;
    private String gradePlus;
    private Elements document1 = null;
    private Elements document2 = null;
    private Elements document3 = null;
    private Element element = null;
    private Element element2 = null;
    private Element element3 = null;
    private String tmp= null;

    protected classData doInBackground(String... strings) {
        String base = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy=2020&ltShtm=B01012&sbjtId=";
        String sbjBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourInwonInqTime.jsp?ltYy=2020&ltShtm=B01012&sbjtId=";
        String gradeBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourBasketInwonInq.jsp?ltYy=2020&ltShtm=B01012&fg=B&sbjtId=";
        classPlus = strings[0]; //********넣어주기
        gradePlus = strings[1];
        String basket = null;

        Elements document = null;
        try {
            document = Jsoup.connect(base+classPlus).get().select(".table_bg_white");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element element = document.get(1);

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
        String finalGradeEmptyUrl = gradeEmptyUrl+gradePlus;
        Elements[] document3Array = new Elements[4];
        Element[] element3Array = new Element[4];
        //1,2,3,4학년
        for(int i=0;i<4;i++){
            try {
                document3Array[i] = Jsoup.connect(gradeEmptyUrl+(i+1)).get().select("body");
            } catch (IOException e) {
                e.printStackTrace();
            }
            element3Array[i]=document3Array[i].get(0);
        }


        /*try {
            document3 = Jsoup.connect(finalGradeEmptyUrl).get().select("body");
        } catch (IOException e) {
            e.printStackTrace();
        }
        element3 = document3.get(0);
        tmp = element3.select("td").eq(1).text().trim(); // 현재/전체*/

        if(gradePlus.equals("0")){ //전체이면
            int sum=0;
            for(int i=0;i<4;i++){
                sum+=Integer.parseInt(element3Array[i].select("td").eq(0).text().trim());
            }
            basket = Integer.toString(sum);
            tmp = element3Array[0].select("td").eq(1).text().trim(); // 현재/전체
        }
        else{
            basket = element3Array[Integer.parseInt(gradePlus)-1].select("td").eq(0).text().trim();
            tmp = element3Array[Integer.parseInt(gradePlus)-1].select("td").eq(1).text().trim(); // 현재/전체
        }

        //3 : 과목번호, 4 : 과목이름, 8 : 시간, 9 : 교수님 || 현재 신청 인원, 전체인원 || 수강바구니, 학년별 신청인원, 전체인원 || 14 : 심교영역
        classes = new classData(element.select("td").eq(3).text(),element.select("td").eq(4).text(),
                element.select("td").eq(9).text(),element.select("td").eq(8).text(),
                element2.select("td").eq(3).text(),element2.select("td").eq(5).text(),
                basket,tmp.substring(0,tmp.indexOf("/")).trim(),tmp.substring(tmp.indexOf("/")+1).trim()
                ,element.select("td").eq(14).text());

        return classes;
    }
}
