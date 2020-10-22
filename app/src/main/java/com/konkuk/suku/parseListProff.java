package com.konkuk.suku;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class parseListProff extends AsyncTask <String,Void, ArrayList<ArrayList<String>>>{

    private ArrayList<ArrayList<String>> nameUrl = new ArrayList<ArrayList<String>>();//리턴값
    private ArrayList<String> majorName = new ArrayList<String>(); //학과
    private ArrayList<String> majorUrl= new ArrayList<String>(); //학과url

    protected ArrayList<ArrayList<String>> doInBackground(String... strings) {

        //String base = "http://www.konkuk.ac.kr/jsp/Coll/coll_index.jsp";


        Elements document = null;
        try {
            document = Jsoup.connect(Constants.proffBase2).get().select(".coll_index");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int documentSize=document.size();

        for(int i=0;i<documentSize;i++){
            Elements elements = document.get(i).select("li a");
            for(int m=0;m<elements.size();m++){
                majorName.add(elements.eq(m).text());
                majorUrl.add(Constants.proffBase1 + elements.eq(m).attr("href"));
            }
        }



        //문과대학,이과대학,건축대학,공과대학,사회과학대학,경영대학,부동산과학원,KU융합과학기술원,상허생명과학대학,수의과대학,예술디자인대학,사범대학,교양교육센터,교양연구평가센터,사회봉사센터,국제학부 //15개 지우기
        /*ArrayList<Integer> arr = new ArrayList<Integer>(Arrays.asList(0, 9, 13, 15, 25, 33, 37, 46, 54, 57, 60, 67, 75));*/
        //위 소개 항목들 삭제
        /*int arr[] = {77,76,75,74,72,64,57,54,46,37,33,25,13,9,0};

        for(int i=0;i<arr.length;i++){
            majorName.remove(arr[i]);   majorUrl.remove(arr[i]);
        }*/
        {
            majorName.remove(77);   majorUrl.remove(77);
            majorName.remove(76);   majorUrl.remove(76);
            majorName.remove(75);   majorUrl.remove(75);
            majorName.remove(74);   majorUrl.remove(74);
        majorName.remove(72);   majorUrl.remove(72);
        majorName.remove(64);   majorUrl.remove(64);
        majorName.remove(57);   majorUrl.remove(57);
        majorName.remove(54);   majorUrl.remove(54);
        majorName.remove(46);   majorUrl.remove(46);
        majorName.remove(37);   majorUrl.remove(37);
        majorName.remove(33);   majorUrl.remove(33);
        majorName.remove(25);   majorUrl.remove(25);
        majorName.remove(15);   majorUrl.remove(15);
        majorName.remove(13);   majorUrl.remove(13);
        majorName.remove(9);   majorUrl.remove(9);
        majorName.remove(0);   majorUrl.remove(0);}

        majorName.add(0,"선택");  majorUrl.add(0,"");
        //학과 홈페이지 url 설정, fragment5 intent 부분 숫자 바꾸기
        {majorUrl.set(31,"http://www.realestate.ac.kr/gb/bbs/board.php?bo_table=faculty"); //부동산학과
        majorUrl.set(32,"http://energy.konkuk.ac.kr/jsp.do?siteId=ENERGY&menuSeq=4697");
        majorUrl.set(33,"http://smartvehicle.konkuk.ac.kr/html.do?siteId=SMARTVEHICLE&menuSeq=2672");
        majorUrl.set(34,"http://sicte.konkuk.ac.kr/html.do?siteId=SICTE&menuSeq=2616");
        majorUrl.set(35,"http://cosmetics.konkuk.ac.kr/html.do?siteId=COSMETICS&menuSeq=2224");
        majorUrl.set(36,"http://home.konkuk.ac.kr:8080/cms/Site/jsp/scrb/sub.jsp?menuId=14898264");
        majorUrl.set(37,"http://bmse.konkuk.ac.kr/jsp.do?siteId=BMSE&menuSeq=2185");
        majorUrl.set(38,"http://kusysbt.konkuk.ac.kr/");
        majorUrl.set(39,"http://home.konkuk.ac.kr/cms/Site/jsp/ibb/sub.jsp?menuId=14570493");
        majorUrl.set(40,"http://biology.konkuk.ac.kr/");
        majorUrl.set(41,"http://anis.konkuk.ac.kr/jsp.do?siteId=ANIS&menuSeq=767");
        majorUrl.set(42,"http://cropscience.konkuk.ac.kr/html.do?siteId=CROPSCIENCE&menuSeq=1443");
        majorUrl.set(43,"http://foodbio.konkuk.ac.kr/html.do?siteId=FOODBIO&menuSeq=1746");
        majorUrl.set(44,"http://kufsm.konkuk.ac.kr/jsp.do?siteId=KUFSM&menuSeq=1979");
        majorUrl.set(45,"http://ehs.konkuk.ac.kr/html.do?siteId=HEALTHENV&menuSeq=2023");
        majorUrl.set(46,"http://fla.konkuk.ac.kr/html.do?siteId=FLA&menuSeq=2700");} //산림조경학과

        for(int i=0;i<majorName.size();i++){
            Log.d("majorname number",i+" "+majorName.get(i));
            Log.d("majorurl number",majorUrl.get(i));
        }

        nameUrl.add(majorName);
        nameUrl.add(majorUrl);

        return nameUrl;
    }
}
