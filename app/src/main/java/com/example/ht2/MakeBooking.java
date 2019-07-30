package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MakeBooking extends AppCompatActivity {
    DatePicker datePicker;
    TimePicker timePicker1;
    TimePicker timePicker2;
    NumberPicker numberPicker;
    Button button;
    Spinner sportSpinner;
    Spinner roomSpinner;
    Spinner equipmentSpinner;

    int bookerID;

    ArrayList listSport;
    ArrayList listRoom;
    ArrayList listEquipment;

    ArrayList listRoomBySport;
    ArrayList listEquipmentByRoom;

    ArrayList listBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_booking);

        datePicker = findViewById(R.id.datePicker);
        timePicker1 = findViewById(R.id.timePicker1);
        timePicker2 = findViewById(R.id.timePicker2);
        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);
        numberPicker = findViewById(R.id.numberPicker1);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(12);

        button = findViewById(R.id.button);
        sportSpinner = findViewById(R.id.sportSpinner);
        roomSpinner = findViewById(R.id.roomSpinner);
        equipmentSpinner = findViewById(R.id.equipmentSpinner);

        TextView textView = findViewById(R.id.tv);
        textView.setTextColor(Color.parseColor("#F02B6B"));

        Intent intent = getIntent();
        listSport = intent.getParcelableArrayListExtra("Sport");
        listRoom = intent.getParcelableArrayListExtra("Room");
        listEquipment = intent.getParcelableArrayListExtra("Equipment");
        listBooking = intent.getParcelableArrayListExtra("Booking");
        bookerID = intent.getIntExtra("bookerID", -1);

        listRoomBySport = new ArrayList<Room>();
        listEquipmentByRoom = new ArrayList<Equipment>();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int daysFromMillennium = datepickerToInt();
                int starttimeAsMinutes = timePicker1.getHour()*60 + timePicker1.getMinute();
                int endtimeAsMinutes = timePicker2.getHour()*60 + timePicker2.getMinute();
                int times = numberPicker.getValue();
                int sportID = ((Sport) sportSpinner.getSelectedItem()).getSportID();
                int roomID = ((Room) roomSpinner.getSelectedItem()).getRoomID();

                String starttimeAsString = String.valueOf(timePicker1.getHour()) + String.valueOf(timePicker1.getMinute());
                String endtimeAsString = String.valueOf(timePicker2.getHour()) + String.valueOf(timePicker2.getMinute());




                int id_max = 0;
                for (int i = 0; i < listBooking.size(); i++){
                    int id = ((Booking) listBooking.get(i)).getBookingID();
                    if (id >= id_max) {
                        id_max = id;
                    }
                }
                int bookingID = id_max +1;

                boolean ok = checkTimeAvailable(daysFromMillennium, starttimeAsMinutes, endtimeAsMinutes);
                if (ok) {
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth();
                    int day = datePicker.getDayOfMonth();
                    makeBooking(year, month, day, starttimeAsString, endtimeAsString, times, sportID, roomID, bookingID);

                } else {
                    Toast.makeText(MakeBooking.this, "Päällekkäisiä varauksia", Toast.LENGTH_LONG).show();


                }


            }
        });

        ArrayAdapter<Sport> adapterSport = new ArrayAdapter<Sport>(this, android.R.layout.simple_spinner_item, listSport);
        adapterSport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportSpinner.setAdapter(adapterSport);

        ArrayAdapter<Room> adapterRoom = new ArrayAdapter<Room>(this, android.R.layout.simple_spinner_item, listRoomBySport);
        adapterRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapterRoom);

        ArrayAdapter<Equipment> adapterEquipment = new ArrayAdapter<Equipment>(this, android.R.layout.simple_spinner_item, listEquipmentByRoom);
        adapterEquipment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentSpinner.setAdapter(adapterEquipment);


        sportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listRoomBySport.clear();
                int sportID = ((Sport) sportSpinner.getSelectedItem()).getSportID();
                int compare_sportID;
                System.out.println("sportID on:" + sportID);
                for (int n = 0; n < listRoom.size(); n++) {
                    compare_sportID = ((Room) listRoom.get(n)).getSportID();
                    if (compare_sportID == sportID) {
                        String name = ((Room) listRoom.get(n)).getName();
                        int roomID = ((Room) listRoom.get(n)).getRoomID();
                        listRoomBySport.add(new Room(roomID, sportID, name));

                    }
                }
                ((ArrayAdapter)roomSpinner.getAdapter()).notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("SaliID on: ");
                listEquipmentByRoom.clear();
                int  roomID = ((Room) roomSpinner.getSelectedItem()).getRoomID();

                int compare_roomID;
                for (int n = 0; n < listEquipment.size(); n++) {
                    compare_roomID = ((Equipment) listEquipment.get(n)).getRoomID();
                    if (compare_roomID == roomID) {
                        String name = ((Equipment) listEquipment.get(n)).getName();
                        int equipmentID = ((Equipment) listEquipment.get(n)).getEquipmentID();
                        listEquipmentByRoom.add(new Equipment(equipmentID, roomID, name));
                    }
                }
                ((ArrayAdapter)equipmentSpinner.getAdapter()).notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                timePicker2.setHour(timePicker1.getHour()+1);
                timePicker2.setMinute(timePicker1.getMinute());
            }
        });

        timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                if (timePicker2.getHour() < timePicker1.getHour() || timePicker2.getHour() == timePicker1.getHour() && timePicker2.getMinute() <= timePicker1.getMinute()){
                    Toast.makeText(MakeBooking.this, "Lopetusajan täytyy olla aloitusajan jälkeen!", Toast.LENGTH_SHORT).show();
                    button.setEnabled(false);
                } else {button.setEnabled(true);}
            }
        });


    }

    public void makeBooking(int year, int month, int day, String starttime, String endtime, int times, int sportID, int roomID, int bookingID){
        String twoDigitMonth;
        if (month < 10) {
            twoDigitMonth = "0" + String.valueOf(month+1);
        }
        else {
            twoDigitMonth = String.valueOf(month);
        }
        String twoDigitDay;
        if (day < 10) {
            twoDigitDay = "0" + String.valueOf(day);
        }
        else {
            twoDigitDay = String.valueOf(day);
        }

        String date = twoDigitDay + twoDigitMonth + String.valueOf(year);

        try {
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(BookingSystemContract.BookingEntry.COLUMN_BOOKINGID, bookingID);
            cv.put(BookingSystemContract.BookingEntry.COLUMN_BOOKERID, bookerID);
            cv.put(BookingSystemContract.BookingEntry.COLUMN_ROOMID, roomID);
            cv.put(BookingSystemContract.BookingEntry.COLUMN_STARTTIME, starttime);
            cv.put(BookingSystemContract.BookingEntry.COLUMN_ENDTIME, endtime);
            cv.put(BookingSystemContract.BookingEntry.COLUMN_DATE, date);
            sqLiteDatabase.insert(BookingSystemContract.BookingEntry.TABLE_NAME, null, cv);
            listBooking.add(new Booking(bookingID, bookerID, roomID, starttime, endtime, date));
            System.out.println("PVM " + date + "\nAlkuaika " + starttime + "\nLoppuaika " + endtime);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean checkTimeAvailable(int daysFromMillennium1, int starttime1FromMidnight, int endtime1FromMidnight){

        for (int i = 0; i < listBooking.size(); i++) {
            String date = ((Booking) listBooking.get(i)).getDate();
            char tens = date.charAt(0);
            char ones = date.charAt(1);
            int day = Integer.parseInt(String.valueOf(tens + ones));
            tens = date.charAt(2);
            ones = date.charAt(3);
            int month = Integer.parseInt(String.valueOf(tens + ones));
            char thousands = date.charAt(4);
            char hundreds = date.charAt(5);
            tens = date.charAt(6);
            ones = date.charAt(7);
            int year = Integer.parseInt(String.valueOf(thousands + hundreds + tens + ones));
            int daysFromMillennium2 = dateToInt(year, month, day);
            if (daysFromMillennium1 == daysFromMillennium2) {
                String starttime2AsString = ((Booking) listBooking.get(i)).getStartTime();
                tens = starttime2AsString.charAt(0);
                ones = starttime2AsString.charAt(1);
                int hour = Integer.parseInt(String.valueOf(tens + ones));
                tens = starttime2AsString.charAt(2);
                ones = starttime2AsString.charAt(3);
                int minute = Integer.parseInt(String.valueOf(tens + ones));
                int starttime2FromMidnight = 60 * hour + minute;

                String endtime2AsString = ((Booking) listBooking.get(i)).getEndTime();
                tens = endtime2AsString.charAt(0);
                ones = endtime2AsString.charAt(1);
                hour = Integer.parseInt(String.valueOf(tens + ones));
                tens = endtime2AsString.charAt(2);
                ones = endtime2AsString.charAt(3);
                minute = Integer.parseInt(String.valueOf(tens + ones));
                int endtime2FromMidnight = 60 * hour + minute;

                if ((starttime1FromMidnight < starttime2FromMidnight) && (endtime1FromMidnight > endtime2FromMidnight)) {
                    return false;
                } else if ((starttime2FromMidnight < starttime1FromMidnight) && (endtime2FromMidnight > endtime1FromMidnight)) {
                    return false;
                } else if ((starttime1FromMidnight < starttime2FromMidnight) && (endtime1FromMidnight > starttime2FromMidnight)) {
                    return false;
                } else if ((starttime2FromMidnight < starttime1FromMidnight) && (endtime2FromMidnight > starttime1FromMidnight)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int datepickerToInt() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        int daysFromMillennium = 365*(year-2000) + 31*(month-1) + day-1;
        return daysFromMillennium;
    }

    public int dateToInt(int year, int month, int day) {
        int daysFromMillennium = 365*(year-2000) + 31*(month-1) + day-1;
        return daysFromMillennium;
    }


}