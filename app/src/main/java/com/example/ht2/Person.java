package com.example.ht2;

public class Person {
    private String name;
    private String phoneNumber;
    private int bookerID;

    public String getName(){
        return name;
    }

    public void setName(String name ) {
        this.name = name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public int getBookerID() {
        return bookerID;
    }

    public void setBookerID(int bookerID) {
        this.bookerID = bookerID;
    }

    public Person(String name, String phoneNumber, int bookerID) {
        this.bookerID = bookerID;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }
}
