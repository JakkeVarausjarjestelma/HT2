package com.example.ht2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.ht2.BookingSystemContract.*;

// ÄLÄ KÄYTÄ
public class BookingSystemDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "varausjarjestelma.sql";
    public static final int DATABASE_VERSION = 1;

    public BookingSystemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SEURA_TABLE = "CREATE TABLE " +
                SeuraEntry.TABLE_NAME + " (" +
                SeuraEntry.COLUMN_SEURAID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SeuraEntry.COLUMN_NIMI + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_SEURA_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}