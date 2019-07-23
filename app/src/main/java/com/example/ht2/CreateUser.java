package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;


public class CreateUser extends AppCompatActivity {
    ArrayList listClub;
    public ArrayList listPerson;
    public ArrayList listBooker;
    EditText nametext;
    EditText phonenumbertext;
    Button button;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        Intent intent = getIntent();
        listClub = intent.getParcelableArrayListExtra("Club");
        listPerson = intent.getParcelableArrayListExtra("Person");
        listBooker = intent.getParcelableArrayListExtra("Booker");

        nametext = findViewById(R.id.editText);
        phonenumbertext = findViewById(R.id.editText5);
        button = findViewById(R.id.button5);
        spinner = findViewById(R.id.spinner4);

        ArrayAdapter<Club> adapter = new ArrayAdapter<Club>(this, android.R.layout.simple_spinner_item, listClub);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nametext.getText().toString();
                String phonenmber = phonenumbertext.getText().toString();
                int clubID = ((Club) spinner.getSelectedItem()).getClubID();
                int bookerid = listBooker.size();
                try {
                    creatBookerAndPerson(name, phonenmber, bookerid, clubID);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });



        TextView tv =  findViewById(R.id.tv);

    }



    public void creatBookerAndPerson(String name, String phonenumber, int bookerid, int clubid) throws IOException {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase mDatabase = dbHelper.getWritableDatabase();
        insertValuesToBooker(mDatabase, listBooker.size(), clubid);
        insertValuesToPerson(mDatabase, bookerid, name, phonenumber);

    }
    // Insert

    public void insertValuesToBooker(SQLiteDatabase sqLiteDatabase, int id, int clubID){
        ContentValues cv = new ContentValues();
        cv.put(BookingSystemContract.BookerEntry.COLUMN_BOOKERID, id);
        cv.put(BookingSystemContract.BookerEntry.COLUMN_CLUBID, clubID);
        sqLiteDatabase.insert(BookingSystemContract.BookerEntry.TABLE_NAME, null, cv);
    }


    public void insertValuesToPerson(SQLiteDatabase sqLiteDatabase, int bookerID, String name, String phonenumber) {
        ContentValues cv = new ContentValues();
        cv.put(BookingSystemContract.PersonEntry.COLUMN_BOOKERID, bookerID);
        cv.put(BookingSystemContract.PersonEntry.COLUMN_NAME, name);
        cv.put(BookingSystemContract.PersonEntry.COLUMN_PHONENUMBER, phonenumber);

    }


}

