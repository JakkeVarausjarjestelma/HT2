package com.example.ht2;

import android.os.Parcel;
import android.os.Parcelable;

public class Booker implements Parcelable {
    private int bookerID;
    private int clubID;


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

    protected Booker(Parcel in) {
        bookerID = in.readInt();
        clubID = in.readInt();
    }

    public static final Creator<Booker> CREATOR = new Creator<Booker>() {
        @Override
        public Booker createFromParcel(Parcel in) {
            return new Booker(in);
        }

        @Override
        public Booker[] newArray(int size) {
            return new Booker[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(clubID);
        parcel.writeInt(bookerID);
    }

    @Override
    public String toString() {
        int id = this.getBookerID();
        return String.valueOf(id);
    }
}
