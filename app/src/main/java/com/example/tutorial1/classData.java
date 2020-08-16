package com.example.tutorial1;

public class classData {
    private String numbers;
    private String name;
    private String professor;
    private String time;
    private String empty;
    private String current;

    //생성자
    public classData() {
    }
    public classData(String numbers, String name, String professor, String time,String empty, String current) {
        this.numbers=numbers;
        this.name=name;
        this.professor=professor;
        this.time=time;
        this.empty=empty;
        this.current=current;
    }

    //getter && setter
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








}
