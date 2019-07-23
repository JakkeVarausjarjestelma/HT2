package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.NumberPicker;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;


public class ListBookings extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;
    Context context = null;
    Button button;
    private Spinner spinner;
    private Spinner spinner2;
    private Spinner spinner3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bookings);
        button = findViewById(R.id.button7);


        Button testButton;
        testButton = findViewById(R.id.button);



        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);

        Intent intent = getIntent();
        final ArrayList ClubList = intent.getParcelableArrayListExtra("Club");

        Bundle bundle2 = getIntent().getExtras();
        ArrayList SportList = bundle2.getParcelableArrayList("Sport");

        Bundle bundle3= getIntent().getExtras();
        ArrayList RoomList = bundle3.getParcelableArrayList("Room");

        Bundle bundle4 = getIntent().getExtras();
        ArrayList PersonList = bundle4.getParcelableArrayList("Person");

        Bundle bundle5 = getIntent().getExtras();
        ArrayList EquipmentList = bundle5.getParcelableArrayList("Equipment");

        Bundle bundle6 = getIntent().getExtras();
        ArrayList BookerList = bundle6.getParcelableArrayList("Booker");

        Bundle bundle7 = getIntent().getExtras();
        ArrayList BookingList = bundle7.getParcelableArrayList("Booking");




        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(ClubList.size());
            }
        });


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
        final TextView tv =  findViewById(R.id.tv);

        tv.setTextColor(Color.parseColor("#EC7469"));

        ArrayAdapter<Club> adapter = new ArrayAdapter<Club>(this, android.R.layout.simple_spinner_item, ClubList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //@Override
        //public void onItemSelected(AdapterView<?> parent, View view, int position, long Club club = (Club) parent.getSelectedItem();

        ArrayAdapter<Sport> adapter2 = new ArrayAdapter<Sport>(this, android.R.layout.simple_spinner_item, SportList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        ArrayAdapter<Person> adapter3 = new ArrayAdapter<Person>(this, android.R.layout.simple_spinner_item, PersonList);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);


        final TextView tv2 =  findViewById(R.id.tv2);
        tv2.setTextColor(Color.parseColor("#EC7469"));
        final TextView tv3 =  findViewById(R.id.tv3);
        tv3.setTextColor(Color.parseColor("#EC7469"));
    }




}