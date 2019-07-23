package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import com.example.ht2.BookingSystemContract.*;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button testbutton;

    public ArrayList<Club> listClub;
    public ArrayList<Booker> listBooker;
    public ArrayList<Sport> listSport;
    public ArrayList<Room> listRoom;
    public ArrayList<Equipment> listEquipment;
    public ArrayList<Booking> listBooking;
    public ArrayList<Person> listPerson;

    public SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        testbutton = findViewById(R.id.testbutton);

        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);
                    mDatabase = dbHelper.getWritableDatabase();
                    insertValuesToClub(mDatabase, listClub.size(), "Yksityinen");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(listClub.size());
            }
        });

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openCreateUser();
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openMakeBooking();
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openListBookings();
            }
        });

        listClub = new ArrayList<Club>();
        listBooker = new ArrayList<Booker>();
        listSport = new ArrayList<Sport>();
        listRoom = new ArrayList<Room>();
        listEquipment = new ArrayList<Equipment>();
        listBooking = new ArrayList<Booking>();
        listPerson = new ArrayList<Person>();

        try {
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            mDatabase = dbHelper.getWritableDatabase();
            //insertValuesToClub(mDatabase, 0, "Yksityinen");
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadCursorToListClub(loadTableToCursor(ClubEntry.TABLE_NAME));
        loadCursorToListBooker(loadTableToCursor(BookerEntry.TABLE_NAME));
        loadCursorToListBooking(loadTableToCursor(BookingEntry.TABLE_NAME));
        loadCursorToListEquipment(loadTableToCursor(EquipmentEntry.TABLE_NAME));
        loadCursorToListSport(loadTableToCursor(SportEntry.TABLE_NAME));
        loadCursorToListPerson(loadTableToCursor(PersonEntry.TABLE_NAME));
        loadCursorToListRoom(loadTableToCursor(RoomEntry.TABLE_NAME));

        /*testbutton = findViewById(R.id.testbutton);
        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testi();
            }
        });
        */


    }

    /*public void testi(){
        loadCursorToListClub(loadTableToCursor("Seura"));
        System.out.println("Koko on " + listClub.size());

        for (int i = 0; i <listClub.size(); i++) {
            System.out.println(listClub.get(i).getName());
        }
    }
    */

    public void loadCursorToListClub(Cursor cursor){
        if (cursor.moveToFirst()) {

            do {int clubID = cursor.getInt(0);
                String name = cursor.getString(1);
                System.out.println(name);
                listClub.add(new Club(clubID, name));
            } while (cursor.moveToNext());

    }
    }
    public void loadCursorToListBooker(Cursor cursor){
        if (cursor.moveToFirst()) {
            do {
                int bookerID = cursor.getInt(0);
                int clubID = cursor.getInt(1);
                listBooker.add(new Booker(bookerID, clubID));
            } while (cursor.moveToNext());

        }
    }
    public void loadCursorToListSport(Cursor cursor){
        if (cursor.moveToFirst()) {

            do {int sportID = cursor.getInt(0);
                String name = cursor.getString(1);
                listSport.add(new Sport(sportID, name));
            } while (cursor.moveToNext());

        }
    }
    public void loadCursorToListRoom(Cursor cursor){
        if (cursor.moveToFirst()) {

            do {int roomID = cursor.getInt(0);
                int sportID = cursor.getInt(1);
                String name = cursor.getString(2);
                listRoom.add(new Room(roomID, sportID, name));
            } while (cursor.moveToNext());

        }
    }
    public void loadCursorToListEquipment(Cursor cursor){
        if (cursor.moveToFirst()) {

            do {int equipmentID = cursor.getInt(0);
                int roomID = cursor.getInt(1);
                String name = cursor.getString(2);
                listEquipment.add(new Equipment(equipmentID, roomID, name));
            } while (cursor.moveToNext());

        }
    }
    public void loadCursorToListBooking(Cursor cursor){
        if (cursor.moveToFirst()) {

            do {int bookingID = cursor.getInt(0);
                int bookerID = cursor.getInt(1);
                int roomID = cursor.getInt(2);
                String startTime = cursor.getString(3);
                String endTime = cursor.getString(4);
                String date = cursor.getString(5);
                listBooking.add(new Booking(bookingID, bookerID, roomID, startTime, endTime, date));
            } while (cursor.moveToNext());

        }
    }
    public void loadCursorToListPerson(Cursor cursor){
        if (cursor.moveToFirst()) {
            do {String name = cursor.getString(0);
                String phoneNumber = cursor.getString(1);
                int bookerID = cursor.getInt(2);
                listPerson.add(new Person(name, phoneNumber, bookerID));
            } while (cursor.moveToNext());

        }
    }





    public Cursor loadTableToCursor(String tableName) {
        DataBaseHelper myDBHelper = null;
        try {
            myDBHelper = new DataBaseHelper(MainActivity.this);
            myDBHelper.createdatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myDBHelper.opendatabase();
        Cursor cursor = myDBHelper.query(tableName, null, null, null, null, null, null);

        return cursor;
    }




    public void openCreateUser(){
        // Luo käyttäjä
        Intent intent = new Intent(this, CreateUser.class);
        startActivity(intent);
    }

    public void openMakeBooking(){
        //  Tee varaus
        Intent intent = new Intent(this, MakeBooking.class);
        startActivity(intent);
    }

    public void openListBookings(){
        //  Tee varaus

        Intent intent = new Intent(this, ListBookings.class);

        intent.putExtra("Club", listClub);
        intent.putExtra("Sport", listSport);
        intent.putExtra("Room", listRoom);
        intent.putExtra("Person", listPerson);
        intent.putExtra("Equipment", listEquipment);
        intent.putExtra("Booker", listBooker);
        intent.putExtra("Booking", listBooking);
        startActivity(intent);
    }


    /*public void write(){
        String s = "SaiPa";
        ContentValues cv = new ContentValues();
        cv.put(BookingSystemContract.SeuraEntry.COLUMN_NIMI, s);
        database.insert(BookingSystemContract.SeuraEntry.TABLE_NAME, null, cv);
        System.out.println("Kirjoitettu");
        }
        */


    /*
    public void read() {
        System.out.println("Luetaan");
        Cursor cursor = database.rawQuery("SELECT * FROM " + BookingSystemContract.SeuraEntry.TABLE_NAME + ";", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String bookName = cursor.getString(cursor.getColumnIndex(BookingSystemContract.SeuraEntry.COLUMN_NIMI));
                    String id = cursor.getString(cursor.getColumnIndex(BookingSystemContract.SeuraEntry.COLUMN_SEURAID));
                    System.out.println(bookName + " " + id);
                } while (cursor.moveToNext());
            }
        }
    }
*/
    public void insertValuesToClub(SQLiteDatabase sqLiteDatabase, int id, String name){
        ContentValues cv = new ContentValues();
        cv.put(ClubEntry.COLUMN_NAME, name);
        cv.put(ClubEntry.COLUMN_CLUBID, id);
        sqLiteDatabase.insert(ClubEntry.TABLE_NAME, null, cv);
    }


};






