package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
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
    TextView textView;

    private boolean userExists;
    int bookerID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        Intent intent = getIntent();
        listClub = intent.getParcelableArrayListExtra("Club");
        listPerson = intent.getParcelableArrayListExtra("Person");
        listBooker = intent.getParcelableArrayListExtra("Booker");
        userExists = intent.getBooleanExtra("userExists", false);
        if (userExists) {
            bookerID = intent.getIntExtra("bookerID", -2);
        }
        System.out.println("Käyttäjä on: " + userExists);

        nametext = findViewById(R.id.editText);
        phonenumbertext = findViewById(R.id.editText5);
        button = findViewById(R.id.button5);
        spinner = findViewById(R.id.spinner4);

        ArrayAdapter<Club> adapter = new ArrayAdapter<Club>(this, android.R.layout.simple_spinner_item, listClub);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        textView =  findViewById(R.id.tv);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nametext.getText().toString();
                String phonenmber = phonenumbertext.getText().toString();
                int clubID = ((Club) spinner.getSelectedItem()).getClubID();

                if (userExists == false) {
                int id_max = 0;
                System.out.println("Mennään for-looppiin");
                for (int i = 0; i < listBooker.size(); i++) {
                    bookerID = Integer.parseInt(listBooker.get(i).toString());
                    if (bookerID >= id_max) {
                        id_max = bookerID;
                    }
                }
                System.out.println("ID_max: " +id_max);
                bookerID = id_max + 1;
                System.out.println("BookerID: " + bookerID);
                }

                try {
                    createBookerAndPerson(name, phonenmber, bookerID, clubID);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });





    }



    public void createBookerAndPerson(String name, String phonenumber, int bookerID, int clubid) throws IOException {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase mDatabase = dbHelper.getWritableDatabase();
        if (userExists) {
            System.out.println("Päivitellään");
            updateValuesToPerson(mDatabase, name, phonenumber, bookerID);
            updateValuesToBooker(mDatabase, bookerID, clubid);

        } else {
            insertValuesToPerson(mDatabase, bookerID, name, phonenumber);
            insertValuesToBooker(mDatabase, bookerID, clubid);
        }



    }
    // Insert

    public void insertValuesToBooker(SQLiteDatabase sqLiteDatabase, int id, int clubID){
        ContentValues cv = new ContentValues();
        cv.put(BookingSystemContract.BookerEntry.COLUMN_BOOKERID, id);
        cv.put(BookingSystemContract.BookerEntry.COLUMN_CLUBID, clubID);
        sqLiteDatabase.insert(BookingSystemContract.BookerEntry.TABLE_NAME, null, cv);
        //listBooker.add(new Booker(id, clubID));
    }


    public void insertValuesToPerson(SQLiteDatabase sqLiteDatabase, int bookerID, String name, String phonenumber) {
        ContentValues cv = new ContentValues();
        cv.put(BookingSystemContract.PersonEntry.COLUMN_BOOKERID, bookerID);
        cv.put(BookingSystemContract.PersonEntry.COLUMN_NAME, name);
        cv.put(BookingSystemContract.PersonEntry.COLUMN_PHONENUMBER, phonenumber);
        sqLiteDatabase.insert(BookingSystemContract.PersonEntry.TABLE_NAME, null, cv);
        //listPerson.add(new Person(name, phonenumber, bookerID));
        userExists = true;
        textView.setText("Muokkaa käyttäjän tietoja");


    }

    public void updateValuesToPerson(SQLiteDatabase sqLiteDatabase, String name, String phonenumber, int bookerID){
        ContentValues cv = new ContentValues();
        System.out.println("Päivitetään henkilö");
        //cv.put(BookingSystemContract.PersonEntry.COLUMN_BOOKERID, bookerID);
        cv.put(BookingSystemContract.PersonEntry.COLUMN_NAME, name);
        cv.put(BookingSystemContract.PersonEntry.COLUMN_PHONENUMBER, phonenumber);
        sqLiteDatabase.update(BookingSystemContract.PersonEntry.TABLE_NAME, cv, BookingSystemContract.PersonEntry.COLUMN_BOOKERID + "="+bookerID, null);

    }

    public void updateValuesToBooker(SQLiteDatabase sqLiteDatabase, int bookerID, int clubID){
        System.out.println("Päivitetään varaaja");
        ContentValues cv = new ContentValues();
        cv.put(BookingSystemContract.BookerEntry.COLUMN_CLUBID, clubID);
        sqLiteDatabase.update(BookingSystemContract.BookerEntry.TABLE_NAME, cv, BookingSystemContract.BookerEntry.COLUMN_BOOKERID + "="+bookerID, null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Resume, userExists: " + userExists);
        if(userExists) {
            textView.setText("Muokkaa käyttäjän tietoja");
        }

    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("testi", 555);
        intent.putExtra("userExists", userExists);
        if (userExists){
            intent.putExtra("bookerID", bookerID);
        }
        setResult(RESULT_OK, intent);
        super.onBackPressed();


    }

}


