package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditBooking extends AppCompatActivity {
    ArrayList listClub;
    public ArrayList listRoom;
    public ArrayList listBooking;
    EditText nametext;
    EditText phonenumbertext;
    Button button11;
    Spinner spinner;
    TextView textView;
    private SQLiteDatabase db;
    private Button button;

    private boolean userExists;
    int bookerID;
    private ArrayList<Room> listBookingByBooker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_booking);
        Intent intent = getIntent();
        try {
            DataBaseHelper helper = new DataBaseHelper(this);
            db = helper.getWritableDatabase();
            System.out.println(db.rawQuery("SELECT * FROM Seura;", null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        button11 = findViewById(R.id.button11);
        spinner = (Spinner) findViewById(R.id.spinner);
        List<Integer> numberlist = new ArrayList<>();



        ArrayList<Parcelable> listBooking = intent.getParcelableArrayListExtra("Booking");
        ArrayList<Parcelable> listRoom = intent.getParcelableArrayListExtra("Room");
        boolean userExists = intent.getBooleanExtra("userExists", false);
        if (userExists) {

            bookerID = intent.getIntExtra("bookerID", -2);


        }
        String s = "SELECT Varaus.Päivämäärä, Varaus.Aloitusaika, Varaus.Lopetusaika, Sali.Nimi  FROM Varaus INNER JOIN Varaaja ON Varaus.VaraajaID = Varaaja.VaraajaID INNER JOIN Sali ON Varaus.SaliID = Sali.SaliID  WHERE Varaaja.VaraajaID = "+bookerID+";";
        System.out.println(s);
        // String s = "SELECT * FROM Varaus WHERE VaraajaID  = ?";

        Cursor c = db.rawQuery(s, null);
        String v = "Järjestysnumero:" + "\t" +"PVM:"+ "\t" +  "Aloitusaika:"+ "\t" + "Lopetusaika:"+ "\t" + "Sali:" + "\n";
        int i = 0;

        if (c.moveToFirst()) {
            do {


                i++;
                numberlist.add(i);
                System.out.println("asdfegea");
                //Toast.makeText(ListBookings.this, "id: " + c.getString(0) + "\n" + "Nimi: " + c.getString(1), Toast.LENGTH_SHORT).show();
                v = v +i +"."+ "\t"  + c.getString(0) + ""+ "\t"  + c.getString(1) +""+ "\t" + c.getString(2) + ""+ "\t" + c.getString(3) + "\n";

            } while (c.moveToNext());


        } else { Toast.makeText(EditBooking.this, "Ei varauksia tällä haulla!", Toast.LENGTH_SHORT).show();}

        TextView myTextView = (TextView)findViewById(R.id.textView2);
        myTextView.setText(v);


        c.close();


        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, numberlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //button11.setOnClickListener();





        //listBookingByBooker = new ArrayList<Room>();

        // for(int i=0; i < listBooking.size(); i++) {

        ///listBookingByBooker.clear();
        //int compare_BookerID;


        //for (int n = 0; n < listRoom.size(); n++) {
        //compare_BookerID = ((Room) listRoom.get(n)).getSportID();z
        //if (compare_BookerID == bookerID) {

        //int bookingID = ((Booking) listBooking.get(n)).getBookerID();
        //listBookingByBooker.add(new Booking(bookingID, bookerID));
    }



}



//}


// }
