package com.konkuk.suku;

public class Constants {
    //1학기 B01011 2학기 B01012 하계계절학기 B01014 동계계절학기 B01015
    public static final String when = "B01012";
    public static final String year = "2021";

    public static final String tbase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp";
    public static final String base = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy="+year+"&ltShtm="+when;
    public static final String sbjBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourInwonInqTime.jsp?ltYy="+year+"&ltShtm="+when+"&sbjtId=";
    public static final String gradeBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourBasketInwonInq.jsp?ltYy="+year+"&ltShtm="+when+"&fg=B&sbjtId=";
    public static final String allCulture = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy="+year+"&ltShtm="+when+"&pobtDiv=B04054";
    public static final String proffBase1 ="http://www.konkuk.ac.kr/jsp/Coll/";
    public static final String proffBase2 = "http://www.konkuk.ac.kr/jsp/Coll/coll_index.jsp";
    //parseListProff --> 수동으로 입력한 주소들 많음 (변동 가능)

    public static final String sbjtId = "&sbjtId=";

    // B04044:전필, B04045:전선, B04061:지필, B0404P:기교, B04054:심교, B04047:교직, B04046:일선, B04054:심교, ALL:전체
    public static final String pobtDiv = "&pobtDiv=";
    public static final String cultCorsFld = "&cultCorsFld="; //기교선택
    public static final String openSust = "&openSust="; //학과2

    public static final String junPil = "B04044";
    public static final String junSun = "B04045";
    public static final  String jiPil = "B04061";
    public static final String giGyo = "B0404P";
    public static final String simGyo = "B04054";
    public static final  String GyoJik = "B04047";
    public static final String IlSun = "B04046";
    public static final String ALL = "ALL";
}
