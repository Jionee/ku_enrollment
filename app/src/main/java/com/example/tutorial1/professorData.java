package com.example.tutorial1;

public class professorData {

    private String name;
    private String email;
    private String room;
    private String phone;

    //생성자
    public professorData() {
    }
    public professorData(String name, String email, String room, String phone) {
        this.name = name;
        this.email = email;
        this.room = room;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
