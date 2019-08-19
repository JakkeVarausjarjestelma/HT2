package com.example.ht2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.example.ht2.BookingSystemContract.*;


public class DataBaseHelper extends SQLiteOpenHelper {
    private final Context myContext;
    private static String DB_NAME = "varausjarjestelmafd.sql";
    private static int DB_VERSION = 1;
    public SQLiteDatabase database;
    private String DB_PATH = null;
    static String CREATE_DB_TABLE = null;
    static String CREATE_OTHER_DB_TABLES = null;



    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        Log.e("Path 1", DB_PATH);
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if(dbexist) {
            System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public boolean checkdatabase() {
        SQLiteDatabase checkdb = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkdb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        if (checkdb != null){
            checkdb.close();
        }
        return checkdb != null ;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);


        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer,0,length);
        }
        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;

        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if(database != null) {
            database.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        CREATE_DB_TABLE = " CREATE TABLE IF NOT EXISTS " + ClubEntry.TABLE_NAME + " (" +
                ClubEntry.COLUMN_CLUBID +" INTEGER PRIMARY KEY, " +
                ClubEntry.COLUMN_NAME + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_DB_TABLE );
        CREATE_OTHER_DB_TABLES =
                " CREATE TABLE IF NOT EXISTS " + BookerEntry.TABLE_NAME + " (" +
                BookerEntry.COLUMN_BOOKERID +" INTEGER PRIMARY KEY, " +
                BookerEntry.COLUMN_CLUBID + " INTEGER, " +
                " FOREIGN KEY ("+BookerEntry.COLUMN_CLUBID+") REFERENCES "+ ClubEntry.TABLE_NAME+"("+ClubEntry.COLUMN_CLUBID+"));";
        // Sport
        sqLiteDatabase.execSQL(CREATE_OTHER_DB_TABLES );
        CREATE_OTHER_DB_TABLES =
                " CREATE TABLE IF NOT EXISTS " + SportEntry.TABLE_NAME + " (" +
                SportEntry.COLUMN_SPORTID +" INTEGER PRIMARY KEY, " +
                SportEntry.COLUMN_NAME + " TEXT);";
        // Room
        sqLiteDatabase.execSQL(CREATE_OTHER_DB_TABLES );
        CREATE_OTHER_DB_TABLES =
                " CREATE TABLE IF NOT EXISTS " + RoomEntry.TABLE_NAME + " (" +
                RoomEntry.COLUMN_ROOMID +" INTEGER PRIMARY KEY, " +
                RoomEntry.COLUMN_SPORTID + " INTEGER, " +
                        RoomEntry.COLUMN_NAME + " TEXT, " +
                " FOREIGN KEY ("+RoomEntry.COLUMN_SPORTID+") REFERENCES "+ SportEntry.TABLE_NAME+"("+SportEntry.COLUMN_SPORTID+"));";
        // Equipment
        sqLiteDatabase.execSQL(CREATE_OTHER_DB_TABLES );
        CREATE_OTHER_DB_TABLES =
                " CREATE TABLE IF NOT EXISTS " + EquipmentEntry.TABLE_NAME + " (" +
                EquipmentEntry.COLUMN_EQUIPMENTID +" INTEGER PRIMARY KEY, " +
                EquipmentEntry.COLUMN_ROOMID + " INTEGER, " +
                EquipmentEntry.COLUMN_NAME + " TEXT, " +
                " FOREIGN KEY ("+EquipmentEntry.COLUMN_ROOMID+") REFERENCES "+ RoomEntry.TABLE_NAME+"("+RoomEntry.COLUMN_ROOMID+"));";
        // Booking
        sqLiteDatabase.execSQL(CREATE_OTHER_DB_TABLES );
        CREATE_OTHER_DB_TABLES =
                " CREATE TABLE IF NOT EXISTS " + BookingEntry.TABLE_NAME + " (" +
                        BookingEntry.COLUMN_BOOKINGID+" INTEGER PRIMARY KEY, " +
                        BookingEntry.COLUMN_BOOKERID + " INTEGER, " +
                        BookingEntry.COLUMN_ROOMID + " INTEGER, " +
                        BookingEntry.COLUMN_STARTTIME + " TEXT, " +
                        BookingEntry.COLUMN_ENDTIME + " TEXT, " +
                        BookingEntry.COLUMN_DATE + " TEXT, " +
                " FOREIGN KEY ("+BookingEntry.COLUMN_ROOMID+") REFERENCES "+ RoomEntry.TABLE_NAME+"("+RoomEntry.COLUMN_ROOMID+")," +
                " FOREIGN KEY ("+BookingEntry.COLUMN_BOOKERID+") REFERENCES "+ BookerEntry.TABLE_NAME+"("+BookerEntry.COLUMN_BOOKERID+"));";

        // Person
        sqLiteDatabase.execSQL(CREATE_OTHER_DB_TABLES );
        CREATE_OTHER_DB_TABLES =
                " CREATE TABLE IF NOT EXISTS " + PersonEntry.TABLE_NAME + " (" +
                PersonEntry.COLUMN_NAME + " VARCHAR(64), " +
                PersonEntry.COLUMN_BOOKERID +" INTEGER PRIMARY KEY, " +
                PersonEntry.COLUMN_PHONENUMBER + " VARCHAR(64)," +
                " FOREIGN KEY ("+PersonEntry.COLUMN_BOOKERID+") REFERENCES "+ BookerEntry.TABLE_NAME+"("+BookerEntry.COLUMN_BOOKERID+"));";
        sqLiteDatabase.execSQL(CREATE_OTHER_DB_TABLES );
        String INSERT_INTO;
        //INSERT_INTO = "INSERT INTO "+ ClubEntry.TABLE_NAME +" ("+ ClubEntry.COLUMN_CLUBID + ", " + ClubEntry.COLUMN_NAME + ") VALUES('0', 'Moikkeli');";
        //sqLiteDatabase.execSQL(INSERT_INTO);
        insertValuesToClub(sqLiteDatabase, 0, "Yksityinen");
        insertValuesToClub(sqLiteDatabase, 1, "Lpr Maila-Veikot");
        insertValuesToClub(sqLiteDatabase, 2, "Suunnistus-Jussit");
        insertValuesToBooker(sqLiteDatabase, 0, 0);
        insertValuesToBooker(sqLiteDatabase, 1, 1);
        insertValuesToBooker(sqLiteDatabase, 2, 2);
        insertValuesToSport(sqLiteDatabase, 0, "Muut palloilulajit");
        insertValuesToSport(sqLiteDatabase, 1, "Tennis");
        insertValuesToSport(sqLiteDatabase, 2, "Sulkapallo");
        insertValuesToRoom(sqLiteDatabase, 0, 0, "Liikuntasali");
        insertValuesToRoom(sqLiteDatabase, 1, 0, "Monitoimisali");
        insertValuesToEquipment(sqLiteDatabase, 0, 0, "Pallohäkki");
        insertValuesToEquipment(sqLiteDatabase, 1, 1, "Pallovarasto");
        insertValuesToRoom(sqLiteDatabase, 2, 1, "Tenniskenttä1");
        insertValuesToRoom(sqLiteDatabase, 3, 1, "Tenniskenttä2");
        insertValuesToRoom(sqLiteDatabase, 4, 2, "Sulkapallokenttä1");
        insertValuesToRoom(sqLiteDatabase, 5, 2, "Sulkapallokenttä2");
        insertValuesToBooking(sqLiteDatabase, 0, 0, 0, "08:00", "10:00", "20-12-2019");
        insertValuesToBooking(sqLiteDatabase, 1, 0, 1, "08:00", "10:00", "21-12-2019");
        insertValuesToBooking(sqLiteDatabase, 2, 1, 0, "10:00", "12:00", "20-12-2019");
        insertValuesToBooking(sqLiteDatabase, 3, 1, 3, "08:00", "10:00", "21-12-2019");
        insertValuesToBooking(sqLiteDatabase, 4, 2, 0, "10:00", "12:00", "20-12-2019");
        insertValuesToBooking(sqLiteDatabase, 5, 2, 4, "08:00", "10:00", "21-12-2019");

        insertValuesToPerson(sqLiteDatabase, 0, "Henu", "3141592653");
        insertValuesToPerson(sqLiteDatabase, 1, "Veikko", "123");
        insertValuesToPerson(sqLiteDatabase, 2, "Jussi", "100");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i1 > i) {
            // do whatever you want
        }
            }



    // Insert
    public void insertValuesToClub(SQLiteDatabase sqLiteDatabase, int id, String name){
        ContentValues cv = new ContentValues();
        cv.put(ClubEntry.COLUMN_NAME, name);
        cv.put(ClubEntry.COLUMN_CLUBID, id);
        sqLiteDatabase.insert(ClubEntry.TABLE_NAME, null, cv);
    }
    public void insertValuesToBooker(SQLiteDatabase sqLiteDatabase, int id, int clubID){
        ContentValues cv = new ContentValues();
        cv.put(BookerEntry.COLUMN_BOOKERID, id);
        cv.put(BookerEntry.COLUMN_CLUBID, clubID);
        sqLiteDatabase.insert(BookerEntry.TABLE_NAME, null, cv);
    }
    public void insertValuesToSport(SQLiteDatabase sqLiteDatabase, int id,  String name){
        ContentValues cv = new ContentValues();
        cv.put(SportEntry.COLUMN_NAME, name);
        cv.put(SportEntry.COLUMN_SPORTID, id);
        sqLiteDatabase.insert(SportEntry.TABLE_NAME, null, cv);
    }

    public void insertValuesToRoom(SQLiteDatabase sqLiteDatabase, int id, int SportID, String name){
        ContentValues cv = new ContentValues();
        cv.put(RoomEntry.COLUMN_NAME, name);
        cv.put(RoomEntry.COLUMN_SPORTID, SportID);
        cv.put(RoomEntry.COLUMN_ROOMID, id);
        sqLiteDatabase.insert(RoomEntry.TABLE_NAME, null, cv);
    }

    public void insertValuesToEquipment(SQLiteDatabase sqLiteDatabase,int id, int RoomID, String name) {
        ContentValues cv = new ContentValues();
        cv.put(EquipmentEntry.COLUMN_EQUIPMENTID, id);
        cv.put(EquipmentEntry.COLUMN_ROOMID, RoomID);
        cv.put(EquipmentEntry.COLUMN_NAME, name);
        sqLiteDatabase.insert(EquipmentEntry.TABLE_NAME, null, cv);
    }
    public void insertValuesToBooking(SQLiteDatabase sqLiteDatabase,int id, int bookerID, int RoomID, String startime, String endtime, String date){
        ContentValues cv = new ContentValues();
        cv.put(BookingEntry.COLUMN_BOOKINGID, id);
        cv.put(BookingEntry.COLUMN_BOOKERID, bookerID);
        cv.put(BookingEntry.COLUMN_ROOMID, RoomID);
        cv.put(BookingEntry.COLUMN_STARTTIME, startime);
        cv.put(BookingEntry.COLUMN_ENDTIME, endtime);
        cv.put(BookingEntry.COLUMN_DATE, date);
        sqLiteDatabase.insert(BookingEntry.TABLE_NAME, null, cv);
    }
    public void insertValuesToPerson(SQLiteDatabase sqLiteDatabase, int bookerID, String name, String phonenumber) {
        ContentValues cv = new ContentValues();
        cv.put(PersonEntry.COLUMN_BOOKERID, bookerID);
        cv.put(PersonEntry.COLUMN_NAME, name);
        cv.put(PersonEntry.COLUMN_PHONENUMBER, phonenumber);
        sqLiteDatabase.insert(PersonEntry.TABLE_NAME, null, cv);

    }
}
