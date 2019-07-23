package com.example.ht2;

import android.os.Parcel;
import android.os.Parcelable;

public class Sport implements Parcelable {
    private int sportID;
    private String name;

    protected Sport(Parcel in) {
        sportID = in.readInt();
        name = in.readString();
    }

    public static final Creator<Sport> CREATOR = new Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel in) {
            return new Sport(in);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(sportID);
        parcel.writeString(name);
    }
}
