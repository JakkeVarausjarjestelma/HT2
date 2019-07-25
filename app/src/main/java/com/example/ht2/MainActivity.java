package com.example.ht2;

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
    private Button testbutton;

    public ArrayList<Club> listClub;
    public ArrayList<Booker> listBooker;
    public ArrayList<Sport> listSport;
    public ArrayList<Room> listRoom;
    public ArrayList<Equipment> listEquipment;
    public ArrayList<Booking> listBooking;
    public ArrayList<Person> listPerson;

    public SQLiteDatabase mDatabase;
    public SQLiteDatabase db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listClub = new ArrayList<Club>();
        listBooker = new ArrayList<Booker>();
        listSport = new ArrayList<Sport>();
        listRoom = new ArrayList<Room>();
        listEquipment = new ArrayList<Equipment>();
        listBooking = new ArrayList<Booking>();
        listPerson = new ArrayList<Person>();

        /*try {
            DataBaseHelper DBHelper = new DataBaseHelper(MainActivity.this);
            db = DBHelper.getWritableDatabase();
            boolean i = DBHelper.checkdatabase();
            if (i == true) {
                System.out.println("Tyhjä db");
                insertBeginValues(db);
            } else {System.out.println("on jo db");}

        } catch (IOException e) {
            e.printStackTrace();
        }
        */

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
                    Cursor c = loadTableToCursor(ClubEntry.TABLE_NAME);
                    loadCursorToListClub(c);


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



        try {
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            mDatabase = dbHelper.getWritableDatabase();

            //insertValuesToClub(mDatabase, 0, "Yksityinen");
            //listClub.add(new Club(0, "Yksityinen"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        loadCursorToListClub(loadTableToCursor(ClubEntry.TABLE_NAME));
        loadCursorToListBooker(loadTableToCursor(BookerEntry.TABLE_NAME));
        loadCursorToListSport(loadTableToCursor(SportEntry.TABLE_NAME));
        loadCursorToListRoom(loadTableToCursor(RoomEntry.TABLE_NAME));
        loadCursorToListEquipment(loadTableToCursor(EquipmentEntry.TABLE_NAME));
        loadCursorToListBooking(loadTableToCursor(BookingEntry.TABLE_NAME));
        loadCursorToListPerson(loadTableToCursor(PersonEntry.TABLE_NAME));



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
                System.out.println("seuraid = " + bookerID);
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

        System.out.println(cursor.getColumnCount());
        if (cursor.moveToFirst()) {

            do {int roomID = cursor.getInt(0);
            System.out.println("SaliID: " +roomID);
                int sportID = cursor.getInt(1);
                System.out.println("LajiID: " + sportID);
                String name = cursor.getString(2);
                System.out.println("Lajinimi: " + name);
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

            System.out.println(cursor.getCount());
            do {int bookingID = cursor.getInt(0);
            System.out.println("Varausid = " + bookingID);
                int bookerID = cursor.getInt(1);
                System.out.println("varaajaid = " + bookerID);
                int roomID = cursor.getInt(2);
                System.out.println("Saliid = " + roomID);
                String startTime = cursor.getString(3);
                System.out.println("Alkuaika = " + startTime);
                String endTime = cursor.getString(4);
                System.out.println("Loppuaika= " + endTime);
                String date = cursor.getString(5);
                System.out.println("pvm = " + date);
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
            //myDBHelper.createdatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //myDBHelper.opendatabase();
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        return cursor;
    }




    public void openCreateUser(){
        // Luo käyttäjä
        Intent intent = getAllListIntents(CreateUser.class);
        startActivity(intent);
    }

    public void openMakeBooking(){
        //  Tee varaus
        Intent intent = getAllListIntents(MakeBooking.class);
        startActivity(intent);
    }

    public void openListBookings(){
        //  Tee varaus

        Intent intent = getAllListIntents(ListBookings.class);
        startActivity(intent);
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

    public void insertBeginValues(SQLiteDatabase db){
        insertValuesToClub(db, 0, "Pesäysit" );
        insertValuesToClub(db, 1, "Fera" );
        insertValuesToBooker(db, 0, 0);
        insertValuesToPerson(db, 0, "Lantta", "314159");
        insertValuesToPerson(db, 1, "Aleksi", "314159");
        insertValuesToSport(db, 0, "Pesis");
        insertValuesToSport(db, 1, "Futis");
        insertValuesToRoom(db, 0,0, "Vanhis");
        insertValuesToEquipment(db, 0, 0, "Pesisvälineet");
        insertValuesToBooking(db, 0, 0, 0, "0800", "1000", "24072019");
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
        sqLiteDatabase.insert("'"+ClubEntry.TABLE_NAME+"'", null, cv);
    }


        //String sql = "INSERT INTO Seura VALUES( " + id + " , " + name + " );";
        //sqLiteDatabase.execSQL(sql);

    public void insertValuesToBooker(SQLiteDatabase sqLiteDatabase, int id, int clubID){
        ContentValues cv = new ContentValues();
        cv.put(BookerEntry.COLUMN_BOOKERID, id);
        cv.put(BookerEntry.COLUMN_CLUBID, clubID);
        listBooker.add(new Booker(id, clubID));
        sqLiteDatabase.insert("'" +BookerEntry.TABLE_NAME+"'", null, cv);
    }
    public void insertValuesToSport(SQLiteDatabase sqLiteDatabase, int id, String name){
        ContentValues cv = new ContentValues();
        cv.put(SportEntry.COLUMN_NAME, name);
        cv.put(SportEntry.COLUMN_SPORTID, id);
        listSport.add(new Sport(id, name));
        sqLiteDatabase.insert("'" +SportEntry.TABLE_NAME+ "'", null, cv);
    }

    public void insertValuesToRoom(SQLiteDatabase sqLiteDatabase, int id, int SportID, String name){
        ContentValues cv = new ContentValues();
        cv.put(RoomEntry.COLUMN_ROOMID, id);
        cv.put(RoomEntry.COLUMN_NAME, name);
        cv.put(RoomEntry.COLUMN_SPORTID, SportID);

        listRoom.add(new Room(id, SportID, name));
        sqLiteDatabase.insert("'"+RoomEntry.TABLE_NAME+"'", null, cv);
    }

    public void insertValuesToEquipment(SQLiteDatabase sqLiteDatabase, int id, int RoomID, String name) {
        ContentValues cv = new ContentValues();
        cv.put(EquipmentEntry.COLUMN_EQUIPMENTID, id);
        cv.put(EquipmentEntry.COLUMN_ROOMID, RoomID);
        cv.put(EquipmentEntry.COLUMN_NAME, name);
        listEquipment.add(new Equipment(id, RoomID, name));
        sqLiteDatabase.insert("'"+ EquipmentEntry.TABLE_NAME+"'", null, cv);
    }
    public void insertValuesToBooking(SQLiteDatabase sqLiteDatabase, int id, int bookerID, int RoomID, String startime, String endtime, String date){
        ContentValues cv = new ContentValues();
        cv.put(BookingEntry.COLUMN_BOOKINGID, id);
        cv.put(BookingEntry.COLUMN_BOOKERID, bookerID);
        cv.put(BookingEntry.COLUMN_ROOMID, RoomID);
        cv.put(BookingEntry.COLUMN_STARTTIME, startime);
        cv.put(BookingEntry.COLUMN_ENDTIME, endtime);
        cv.put(BookingEntry.COLUMN_DATE, date);
        listBooking.add(new Booking(id, bookerID, RoomID, startime, endtime, date));
        sqLiteDatabase.insert("'"+BookingEntry.TABLE_NAME+"'", null, cv);
    }
    public void insertValuesToPerson(SQLiteDatabase sqLiteDatabase, int bookerID, String name, String phonenumber) {
        ContentValues cv = new ContentValues();
        System.out.println("Persoonan lisäys");
        cv.put(PersonEntry.COLUMN_BOOKERID, bookerID);
        cv.put(PersonEntry.COLUMN_NAME, name);
        System.out.println(name);
        cv.put(PersonEntry.COLUMN_PHONENUMBER, phonenumber);
        listPerson.add(new Person(name, phonenumber, bookerID));
        sqLiteDatabase.insert("'"+PersonEntry.TABLE_NAME+"'", null, cv);

    }

};






