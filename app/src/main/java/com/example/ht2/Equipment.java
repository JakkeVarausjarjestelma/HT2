package com.example.ht2;

public class Equipment {
    private int equipmentID;
    private int roomID;
    private String name;

    public String getName(){
        return name;
    }

    public void setName(String name ) {
        this.name = name;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Equipment( int equipmentID, int roomID, String name) {
        this.roomID = roomID;
        this.equipmentID = equipmentID;
        this.name = name;
    }
}
