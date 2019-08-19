package com.example.ht2;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;

import android.net.ParseException;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import java.util.ArrayList;
import com.example.ht2.BookingSystemContract.*;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;


    public ArrayList<Club> listClub;
    public ArrayList<Booker> listBooker;
    public ArrayList<Sport> listSport;
    public ArrayList<Room> listRoom;
    public ArrayList<Equipment> listEquipment;
    public ArrayList<Booking> listBooking;
    public ArrayList<Person> listPerson;

    public SQLiteDatabase mDatabase;
    public SQLiteDatabase db;
    boolean userExists = false;
    int bookerID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button);


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
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openEditBooking();
            }
        });


        try {
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            mDatabase = dbHelper.getWritableDatabase();

        } catch (IOException e) {
            e.printStackTrace();
        }

        loadDBtoArrayLists();
    }


    public void loadCursorToListClub(Cursor cursor){
        if (cursor.moveToFirst()) {
            do {int clubID = cursor.getInt(0);
                String name = cursor.getString(1);
                //System.out.println(name);
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
                String startTimeString = cursor.getString(3);
                String endTimeString = cursor.getString(4);
                String dateString = cursor.getString(5);
                String start = dateString+ "T" + startTimeString + "Z";
                String end = dateString + "T" + endTimeString + "Z";

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm'Z'");
                try {

                    java.util.Date dateStart =  format.parse(start);

                    java.util.Date dateEnd = format.parse(end);


                    listBooking.add(new Booking(bookingID, bookerID, roomID, dateStart, dateEnd));
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());

        }
    }
    public void loadCursorToListPerson(Cursor cursor){
        if (cursor.moveToFirst()) {
            do {String name = cursor.getString(0);
                int bookerID = cursor.getInt(1);
                String phoneNumber = cursor.getString(2);
                listPerson.add(new Person(name, phoneNumber, bookerID));
            } while (cursor.moveToNext());

        }
    }


    public Cursor loadTableToCursor(String tableName) {
        DataBaseHelper myDBHelper = null;
        try {
            myDBHelper = new DataBaseHelper(MainActivity.this);
            //myDBHelper.createdatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //myDBHelper.opendatabase();
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        return cursor;
    }

    @Override
    protected void onResume() { // Loads database to lists and writes json-file.
        super.onResume();
        loadDBtoArrayLists();

        try {
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            mDatabase = dbHelper.getWritableDatabase();

        } catch (IOException e) {
            e.printStackTrace();
        }
        WriteBookings writeBookings = new WriteBookings(listRoom, mDatabase);
        try {
            writeBookings.write();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void openCreateUser(){
        Intent intent = getAllListIntents(CreateUser.class);
        intent.putExtra("userExists", userExists);
        if (userExists) {
            intent.putExtra("bookerID", bookerID);
        }
        startActivityForResult(intent, 999);
    }

    public void openMakeBooking(){
        if (userExists) {
            Intent intent = getAllListIntents(MakeBooking.class);
            intent.putExtra("bookerID", bookerID);
            intent.putExtra("index", -1); // nothing to do with makeing a new booking

            startActivityForResult(intent, 999);
        } else {
            Toast.makeText(MainActivity.this, "Luo käyttäjä ensin!", Toast.LENGTH_SHORT).show();
        }

    }

    public void openListBookings(){
        Intent intent = getAllListIntents(ListBookings.class);
        startActivityForResult(intent, 999);
    }

    public void openEditBooking(){
        if (userExists) {
            Intent intent = getAllListIntents(EditBooking.class);
            intent.putExtra("userExists", userExists);
            intent.putExtra("bookerID", bookerID);
            System.out.println("BookkerID tässä on " +bookerID );
            startActivityForResult(intent, 999);
        } else {
            Toast.makeText(MainActivity.this, "Luo käyttäjä ensin!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("requestCode on: " + requestCode);
        System.out.println(("resultCode on: "+ resultCode));
        if (requestCode == 999 && resultCode == RESULT_OK) {
            System.out.println("requestCode on: " + requestCode);
            System.out.println(("resultCode on: "+ resultCode));
            System.out.println("Tietoa tulee");
            int x = data.getIntExtra("testi", 0);
            System.out.println("Äksä on: " + x);
            userExists = data.getBooleanExtra("userExists", false);
            if (userExists) {
                button1.setText("Muokkaa käyttäjän tietoja");
                bookerID = data.getIntExtra("bookerID",  -2);
            }
            System.out.println("Käyttäjä on: " + userExists);

        }
        else {System.out.print("Ei toimi");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public Intent getAllListIntents(Class<?> cls){
        Intent intent = new Intent(this, cls);
        intent.putParcelableArrayListExtra("Club", listClub);
        intent.putParcelableArrayListExtra("Sport", listSport);
        intent.putParcelableArrayListExtra("Room", listRoom);
        intent.putParcelableArrayListExtra("Person", listPerson);
        intent.putParcelableArrayListExtra("Equipment", listEquipment);
        intent.putParcelableArrayListExtra("Booker", listBooker);
        intent.putParcelableArrayListExtra("Booking", listBooking);
        return intent;
    }

    public void loadDBtoArrayLists(){
        listClub = new ArrayList<Club>();
        listBooker = new ArrayList<Booker>();
        listSport = new ArrayList<Sport>();
        listRoom = new ArrayList<Room>();
        listEquipment = new ArrayList<Equipment>();
        listBooking = new ArrayList<Booking>();
        listPerson = new ArrayList<Person>();

        loadCursorToListClub(loadTableToCursor(ClubEntry.TABLE_NAME));
        loadCursorToListBooker(loadTableToCursor(BookerEntry.TABLE_NAME));
        loadCursorToListSport(loadTableToCursor(SportEntry.TABLE_NAME));
        loadCursorToListRoom(loadTableToCursor(RoomEntry.TABLE_NAME));
        loadCursorToListEquipment(loadTableToCursor(EquipmentEntry.TABLE_NAME));
        loadCursorToListBooking(loadTableToCursor(BookingEntry.TABLE_NAME));
        loadCursorToListPerson(loadTableToCursor(PersonEntry.TABLE_NAME));
    }

};






