package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class ListBookings extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;
    Context context = null;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bookings);
        button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper myDBHelper = null;
                try {
                    myDBHelper = new DataBaseHelper(ListBookings.this);
                    myDBHelper.createdatabase();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                myDBHelper.opendatabase();
                Toast.makeText(ListBookings.this, "Success", Toast.LENGTH_SHORT).show();
                Cursor c = myDBHelper.query("TAULU", null, null, null, null, null, null);
                if (c.moveToFirst()) {
                    do {
                        Toast.makeText(ListBookings.this, "id: " + c.getString(0) + "\n" + "Nimi: " + c.getString(1), Toast.LENGTH_LONG).show();

                    } while (c.moveToNext());

                }
            }
        });




    }
}
