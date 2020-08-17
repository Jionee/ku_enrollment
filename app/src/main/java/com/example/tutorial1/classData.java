package com.example.tutorial1;

import java.util.ArrayList;

public class classData {
    private String numbers;
    private String name;
    private String professor;
    private String time;
    private String empty;
    private String current;
    private ArrayList<String> basket;//학년별 수강바구니
    private ArrayList<String> gradeEmpty;//학년별 수강인원
    private ArrayList<String> gradeCurrent;//학년별 전체인원
    private String field; //심교 영역


    //생성자
    public classData() {
    }
    public classData(String numbers, String name, String professor, String time,String empty, String current,
                     ArrayList<String> basket,ArrayList<String> gradeEmpty, ArrayList<String> gradeCurrent, String field) {
        this.numbers=numbers;
        this.name=name;
        this.professor=professor;
        this.time=time;
        this.empty=empty;
        this.current=current;
        this.basket = basket;
        this.gradeEmpty = gradeEmpty;
        this.gradeCurrent = gradeCurrent;
        this.field = field;
    }

    //getter && setter
    public ArrayList<String> getBasket() {
        return basket;
    }

    public void setBasket(ArrayList<String> basket) {
        this.basket = basket;
    }

    public ArrayList<String> getGradeEmpty() {
        return gradeEmpty;
    }

    public void setGradeEmpty(ArrayList<String> gradeEmpty) {
        this.gradeEmpty = gradeEmpty;
    }

    public ArrayList<String> getGradeCurrent() {
        return gradeCurrent;
    }

    public void setGradeCurrent(ArrayList<String> gradeCurrent) {
        this.gradeCurrent = gradeCurrent;
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }







}
