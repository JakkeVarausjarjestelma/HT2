package com.example.ht2;

public class Room {
    private String name;
    private int roomID;
    private int sportID;

    public String getName(){
        return name;
    }

    public void setName(String name ) {
        this.name = name;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getSportID() {
        return sportID;
    }

    public void setSportID(int sportID) {
        this.sportID = sportID;
    }

    public Room(int roomID, int sportID, String name) {
        this.roomID = roomID;
        this.sportID = sportID;
        this.name = name;
    }

}
