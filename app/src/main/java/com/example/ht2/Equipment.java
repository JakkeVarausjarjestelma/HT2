package com.example.ht2;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipment implements Parcelable {
    private int equipmentID;
    private int roomID;
    private String name;

    protected Equipment(Parcel in) {
        equipmentID = in.readInt();
        roomID = in.readInt();
        name = in.readString();
    }

    public static final Creator<Equipment> CREATOR = new Creator<Equipment>() {
        @Override
        public Equipment createFromParcel(Parcel in) {
            return new Equipment(in);
        }

        @Override
        public Equipment[] newArray(int size) {
            return new Equipment[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(equipmentID);
        parcel.writeInt(roomID);
        parcel.writeString(name);
    }
}
