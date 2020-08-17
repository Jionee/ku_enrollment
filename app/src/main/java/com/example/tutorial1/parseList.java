package com.example.tutorial1;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class parseList extends AsyncTask <String,Void, ArrayList<ArrayList<String>>>{

    private ArrayList<String> majorName; //학과
    private ArrayList<String> majorNumber; //학과번호
    private ArrayList<String> basicCultureName; //기교
    private ArrayList<String> basicCultureNumber; //기교번호

    protected ArrayList<ArrayList<String>> doInBackground(String... strings) {

        String base = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp";

        Elements document = null;
        try {
            document = Jsoup.connect(base).get().select(".ctl_enabled");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element majorElements = document.get(2); //학과 리스트
        Element basicCultureElements = document.get(4);//기교 리스트
        majorName = new ArrayList<String>();
        basicCultureName = new ArrayList<String>();
        Element majorElements2 = document.get(2);
        Element basicCultureElements2 = document.get(4);
        majorNumber = new ArrayList<String>();
        basicCultureNumber = new ArrayList<String>();

        Iterator<Element> ie1 = majorElements.select("option").iterator();
        Iterator<Element> ie2 = majorElements2.select("option").iterator();
        Iterator<Element> ie3 = basicCultureElements.select("option").iterator();
        Iterator<Element> ie4 = basicCultureElements2.select("option").iterator();

        while(ie1.hasNext()){
            majorName.add(ie1.next().text());
            majorNumber.add(ie2.next().attr("value"));
        }
        while(ie3.hasNext()){
            basicCultureName.add(ie3.next().text());
            basicCultureNumber.add(ie4.next().attr("value"));
        }
        /*majorName.add(majorElements.select("option").iterator().next().text());
        basicCultureName.add(basicCultureElements.select("option").iterator().next().text());
        majorNumber.add(majorElements2.select("option").iterator().next().attr("value"));
        basicCultureNumber.add(basicCultureElements2.select("option").iterator().next().attr("value"));*/

        majorName.remove(0);
        majorNumber.remove(0);
        basicCultureName.remove(0);
        basicCultureNumber.remove(0);

        ArrayList<ArrayList<String>> selector = new ArrayList<ArrayList<String>>();
        selector.add(majorName);
        selector.add(majorNumber);
        selector.add(basicCultureName);
        selector.add(basicCultureNumber);
        return selector;
    }
}
