package com.example.ht2;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private String name;
    private String phoneNumber;
    private int bookerID;

    protected Person(Parcel in) {
        name = in.readString();
        phoneNumber = in.readString();
        bookerID = in.readInt();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName(){
        return name;
    }

    public void setName(String name ) {
        this.name = name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public int getBookerID() {
        return bookerID;
    }

    public void setBookerID(int bookerID) {
        this.bookerID = bookerID;
    }

    public Person(String name, String phoneNumber, int bookerID) {
        this.bookerID = bookerID;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phoneNumber);
        parcel.writeInt(bookerID);
    }
}
