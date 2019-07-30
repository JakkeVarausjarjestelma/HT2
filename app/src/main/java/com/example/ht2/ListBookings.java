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
    DataBaseHelper dataBaseHelper;
    Context context = null;
    Button button;
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


        button = findViewById(R.id.button7);
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





      /*testButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println(listClub.size());
            }
        });
       */


        button.setOnClickListener(new OnClickListener() {
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


        ArrayAdapter<Club> adapter = new ArrayAdapter<Club>(this, android.R.layout.simple_spinner_item, listClub);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //@Override
        //public void onItemSelected(AdapterView<?> parent, View view, int position, long Club club = (Club) parent.getSelectedItem();

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

        String s = "SELECT Varaus.Päivämäärä, Varaus.Aloitusaika  FROM Varaus INNER JOIN Varaaja ON Varaus.VaraajaID = Varaaja.VaraajaID WHERE Varaaja.VaraajaID = "+personid+";";
        System.out.println(s);
        // String s = "SELECT * FROM Varaus WHERE VaraajaID  = ?";

        Cursor c = db.rawQuery(s, null);

        if (c.moveToFirst()) {
            do {
                System.out.println("asdfegea");
                Toast.makeText(ListBookings.this, "id: " + c.getString(0) + "\n" + "Nimi: " + c.getString(1), Toast.LENGTH_SHORT).show();

            } while (c.moveToNext());


        } else { Toast.makeText(ListBookings.this, "Ei varauksia tällä haulla!", Toast.LENGTH_SHORT).show();}

        //TextView textView = findViewById(R.id.textView5);

        //textView.setText((CharSequence) c);
        c.close();


    }


    private void searchBySport(int sportid) {
        String s = "SELECT * FROM Varaus WHERE LajiID  = ?";
        Cursor c = db.rawQuery(s, new String[] {String.valueOf(sportid)});

        TextView textView = findViewById(R.id.textView5);

        textView.setText((CharSequence) c);





    }


    public void searchByClub(int clubid){
        TextView textView = findViewById(R.id.textView5);

        String s = "SELECT * FROM Varaus WHERE 'SeuraID'  = " +String.valueOf(clubid);
        s = "SELECT Aloitusaika, Sali.Nimi FROM Varaus INNER JOIN Sali ON Sali.SaliID = Varaus.SaliID;";
        Cursor c = db.rawQuery(s, new String[] {});

        //String query="SELECT * FROM Varaus WHERE 'SeuraID'  = ?";
        //String[] selectionArgs = {"'0'" };
        //c = db.query("Varaus", new String[] {BookingSystemContract.BookingEntry.}, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                Toast.makeText(ListBookings.this, "id: " + c.getString(0), Toast.LENGTH_LONG).show();

            } while (c.moveToNext());



        }
        //c = db.rawQuery(query, selectionArgs);
        if (c.moveToFirst() == false) {
            textView.setText("Haku tehty");
        }









    }


}
