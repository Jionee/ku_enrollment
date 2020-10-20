package com.konkuk.suku;

public class Constants {
    public static final String tbase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp";
    public static final String base = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy=2020&ltShtm=B01015";
    public static final String sbjBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourInwonInqTime.jsp?ltYy=2020&ltShtm=B01015&sbjtId=";
    public static final String gradeBase = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourBasketInwonInq.jsp?ltYy=2020&ltShtm=B01015&fg=B&sbjtId=";
    public static final String allCulture = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy=2020&ltShtm=B01015&pobtDiv=B04054";
    public static final String proffBase1 ="http://www.konkuk.ac.kr/jsp/Coll/";
    public static final String proffBase2 = "http://www.konkuk.ac.kr/jsp/Coll/coll_index.jsp";
    //parseListProff --> 수동으로 입력한 주소들 많음 (변동 가능)

    public static final String sbjtId = "&sbjtId=";

    // B04044:전필, B04045:전선, B04061:지필, B0404P:기교, B04054:심교, B04047:교직, B04046:일선, B04054:심교, ALL:전체
    public static final String pobtDiv = "&pobtDiv=";
    public static final String cultCorsFld = "&cultCorsFld="; //기교선택
    public static final String openSust = "&openSust="; //학과

    public static final String junPil = "B04044";
    public static final String junSun = "B04045";
    public static final  String jiPil = "B04061";
    public static final String giGyo = "B0404P";
    public static final String simGyo = "B04054";
    public static final  String GyoJik = "B04047";
    public static final String IlSun = "B04046";
    public static final String ALL = "ALL";
}
