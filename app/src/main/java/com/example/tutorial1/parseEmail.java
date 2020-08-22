package com.example.tutorial1;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class parseEmail extends AsyncTask <String,Void,ArrayList<professorData>>{

    private professorData professor = new professorData();
    private ArrayList<professorData> professorArr = new ArrayList<professorData>();
    String base = null;

    public parseEmail(String url) {
        this.base = url;
    }

    protected ArrayList<professorData> doInBackground(String... strings) {

        // strings[0] : url 넘기기
        //base = strings[0];

        Elements document = null;
        try {
            document = Jsoup.connect(base).get().select("tbody");
        } catch (IOException e) { e.printStackTrace(); }

        Iterator<Element> it = document.select("tr").iterator();
        while(it.hasNext()){
            Element tmp = it.next();
            professor = new professorData(tmp.select("a").text(),tmp.select("td").eq(4).text(),
                    tmp.select("td").eq(2).text(),tmp.select("td").eq(3).text());
            professorArr.add(professor);
        }

        return professorArr;
    }
}
