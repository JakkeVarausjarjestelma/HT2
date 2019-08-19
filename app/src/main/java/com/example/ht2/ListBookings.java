package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;


public class ListBookings extends AppCompatActivity {
    Button button8;
    private Button button6;
    private Button button10;
    private Spinner spinner;
    private Spinner spinner2;
    private Spinner spinner3;

    public ArrayList<Club> listClub;
    public ArrayList<Booker> listBooker;
    public ArrayList<Sport> listSport;
    public ArrayList<Room> listRoom;
    public ArrayList<Equipment> listEquipment;
    public ArrayList<Booking> listBooking;
    public ArrayList<Person> listPerson;
    private SQLiteDatabase db;
    TextView myTextView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            DataBaseHelper helper = new DataBaseHelper(this);
            db = helper.getWritableDatabase();
            System.out.println(db.rawQuery("SELECT * FROM Seura;", null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_list_bookings);

        button6 = findViewById(R.id.button6);
        button8 = findViewById(R.id.button8);
        button10 = findViewById(R.id.button10);


        spinner = findViewById(R.id.equipmentSpinner);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);

        Intent intent = getIntent();
        listClub = intent.getParcelableArrayListExtra("Club");
        listSport = intent.getParcelableArrayListExtra("Sport");
        listClub = intent.getParcelableArrayListExtra("Club");
        listRoom = intent.getParcelableArrayListExtra("Room");
        listPerson = intent.getParcelableArrayListExtra("Person");
        listEquipment = intent.getParcelableArrayListExtra("Equipment");
        listBooker = intent.getParcelableArrayListExtra("Booker");
        listBooking = intent.getParcelableArrayListExtra("Booking");



        button6.setOnClickListener(new OnClickListener() {
            //Search by Club
            @Override
            public void onClick(View view) {
                int clubID = ((Club) spinner.getSelectedItem()).getClubID();
                searchByClub(clubID);
            }
        });

        button8.setOnClickListener(new OnClickListener() {

            //Search by Sport

            @Override
            public void onClick(View view) {
                int sportID = ((Sport) spinner2.getSelectedItem()).getSportID();
                searchBySport(sportID);


            }
        });

        button10.setOnClickListener(new OnClickListener() {


            @Override
            //Search by Person
            public void onClick(View view) {
                int bookerID = ((Person) spinner3.getSelectedItem()).getBookerID();
                searchByPerson(bookerID);

            }
        });

        myTextView = (TextView)findViewById(R.id.textView5);
        myTextView.setText("");

        TextView tv =  findViewById(R.id.tv);
        tv.setTextColor(Color.parseColor("#EC7469"));

        ArrayAdapter<Club> adapter = new ArrayAdapter<Club>(this, android.R.layout.simple_spinner_item, listClub);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        ArrayAdapter<Sport> adapter2 = new ArrayAdapter<Sport>(this, android.R.layout.simple_spinner_item, listSport);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);


        ArrayAdapter<Person> adapter3 = new ArrayAdapter<Person>(this, android.R.layout.simple_spinner_item, listPerson);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);


        final TextView tv2 =  findViewById(R.id.tv2);
        tv2.setTextColor(Color.parseColor("#EC7469"));
        final TextView tv3 =  findViewById(R.id.tv3);
        tv3.setTextColor(Color.parseColor("#EC7469"));
    }

    private void searchByPerson(int personid) {
        String s = "SELECT Varaus.Päivämäärä, Varaus.Aloitusaika, Varaus.Lopetusaika, Sali.Nimi  FROM Varaus INNER JOIN Varaaja ON Varaus.VaraajaID = Varaaja.VaraajaID INNER JOIN Sali ON Varaus.SaliID = Sali.SaliID  WHERE Varaaja.VaraajaID = "+personid+";";
        System.out.println(s);

        Cursor c = db.rawQuery(s, null);
        String v =  "Päivämäärä:      " +  "Aloitusaika:        " + "Lopetusaika:       " + "Sali:       " + "\n";

        if (c.moveToFirst()) {
            do {
                System.out.println("asdfegea");
                v = v   + c.getString(0) + "            "  + c.getString(1) +"                  " + c.getString(2) + "                   " + c.getString(3) + "\n";

            } while (c.moveToNext());


        } else { Toast.makeText(ListBookings.this, "Ei varauksia tällä haulla!", Toast.LENGTH_SHORT).show();}

        myTextView.setText(v);

        c.close();


    }


    private void searchBySport(int sportid) {

        String s = "SELECT Varaus.Päivämäärä, Varaus.Aloitusaika, Varaus.Lopetusaika, Sali.Nimi  FROM Varaus INNER JOIN Sali ON Varaus.SaliID = Sali.SaliID INNER JOIN Lajivalinta ON Sali.LajiID = Lajivalinta.LajiID  WHERE Lajivalinta.LajiID = "+sportid+";";
        System.out.println(s);


        Cursor c = db.rawQuery(s, null);
        String v =  "Päivämäärä:      " +  "Aloitusaika:        " + "Lopetusaika:       " + "Sali:       " + "\n";

        if (c.moveToFirst()) {
            do {
                v = v   + c.getString(0) + "            "  + c.getString(1) +"                  " + c.getString(2) + "                   " + c.getString(3) + "\n";

            } while (c.moveToNext());


        } else { Toast.makeText(ListBookings.this, "Ei varauksia tällä haulla!", Toast.LENGTH_SHORT).show();}

        myTextView.setText(v);

        c.close();




    }


    public void searchByClub(int clubid){
        String s = "SELECT Varaus.Päivämäärä, Varaus.Aloitusaika, Varaus.Lopetusaika, Sali.Nimi  FROM Varaus INNER JOIN Varaaja ON Varaus.VaraajaID = Varaaja.VaraajaID INNER JOIN Seura ON Varaaja.SeuraID = Seura.SeuraID INNER JOIN Sali ON Varaus.SaliID = Sali.SaliID  WHERE Seura.SeuraID = "+clubid+";";
        System.out.println(s);

        Cursor c = db.rawQuery(s, null);
        String v =  "Päivämäärä:      " +  "Aloitusaika:        " + "Lopetusaika:       " + "Sali:       " + "\n";

        if (c.moveToFirst()) {
            do {

                v = v   + c.getString(0) + "            "  + c.getString(1) +"                  " + c.getString(2) + "                   " + c.getString(3) + "\n";

            } while (c.moveToNext());


        } else { Toast.makeText(ListBookings.this, "Ei varauksia tällä haulla!", Toast.LENGTH_SHORT).show();}


        myTextView.setText(v);


        c.close();

    }}