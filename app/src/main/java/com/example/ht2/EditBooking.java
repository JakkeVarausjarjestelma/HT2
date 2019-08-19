package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditBooking extends AppCompatActivity {

    public ArrayList listBooking;
    public  ArrayList listBookingByBooker;

    ArrayList listSport;
    ArrayList listRoom;
    ArrayList listEquipment;

    Spinner spinner;
    private SQLiteDatabase db;

    Button editButton;
    Button deleteButton;

    int bookerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_booking);
        Intent intent = getIntent();
        listSport = intent.getParcelableArrayListExtra("Sport");
        listRoom = intent.getParcelableArrayListExtra("Room");
        listEquipment = intent.getParcelableArrayListExtra("Equipment");
        listBooking = intent.getParcelableArrayListExtra("Booking");
        bookerID = intent.getIntExtra("bookerID", -2);
        System.out.println("BookerID  editbookingissa on : " + bookerID);

        listBookingByBooker = getListBookingByBooker();


        try {
            DataBaseHelper helper = new DataBaseHelper(this);
            db = helper.getWritableDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteButton = findViewById(R.id.button11);
        editButton = findViewById(R.id.button12);


        spinner = (Spinner) findViewById(R.id.spinner);

        listOwnBookings();



        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listBookingByBooker.size() > 0) {
                    int n = ((Integer) spinner.getSelectedItem()).intValue();
                    int bookingID = ((Booking) listBookingByBooker.get(n-1)).getBookingID();
                    db.delete(BookingSystemContract.BookingEntry.TABLE_NAME, BookingSystemContract.BookingEntry.COLUMN_BOOKINGID + "=" + bookingID + " AND + " + BookingSystemContract.BookingEntry.COLUMN_BOOKERID + " = " + bookerID, null);
                    listBookingByBooker.remove(n - 1);
                    Toast.makeText(EditBooking.this, "Varaus poistettu onnistuneesti", Toast.LENGTH_LONG).show();

                    listOwnBookings();
                } else {
                    Toast.makeText(EditBooking.this, "Sinulla ei ole yhtään varausta", Toast.LENGTH_LONG).show();
                }


            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listBookingByBooker.size() > 0) {
                    openMakeBooking();
                } else {
                    Toast.makeText(EditBooking.this, "Sinulla ei ole yhtään varausta", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    public void listOwnBookings(){
        List<Integer> numberlist = new ArrayList<>();
        String s = "SELECT Varaus.Päivämäärä, Varaus.Aloitusaika, Varaus.Lopetusaika, Sali.Nimi FROM Varaus INNER JOIN Varaaja ON Varaus.VaraajaID = Varaaja.VaraajaID INNER JOIN Sali ON Varaus.SaliID = Sali.SaliID  WHERE Varaaja.VaraajaID = "+bookerID+";";
        Cursor c = db.rawQuery(s, null);
        String v = "Järjestysnumero:" + "\t" +"PVM:"+ "\t" +  "Aloitusaika:"+ "\t" + "Lopetusaika:"+ "\t" + "Sali:\n";
        int i = 0;

        if (c.moveToFirst()) {
            do {
                i++;
                numberlist.add(i);
                v = v +i +"."+ "\t"  + c.getString(0) + ""+ "\t"  + c.getString(1) +""+ "\t" + c.getString(2) + ""+ "\t" + c.getString(3) + "\n";
            } while (c.moveToNext());
        }

        TextView myTextView = (TextView)findViewById(R.id.textView2);
        myTextView.setText(v);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, numberlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        c.close();
    }

    public void openMakeBooking(){
        int index = ((Integer) spinner.getSelectedItem()).intValue() - 1;
        Class<?> cls = MakeBooking.class;
        Intent intent = new Intent(this, cls);
        intent.putExtra("index", index);
        intent.putExtra("bookerID", bookerID);
        intent.putParcelableArrayListExtra("Sport", listSport);
        intent.putParcelableArrayListExtra("Room", listRoom);
        intent.putParcelableArrayListExtra("Equipment", listEquipment);
        intent.putParcelableArrayListExtra("Booking", listBooking);
        intent.putParcelableArrayListExtra("BookingByBooker", listBookingByBooker);

        startActivityForResult(intent, 998);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCursorToListBooking(loadTableToCursor(BookingSystemContract.BookingEntry.TABLE_NAME));
        listOwnBookings();
        listBookingByBooker = getListBookingByBooker();
    }

    public ArrayList<Booking> getListBookingByBooker(){
        listBookingByBooker = new ArrayList<Booking>();
        int compare_bookerid;
        for (int n = 0; n < listBooking.size(); n++) {
            compare_bookerid = ((Booking) listBooking.get(n)).getBookerID();
            if (compare_bookerid == bookerID) {

                int bookingID = ((Booking) listBooking.get(n)).getBookingID();
                int roomID = ((Booking) listBooking.get(n)).getRoomID();
                Date starttime = ((Booking) listBooking.get(n)).getStartTime();
                Date endtime = ((Booking) listBooking.get(n)).getEndTime();
                listBookingByBooker.add(new Booking(bookingID, bookerID, roomID, starttime, endtime));
            }
        }
        return listBookingByBooker;
    }

    public void loadCursorToListBooking(Cursor cursor){
        if (cursor.moveToFirst()) {
            listBooking = new ArrayList();

            do {int bookingID = cursor.getInt(0);
                int bookerID = cursor.getInt(1);
                int roomID = cursor.getInt(2);
                String startTimeString = cursor.getString(3);
                String endTimeString = cursor.getString(4);
                String dateString = cursor.getString(5);
                String start = dateString+ "T" + startTimeString + "Z";
                String end = dateString + "T" + endTimeString + "Z";

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm'Z'");
                try {
                    java.util.Date dateStart =  format.parse(start);
                    java.util.Date dateEnd = format.parse(end);
                    listBooking.add(new Booking(bookingID, bookerID, roomID, dateStart, dateEnd));
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());

        }
    }
    public Cursor loadTableToCursor(String tableName) {
        DataBaseHelper myDBHelper = null;
        try {
            myDBHelper = new DataBaseHelper(EditBooking.this);
            //myDBHelper.createdatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //myDBHelper.opendatabase();
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        return cursor;
    }

}
