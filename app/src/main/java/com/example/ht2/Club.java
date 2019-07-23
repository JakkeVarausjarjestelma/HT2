package com.example.ht2;

import android.os.Parcel;
import android.os.Parcelable;

public class Club implements Parcelable {
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


    protected Club(Parcel in) {
        clubID = in.readInt();
        name = in.readString();
    }

    public static final Creator<Club> CREATOR = new Creator<Club>() {
        @Override
        public Club createFromParcel(Parcel in) {
            return new Club(in);
        }

        @Override
        public Club[] newArray(int size) {
            return new Club[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(clubID);
        parcel.writeString(name);
    }
    @Override
    public String toString() {
        return this.getName();
    }
}
