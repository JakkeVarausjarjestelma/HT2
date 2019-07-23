package com.example.ht2;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.sql.Time;

public class Booking implements Parcelable {
    private int bookingID;
    private int bookerID;
    private int roomID;
    private String startTime;
    private String endTime;
    private String date;

    protected Booking(Parcel in) {
        bookingID = in.readInt();
        bookerID = in.readInt();
        roomID = in.readInt();
        startTime = in.readString();
        endTime = in.readString();
        date = in.readString();
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getBookerID() {
        return bookerID;
    }

    public void setBookerID(int bookerID) {
        this.bookerID = bookerID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Booking(int bookingID, int bookerID, int roomID, String startTime, String endTime, String date) {
        this.roomID = roomID;
        this.bookingID = bookingID;
        this.bookerID = bookerID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bookingID);
        parcel.writeInt(bookerID);
        parcel.writeInt(roomID);
        parcel.writeString(startTime);
        parcel.writeString(endTime);
        parcel.writeString(date);
    }
}
