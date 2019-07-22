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
    private static String DB_NAME = "varausjarjestelma.sql";
    private static int DB_VERSION = 1;
    public SQLiteDatabase database;
    private String DB_PATH = null;
    static String CREATE_DB_TABLE = null;



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

    private boolean checkdatabase() {
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
        CREATE_DB_TABLE = " CREATE TABLE " + "Seura" +
                " (SeuraID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " Nimi TEXT NOT NULL); ";
        sqLiteDatabase.execSQL(CREATE_DB_TABLE);
        System.out.println("tääl oltiin");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i > i1)
            try {
                copydatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    // Querys
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Seura", null);
        return cursor;
    }

    // Insert



}
