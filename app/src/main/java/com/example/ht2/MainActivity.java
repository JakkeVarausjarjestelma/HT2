package com.example.ht2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.os.Parcelable;
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

            //insertValuesToClub(mDatabase, 0, "Yksityinen");
            //listClub.add(new Club(0, "Yksityinen"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadDBtoArrayLists();



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
                //System.out.println("seuraid = " + bookerID);
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

        //System.out.println(cursor.getColumnCount());
        if (cursor.moveToFirst()) {

            do {int roomID = cursor.getInt(0);
            //System.out.println("SaliID: " +roomID);
                int sportID = cursor.getInt(1);
                //System.out.println("LajiID: " + sportID);
                String name = cursor.getString(2);
                //System.out.println("Lajinimi: " + name);
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

            //System.out.println(cursor.getCount());
            do {int bookingID = cursor.getInt(0);
            //System.out.println("Varausid = " + bookingID);
                int bookerID = cursor.getInt(1);
                //System.out.println("varaajaid = " + bookerID);
                int roomID = cursor.getInt(2);
                //System.out.println("Saliid = " + roomID);
                String startTime = cursor.getString(3);
                //System.out.println("Alkuaika = " + startTime);
                String endTime = cursor.getString(4);
                //System.out.println("Loppuaika = " + endTime);
                String date = cursor.getString(5);
                //System.out.println("pvm = " + date);
                listBooking.add(new Booking(bookingID, bookerID, roomID, startTime, endTime, date));
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
    protected void onResume() {
        super.onResume();
        loadDBtoArrayLists();

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
            intent.putExtra("bookerID", bookerID);
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

};






