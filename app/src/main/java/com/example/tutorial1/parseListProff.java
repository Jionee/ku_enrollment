package com.example.tutorial1;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class parseListProff extends AsyncTask <String,Void, ArrayList<ArrayList<String>>>{

    private ArrayList<ArrayList<String>> nameUrl = new ArrayList<ArrayList<String>>();//리턴값
    private ArrayList<String> majorName = new ArrayList<String>(); //학과
    private ArrayList<String> majorUrl= new ArrayList<String>(); //학과url

    protected ArrayList<ArrayList<String>> doInBackground(String... strings) {

        String base = "http://www.konkuk.ac.kr/jsp/Coll/coll_index.jsp";


        Elements document = null;
        try {
            document = Jsoup.connect(base).get().select(".coll_index");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int documentSize=document.size();

        for(int i=0;i<documentSize;i++){
            Elements elements = document.get(i).select("li a");
            for(int m=0;m<elements.size();m++){
                majorName.add(elements.eq(m).text());
                majorUrl.add("http://www.konkuk.ac.kr/jsp/Coll/" + elements.eq(m).attr("href"));
            }
        }

        //문과대학,이과대학,건축대학,공과대학,소프트웨어학부,산업경영융합학부,사회과학대학,경영대학,KU융합과학기술원,상허생명과학대학,수의과대학,예술디자인대학,사범대학,교양교육센터,교양연구평가센터 //15개 지우기
        /*ArrayList<Integer> arr = new ArrayList<Integer>(Arrays.asList(0, 9, 13, 15, 28, 36, 40, 49, 57, 60, 67, 76, 77));*/
        {majorName.remove(77);   majorUrl.remove(77);
        majorName.remove(76);   majorUrl.remove(76);
        majorName.remove(67);   majorUrl.remove(67);
        majorName.remove(60);   majorUrl.remove(60);
        majorName.remove(57);   majorUrl.remove(57);
        majorName.remove(49);   majorUrl.remove(49);
        majorName.remove(40);   majorUrl.remove(40);
        majorName.remove(36);   majorUrl.remove(36);
        majorName.remove(28);   majorUrl.remove(28);
        majorName.remove(15);   majorUrl.remove(15);
        majorName.remove(13);   majorUrl.remove(13);
        majorName.remove(9);   majorUrl.remove(9);
        majorName.remove(0);   majorUrl.remove(0);}
        majorName.add(0,"선택");  majorUrl.add(0,"");

        nameUrl.add(majorName);
        nameUrl.add(majorUrl);

        return nameUrl;
    }
}
