package com.konkuk.suku;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class parseData extends AsyncTask <String,Void, ArrayList<classData>>{


    private ArrayList<classData> classDataset;
    private static classData[] c;

    Elements document = null;
    String string2_value = null;


    protected void onPreExecute(){
        classDataset = new ArrayList<>();
    }
    protected void onProgressUpdate() {
        super.onProgressUpdate();
    }
    protected void onPostExecute(){

    }

    protected ArrayList<classData> doInBackground(String... strings) {

        int documentSize=0;
        try { //strings[0] : 전달받은주소 ==> 강의이름,강의번호,시간,교수님
            document = Jsoup.connect(strings[0]).get().select(".table_bg_white");
            documentSize = document.size();
        } catch (IOException e) { e.printStackTrace(); }
        string2_value = strings[1];

        c = new classData[documentSize];
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(20, 270, 1, TimeUnit.SECONDS, new LinkedBlockingQueue(100));

        for (int i = 0; i < documentSize; i++) {
            Task thread = new Task(document,i,string2_value,documentSize);
            threadPoolExecutor.execute(thread);
        }


        //threadpool끝나기 기다리기
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) { e.printStackTrace(); }


        for(int j=0;j<documentSize;j++){
            classDataset.add(c[j]);
        }

        classDataset.remove(0);

        return classDataset;
    }

    private static class Task implements Runnable {
        String sbjBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourInwonInqTime.jsp?ltYy=2020&ltShtm=B01012&sbjtId=";
        String gradeBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourBasketInwonInq.jsp?ltYy=2020&ltShtm=B01012&fg=B&sbjtId=";
        int classSequence = 0;
        Elements document1 = null;
        Elements document2 = null;
        Elements document3 = null;
        Element element = null;
        Element element2 = null;
        Element element3 = null;
        String tmp= null;
        String gradePlus = null;
        boolean done = false;
        int documentSize=0;

        /////////////////////////////
        private classData classes;
        private ArrayList<classData> classDataset=new ArrayList<classData>();

        Task(Elements document,int classSequence,String gradePlus,int documentSize){
            this.document1=document;
            this.classSequence=classSequence;
            this.gradePlus = gradePlus;
            this.documentSize=documentSize;
        }

        @Override
        public void run() {
            //input : document객체, i번째과목, strings[1],
            //return : classData
            element = document1.get(classSequence);

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
                    ,element.select("td").eq(14).text(),Integer.parseInt(gradePlus));

            parseData.c[classSequence]=classes;
            System.out.println((classSequence+1)+"번째 과목" +element.select("td").eq(4).text()+ "로딩중");

            document1 = null;
            document2 = null;
            document3 = null;
            element = null;
            element2 = null;
            element3 = null;
            classes = null;

        }//run끝

        public classData getClasses(){
            return classes;
        }
    }

}
