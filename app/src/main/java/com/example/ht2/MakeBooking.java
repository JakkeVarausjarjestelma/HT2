package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class MakeBooking extends AppCompatActivity {
    DatePicker datePicker;
    TimePicker timePicker1;
    TimePicker timePicker2;
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
                int starttime = timePicker1.getHour()*60 + timePicker1.getMinute();
                int endtime = timePicker2.getHour()*60 + timePicker2.getMinute();


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
                for (int n = 0; n < listRoom.size(); n++) {
                    compare_sportID = ((Room) listRoom.get(n)).getSportID();
                    if (compare_sportID == sportID) {
                        String name = ((Room) listRoom.get(n)).getName();
                        int roomID = ((Room) listRoom.get(n)).getRoomID();
                        listRoomBySport.add(new Room(roomID, sportID, name));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listEquipmentByRoom.clear();
                int  roomID = ((Room) roomSpinner.getSelectedItem()).getRoomID();
                System.out.println("SaliID on: " + roomID);
                int compare_roomID;
                for (int n = 0; n < listEquipment.size(); n++) {
                    compare_roomID = ((Equipment) listEquipment.get(n)).getRoomID();
                    if (compare_roomID == roomID) {
                        String name = ((Equipment) listEquipment.get(n)).getName();
                        int equipmentID = ((Equipment) listEquipment.get(n)).getEquipmentID();
                        listEquipmentByRoom.add(new Equipment(equipmentID, roomID, name));
                    }
                }

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

    public void makeBooking(){

    }

    public int datepickerToInt() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        int daysFromMillennium = 365*(year-2000) + 31*(month-1) + day-1;
        return daysFromMillennium;
    }


}