package com.example.ht2;

public class Booker {
    private int bookerID;
    private int clubID;

    public Booker() {

    }

    public int getBookerID() {
        return bookerID;
    }

    public void setBookerID(int bookerID) {
        this.bookerID = bookerID;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public Booker(int bookerID, int clubID) {
        this.clubID = clubID;
        this.bookerID = bookerID;
    }

}
