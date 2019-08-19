package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    int index;
    int bookingidToEdit;

    ArrayList listSport;
    ArrayList listRoom;
    ArrayList listEquipment;

    ArrayList listRoomBySport;
    ArrayList listEquipmentByRoom;

    ArrayList listBooking;
    ArrayList listBookingByBooker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_booking);

        datePicker = findViewById(R.id.datePicker);
        timePicker1 = findViewById(R.id.timePicker1);
        timePicker2 = findViewById(R.id.timePicker2);
        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);
        timePicker2.setHour(timePicker1.getHour() + 1);
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
        index = intent.getIntExtra("index", -1);
        if (index != -1) {
            listBookingByBooker = intent.getParcelableArrayListExtra("BookingByBooker");

        }
        bookingidToEdit  = 0;

        listRoomBySport = new ArrayList<Room>();
        listEquipmentByRoom = new ArrayList<Equipment>();

        ArrayAdapter<Sport> adapterSport = new ArrayAdapter<Sport>(this, android.R.layout.simple_spinner_item, listSport);
        adapterSport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportSpinner.setAdapter(adapterSport);

        ArrayAdapter<Room> adapterRoom = new ArrayAdapter<Room>(this, android.R.layout.simple_spinner_item, listRoomBySport);
        adapterRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapterRoom);

        ArrayAdapter<Equipment> adapterEquipment = new ArrayAdapter<Equipment>(this, android.R.layout.simple_spinner_item, listEquipmentByRoom);
        adapterEquipment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentSpinner.setAdapter(adapterEquipment);

        if (index != -1) { // EditBooking
            setBookingValuesToSpinners();
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int times = numberPicker.getValue();

                int sportID = ((Sport) sportSpinner.getSelectedItem()).getSportID();
                int roomID = ((Room) roomSpinner.getSelectedItem()).getRoomID();

                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();

                int hour1 = timePicker1.getHour();
                int minute1 = timePicker1.getMinute();

                int hour2 = timePicker2.getHour();
                int minute2 = timePicker2.getMinute();

                Calendar calendar1 = new GregorianCalendar(year, month, dayOfMonth, hour1, minute1);
                long time1 = calendar1.getTimeInMillis();

                Calendar calendar2 = new GregorianCalendar(year, month, dayOfMonth, hour2, minute2);
                long time2 = calendar2.getTimeInMillis();

                int counterGood = 0;
                int counterBad = 0;

                for (int n = 0; n < times; n++) {
                    if (n > 0) {
                        time1 = time1 + 1000*3600*24*7;  // adds one week in milliseconds
                        time2 = time2 + 1000*3600*24*7;
                    }

                    // finds free bookingID for new booking
                        int id_max = 0;
                        for (int i = 0; i < listBooking.size(); i++) {
                            int id = ((Booking) listBooking.get(i)).getBookingID();
                            if (id >= id_max) {
                                id_max = id;
                            }
                        }
                        int bookingID = id_max + 1;



                    boolean ok;
                    ok = checkTimeAvailable(time1, time2, roomID);

                    if (ok) {
                        counterGood = counterGood + 1;
                        try {
                            makeBooking(time1, time2, sportID, roomID, bookingID, index, bookingidToEdit);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        counterBad = counterBad + 1;

                    }


                }
                if (index == -1) {
                    Toast.makeText(MakeBooking.this, "Varauksen teko onnistui " + counterGood + " kertaa.\nVaraus epäonnistui päällekkäisyyden takia " + counterBad + " kertaa.", Toast.LENGTH_LONG).show();
                } else {
                    if (counterGood == 1) {
                        Toast.makeText(MakeBooking.this, "Varauksen muutos onnistui", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MakeBooking.this, "Varauksen muutos epäonnistui päällekkäisyyden vuoksi", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });




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
                ((ArrayAdapter) roomSpinner.getAdapter()).notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listEquipmentByRoom.clear();
                int roomID = ((Room) roomSpinner.getSelectedItem()).getRoomID();

                int compare_roomID;
                for (int n = 0; n < listEquipment.size(); n++) {
                    compare_roomID = ((Equipment) listEquipment.get(n)).getRoomID();
                    if (compare_roomID == roomID) {
                        String name = ((Equipment) listEquipment.get(n)).getName();
                        int equipmentID = ((Equipment) listEquipment.get(n)).getEquipmentID();
                        listEquipmentByRoom.add(new Equipment(equipmentID, roomID, name));
                    }
                }
                ((ArrayAdapter) equipmentSpinner.getAdapter()).notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                timePicker2.setHour(timePicker1.getHour() + 1);
                timePicker2.setMinute(timePicker1.getMinute());
            }
        });

        timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                if (timePicker2.getHour() < timePicker1.getHour() || timePicker2.getHour() == timePicker1.getHour() && timePicker2.getMinute() <= timePicker1.getMinute()) {
                    Toast.makeText(MakeBooking.this, "Lopetusajan täytyy olla aloitusajan jälkeen!", Toast.LENGTH_SHORT).show();
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }
            }
        });


    }

    public void makeBooking(long starttime, long endtime, int sportID, int roomID, int bookingID, int index, int bookingidToEdit) throws IOException {
        Calendar date1 = Calendar.getInstance();
        date1.setTimeInMillis(starttime);
        Calendar date2 = Calendar.getInstance();
        date2.setTimeInMillis(endtime);



        android.icu.text.SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy'");
        String date = format.format(starttime);
        String strTime1;
        String strTime2;
        String strHours;
        String strMinutes;
        int hours;
        int minutes;
        hours = date1.get(Calendar.HOUR_OF_DAY);
        minutes = date1.get(Calendar.HOUR_OF_DAY);
        if (hours < 10){
            strHours = "0" + hours;
        } else {
            strHours = String.valueOf(hours);
        }
        if (minutes < 10){
            strMinutes = "0" + minutes;
        } else {
            strMinutes = String.valueOf(minutes);
        }
        strTime1 = strHours + ":" + strMinutes;

        hours = date2.get(Calendar.HOUR_OF_DAY);
        minutes = date2.get(Calendar.HOUR_OF_DAY);
        if (hours < 10){
            strHours = "0" + hours;
        } else {
            strHours = String.valueOf(hours);
        }
        if (minutes < 10){
            strMinutes = "0" + minutes;
        } else {
            strMinutes = String.valueOf(minutes);
        }
        strTime2 = strHours + ":" + strMinutes;


        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BookingSystemContract.BookingEntry.COLUMN_BOOKERID, bookerID);
        cv.put(BookingSystemContract.BookingEntry.COLUMN_ROOMID, roomID);
        cv.put(BookingSystemContract.BookingEntry.COLUMN_STARTTIME, strTime1);
        cv.put(BookingSystemContract.BookingEntry.COLUMN_ENDTIME, strTime2);
        cv.put(BookingSystemContract.BookingEntry.COLUMN_DATE, date);


        if (index == -1) {
            cv.put(BookingSystemContract.BookingEntry.COLUMN_BOOKINGID, bookingID);
            sqLiteDatabase.insert(BookingSystemContract.BookingEntry.TABLE_NAME, null, cv);

            Date d1 = new Date(starttime);
            Date d2 = new Date(endtime);
            listBooking.add(new Booking(bookingID, bookerID, roomID, d1, d2));
        } else {
            String s = BookingSystemContract.BookingEntry.COLUMN_BOOKINGID + " = " + bookingidToEdit;
            sqLiteDatabase.update(BookingSystemContract.BookingEntry.TABLE_NAME, cv, s, null);
        }

    }


    public boolean checkTimeAvailable(long startTime1, long endTime1, int roomID1){
        long startTime2;
        long endTime2;
        int roomID2;


        for (int i = 0; i < listBooking.size(); i++) {
            Date startTimeAsDate = ((Booking) listBooking.get(i)).getStartTime();
            Date endTimeAsDate = ((Booking) listBooking.get(i)).getEndTime();
            startTime2 = startTimeAsDate.getTime();
            endTime2 = endTimeAsDate.getTime();
            roomID2 = ((Booking) listBooking.get(i)).getRoomID();

            if (index != -1 && ((Booking) listBooking.get(i)).getBookingID()  == bookingidToEdit) {
            } else {
                if (roomID1 == roomID2) {
                    if (startTime1 >= startTime2 && startTime1 < endTime2) {
                        return false;
                    } else if (endTime1 > startTime2 && endTime1 <= endTime2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void setBookingValuesToSpinners(){
            Date startTimeToEdit = ((Booking) listBookingByBooker.get(index)).getStartTime();
            Date endTimeToEdit = ((Booking) listBookingByBooker.get(index)).getEndTime();
            bookingidToEdit  = ((Booking) listBookingByBooker.get(index)).getBookingID();

            int roomidToEdit = ((Booking) listBookingByBooker.get(index)).getRoomID();
            int sportidtoEdit = -1;
            for (int x = 0; x < listRoom.size(); x++){

                int compareRoomid = ((Room) listRoom.get(x)).getRoomID();
                if (compareRoomid == roomidToEdit) {
                    sportidtoEdit = ((Room) listRoom.get(x)).getSportID();
                    break;
                }
            }
            sportSpinner.setSelection(sportidtoEdit);
            System.out.println("postitio nyt on " + sportSpinner.getSelectedItemPosition() );


            Calendar calendar1 = new GregorianCalendar();
            calendar1.setTime(startTimeToEdit);
            Calendar calendar2 = new GregorianCalendar();
            calendar2.setTime(endTimeToEdit);
            datePicker.updateDate(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));
            timePicker1.setHour(calendar1.get(Calendar.HOUR_OF_DAY));
            timePicker1.setMinute(calendar1.get(Calendar.MINUTE));
            timePicker2.setHour(calendar2.get(Calendar.HOUR_OF_DAY));
            timePicker2.setMinute(calendar2.get(Calendar.MINUTE));


            int compare_sportID;
            for (int n = 0; n < listRoom.size(); n++) {
                compare_sportID = ((Room) listRoom.get(n)).getSportID();
                if (compare_sportID == sportidtoEdit) {
                    String name = ((Room) listRoom.get(n)).getName();
                    int roomID = ((Room) listRoom.get(n)).getRoomID();
                    listRoomBySport.add(new Room(roomID, sportidtoEdit, name));
                }
            }
            ((ArrayAdapter) roomSpinner.getAdapter()).notifyDataSetChanged();
            int compare_roomID;
            for (int n = 0; n < listEquipment.size(); n++) {
                compare_roomID = ((Equipment) listEquipment.get(n)).getRoomID();
                if (compare_roomID == roomidToEdit) {
                    String name = ((Equipment) listEquipment.get(n)).getName();
                    int equipmentID = ((Equipment) listEquipment.get(n)).getEquipmentID();
                    listEquipmentByRoom.add(new Equipment(equipmentID, roomidToEdit, name));
                }
            }
            ((ArrayAdapter) equipmentSpinner.getAdapter()).notifyDataSetChanged();

            button.setText("Muokkaa varausta");
            numberPicker.setMaxValue(1);


        }

    }




