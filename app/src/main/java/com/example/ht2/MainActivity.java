package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

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

};






