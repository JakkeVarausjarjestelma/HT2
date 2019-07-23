package com.example.ht2;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {
    private String name;
    private int roomID;
    private int sportID;

    protected Room(Parcel in) {
        name = in.readString();
        roomID = in.readInt();
        sportID = in.readInt();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(roomID);
        parcel.writeInt(sportID);
    }
}
