package com.example.ht2;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteBookings {
    public ArrayList<Club> listClub;
    public ArrayList<Booker> listBooker;
    public ArrayList<Sport> listSport;
    public ArrayList<Room> listRoom;
    public ArrayList<Equipment> listEquipment;
    public ArrayList<Booking> listBooking;
    public ArrayList<Person> listPerson;
    private SQLiteDatabase db;
    Context context;

    public WriteBookings(ArrayList<Room> listRoom, SQLiteDatabase sqLiteDatabase) {
        this.listRoom = listRoom;
        this.db = sqLiteDatabase;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("unchecked")
    public void write() throws JSONException {


        int roomid = 0;

        for (int i = 0; i < listRoom.size(); i++) {


            roomid =  listRoom.get(i).getRoomID();

            String s = "SELECT Varaus.Päivämäärä, Varaus.Aloitusaika, Varaus.Lopetusaika, Sali.Nimi  FROM Varaus INNER JOIN Sali ON Varaus.SaliID = Sali.SaliID  WHERE Sali.SaliID = " + roomid + ";";
            Cursor c = db.rawQuery(s, null);
            JSONObject obj = new JSONObject();

            if (c.moveToFirst()) {
                do {

                    obj.put("Päivämäärä", c.getString(0));
                    obj.put("Aloitusaika", c.getString(1));
                    obj.put("Lopetusaika", c.getString(2));
                    obj.put("Sali", c.getString(3));
                } while (c.moveToNext());

                c.close();
            }


            //System.out.println(obj);


            try (FileWriter file = new FileWriter("myJson.json")) {

                file.write(obj.toString());
                file.flush();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
