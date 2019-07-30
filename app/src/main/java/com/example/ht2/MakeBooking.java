package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MakeBooking extends AppCompatActivity {
    DatePicker datePicker;
    TimePicker timePicker1;
    TimePicker timePicker2;

    int bookerID;

    private ArrayList<Sport> listSport;
    private ArrayList<Room> listRoom;
    private ArrayList<Equipment> listEquipment;
    private ArrayList<Booking> listBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_booking);


        datePicker = findViewById(R.id.datePicker);
        timePicker1 = findViewById(R.id.timePicker1);
        timePicker2 = findViewById(R.id.timePicker2);

        TextView textView= findViewById(R.id.tv);
        textView.setTextColor(Color.parseColor("#F02B6B"));


        TimePicker timePicker = findViewById(R.id.timePicker1);
        timePicker.setIs24HourView(true);

        TimePicker timePicker2 = findViewById(R.id.timePicker2);
        timePicker2.setIs24HourView(true);

        Intent intent = getIntent();
        listSport = intent.getParcelableArrayListExtra("Sport");
        listRoom = intent.getParcelableArrayListExtra("Room");
        listEquipment = intent.getParcelableArrayListExtra("Equipment");
        listBooking = intent.getParcelableArrayListExtra("Booking");
        bookerID = intent.getIntExtra("bookerID", -1);










    }

    public void makeBooking(){

    }


}