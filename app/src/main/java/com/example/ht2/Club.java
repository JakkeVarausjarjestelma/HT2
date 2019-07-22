package com.example.ht2;

public class Club {
    private int clubID;
    private String name;

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public String getName(){
        return name;
    }

    public void setName(String name ) {
        this.name = name;
    }

    public Club(int clubID, String name) {
        this.clubID = clubID;
        this.name = name;
    }

}
