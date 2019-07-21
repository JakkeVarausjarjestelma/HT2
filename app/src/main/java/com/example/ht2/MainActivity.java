package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Context context;
    DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookingSystemDBHelper dbHelper = new BookingSystemDBHelper(this);
        database = dbHelper.getWritableDatabase();
        spinner.findViewById(R.id.spinner);


        /*try {
            dataBaseHelper = new DataBaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Eipä toimi");
        }
        */

    }
    public void write(View v){
        String s = "SaiPa";
        ContentValues cv = new ContentValues();
        cv.put(BookingSystemContract.SeuraEntry.COLUMN_NIMI, s);
        database.insert(BookingSystemContract.SeuraEntry.TABLE_NAME, null, cv);
        System.out.println("Kirjoitettu");


    }
    public void read(View v) {
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

    };






