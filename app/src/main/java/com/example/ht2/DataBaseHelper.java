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
    private static String DB_NAME = "varadxdcfdxdfdffhjfdfgshfddfkfsdjrdffgdsddjfssffdddfgfdfdfgfxsdfgsddfdhjiffkdfgjsddzxdsgsdfffhgdfgadsdfxdsasdvhdxckjdfcxvalilyyoxdfntijfjdxcvtujssdfsdfdzxfcssddfasdsesaddsfsdfdassdfsdfsdfdasdasddasdsdfsdasdfsdfsSDFGdasdf.sql";
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
        insertValuesToBooking(sqLiteDatabase, 0, 0, 0, "0800", "1000", "20122019");
        insertValuesToBooking(sqLiteDatabase, 1, 0, 1, "0800", "1000", "21122019");
        insertValuesToBooking(sqLiteDatabase, 2, 1, 0, "1000", "1200", "20122019");
        insertValuesToBooking(sqLiteDatabase, 3, 1, 3, "0800", "1000", "21122019");
        insertValuesToBooking(sqLiteDatabase, 4, 2, 0, "1000", "1200", "20122019");
        insertValuesToBooking(sqLiteDatabase, 5, 2, 4, "0800", "1000", "21122019");

        insertValuesToPerson(sqLiteDatabase, 0, "Henu", "3141592653");
        insertValuesToPerson(sqLiteDatabase, 1, "Veikko", "123");
        insertValuesToPerson(sqLiteDatabase, 2, "Jussi", "100");


        /*CREATE_OTHER_DB_TABLES = CREATE_OTHER_DB_TABLES +
                "INSERT INTO "+ ClubEntry.TABLE_NAME +" VALUES(0, Moikkeli);" +
                "INSERT INTO "+ BookerEntry.TABLE_NAME +" VALUES(0, 0);" +
                "INSERT INTO "+ SportEntry.TABLE_NAME +" VALUES( 0, Pesis);" +
                "INSERT INTO "+ RoomEntry.TABLE_NAME +" VALUES( 0, 0, Pesiskenttä );" +
                "INSERT INTO "+ EquipmentEntry.TABLE_NAME +" VALUES(0, 0, Mailat ja räpylät );" +
                "INSERT INTO "+ BookingEntry.TABLE_NAME +" VALUES(0, 0, 0, 0800, 1000, 24072019 );" +
                "INSERT INTO "+ RoomEntry.TABLE_NAME + " VALUES( Aleksi, 23423, 0);";
                */


        Cursor c = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                System.out.println(c.getString(0));
                c.moveToNext();
            }
        }

        String table = RoomEntry.TABLE_NAME;
        Cursor dbCursor = sqLiteDatabase.query(table, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        System.out.println("XXXX SDsdknvslk sdflnslänvdk");

        System.out.println("Taulu: " + table);
        System.out.println("ColumnCount: " + dbCursor.getColumnCount());
        for (int i = 0; i < columnNames.length; i++){
        System.out.println("Sarake: " + columnNames[i] + " " + i);}


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (1 > 2) {
            CREATE_DB_TABLE = " CREATE TABLE IF NOT EXISTS " + ClubEntry.TABLE_NAME + " (" +
                    ClubEntry.COLUMN_CLUBID +" INTEGER PRIMARY KEY, " +
                    ClubEntry.COLUMN_NAME + " TEXT);";
            // Booker
            CREATE_OTHER_DB_TABLES = CREATE_OTHER_DB_TABLES +
                    " CREATE TABLE IF NOT EXISTS " + BookerEntry.TABLE_NAME + " (" +
                    BookerEntry.COLUMN_BOOKERID +" INTEGER PRIMARY KEY, " +
                    BookerEntry.COLUMN_CLUBID + " INTEGER, " +
                     " FOREIGN KEY ("+BookerEntry.COLUMN_CLUBID+") REFERENCES "+ ClubEntry.TABLE_NAME+"("+ClubEntry.COLUMN_CLUBID+"));";
            // Sport
            CREATE_OTHER_DB_TABLES = CREATE_OTHER_DB_TABLES +
                    " CREATE TABLE IF NOT EXISTS " + SportEntry.TABLE_NAME + " (" +
                    SportEntry.COLUMN_SPORTID +" INTEGER PRIMARY KEY, " +
                    SportEntry.COLUMN_NAME + " TEXT);";
            // Room
            CREATE_OTHER_DB_TABLES = CREATE_OTHER_DB_TABLES +
                    " CREATE TABLE IF NOT EXISTS " + RoomEntry.TABLE_NAME + " (" +
                    RoomEntry.COLUMN_ROOMID +" INTEGER PRIMARY KEY, " +
                    RoomEntry.COLUMN_SPORTID + " INTEGER, " +
                    RoomEntry.COLUMN_NAME + " TEXT," +
                    " FOREIGN KEY ("+RoomEntry.COLUMN_SPORTID+") REFERENCES "+ SportEntry.TABLE_NAME+"("+SportEntry.COLUMN_SPORTID+"));";
            // Equipment
            CREATE_OTHER_DB_TABLES = CREATE_OTHER_DB_TABLES +
                    " CREATE TABLE IF NOT EXISTS " + EquipmentEntry.TABLE_NAME + " (" +
                    EquipmentEntry.COLUMN_EQUIPMENTID +" INTEGER PRIMARY KEY, " +
                    EquipmentEntry.COLUMN_ROOMID + " INTEGER, " +
                    EquipmentEntry.COLUMN_NAME + "TEXT," +
                    " FOREIGN KEY ("+EquipmentEntry.COLUMN_ROOMID+") REFERENCES "+ RoomEntry.TABLE_NAME+"("+RoomEntry.COLUMN_ROOMID+"));";
            // Booking
            CREATE_OTHER_DB_TABLES = CREATE_OTHER_DB_TABLES +
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
            CREATE_OTHER_DB_TABLES = CREATE_OTHER_DB_TABLES +
                    " CREATE TABLE IF NOT EXISTS " + PersonEntry.TABLE_NAME + " (" +
                    PersonEntry.COLUMN_NAME + " VARCHAR(64), " +
                    PersonEntry.COLUMN_BOOKERID +" INTEGER PRIMARY KEY, " +
                    PersonEntry.COLUMN_PHONENUMBER + " VARCHAR(64)," +
                    " FOREIGN KEY ("+PersonEntry.COLUMN_BOOKERID+") REFERENCES "+ BookerEntry.TABLE_NAME+"("+BookerEntry.COLUMN_BOOKERID+"),);";
            CREATE_OTHER_DB_TABLES = CREATE_OTHER_DB_TABLES +
                    "INSERT INTO "+ ClubEntry.TABLE_NAME +" VALUES(0, Moikkeli);" +
                    "INSERT INTO "+ BookerEntry.TABLE_NAME +" VALUES(0, 0);" +
                    "INSERT INTO "+ SportEntry.TABLE_NAME +" VALUES( 0, Pesis);" +
                    "INSERT INTO "+ RoomEntry.TABLE_NAME +" VALUES( 0, 0, Pesiskenttä );" +
                    "INSERT INTO "+ EquipmentEntry.TABLE_NAME +" VALUES(0, 0, Mailat ja räpylät );" +
                    "INSERT INTO "+ BookingEntry.TABLE_NAME +" VALUES(0, 0, 0, 0800, 1000, 24072019 );" +
                    "INSERT INTO "+ RoomEntry.TABLE_NAME + " VALUES( Aleksi, 23423, 0);";


            sqLiteDatabase.execSQL(CREATE_OTHER_DB_TABLES);

            // Insert data
            /*
            insertValuesToClub(sqLiteDatabase, 0, "Yksityinen");
            insertValuesToClub(sqLiteDatabase, 1, "LprNMKY");
            insertValuesToClub(sqLiteDatabase, 2, "Seals Basket");
            insertValuesToClub(sqLiteDatabase, 3, "Veiterä");
            */


        }
            }


    // Querys ??????
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Seura", null);
        return cursor;
    }


    // Insert
    public void insertValuesToClub(SQLiteDatabase sqLiteDatabase, int id, String name){
        ContentValues cv = new ContentValues();
        cv.put(ClubEntry.COLUMN_NAME, name);
        cv.put(ClubEntry.COLUMN_CLUBID, id);
        sqLiteDatabase.insert(ClubEntry.TABLE_NAME, null, cv);

        //String sql = "INSERT INTO Seura VALUES( " + id + " , " + name + " );";
        //sqLiteDatabase.execSQL(sql);
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
