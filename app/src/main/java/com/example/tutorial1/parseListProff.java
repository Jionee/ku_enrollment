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
            /*Iterator<Element> ie = document.get(i).select("li a").iterator();

            while(ie.hasNext()){
                Element indexElement = ie.next();
                    majorName.add(indexElement.text());
                majorUrl.add(indexElement.attr("href"));
            }*/

        }

        //문과대학,이과대학,건축대학,공과대학,소프트웨어학부,산업경영융합학부,사회과학대학,경영대학,KU융합과학기술원,상허생명과학대학,수의과대학,예술디자인대학,사범대학,교양교육센터,교양연구평가센터 //15개
        ArrayList<Integer> arr = new ArrayList<Integer>( Arrays.asList(0, 9, 13, 15, 20, 23, 30, 38, 42, 51, 59, 62, 69, 77, 78));
        Iterator<Integer> ie3 = arr.iterator();
        while(ie3.hasNext()){
            Integer index = ie3.next();
            majorName.remove(index);
            majorUrl.remove(index);
        }

        nameUrl.add(majorName);
        nameUrl.add(majorUrl);

        return nameUrl;
    }


}
