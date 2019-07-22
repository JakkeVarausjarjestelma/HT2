package com.example.ht2;

public class Sport {
    private int sportID;
    private String name;

    public String getName(){
        return name;
    }

    public void setName(String name ) {
        this.name = name;
    }

    public int getSportID() {
        return sportID;
    }

    public void setSportID(int sportID) {
        this.sportID = sportID;
    }

    public Sport(int sportID, String name) {
        this.sportID = sportID;
        this.name = name;
    }

}
