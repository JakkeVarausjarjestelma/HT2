package com.example.ht2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Booking implements Parcelable {
    private int bookingID;
    private int bookerID;
    private int roomID;
    private Date startTime;
    private Date endTime;

    protected Booking(Parcel in) {
        bookingID = in.readInt();
        bookerID = in.readInt();
        roomID = in.readInt();
        startTime = (Date) in.readSerializable();
        endTime = (Date) in.readSerializable();
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Booking(int bookingID, int bookerID, int roomID, Date startTime, Date endTime) {
        this.roomID = roomID;
        this.bookingID = bookingID;
        this.bookerID = bookerID;
        this.startTime = startTime;
        this.endTime = endTime;


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
        parcel.writeSerializable(startTime);
        parcel.writeSerializable(endTime);
    }
}
