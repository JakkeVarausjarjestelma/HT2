package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class MakeBooking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_booking);

        final TextView tv = findViewById(R.id.tv);
        NumberPicker np = findViewById(R.id.np);
        tv.setTextColor(Color.parseColor("#F02B6B"));
        np.setMinValue(1);
        np.setMaxValue(11);
        np.setWrapSelectorWheel(true);

        final
        NumberPicker np2 =  findViewById(R.id.np2);
        final String[] values= {"Sulkapallo","Tennis", "Jalkapallo", "Pesäpallo", "Muut paalloilulajit"};
        np2.setMinValue(0);
        np2.setMaxValue(values.length-1);
        np2.setDisplayedValues(values);
        np2.setWrapSelectorWheel(true);

        final
        NumberPicker np3 =  findViewById(R.id.np3);
        final String[] valuess= {"Sali4","Tennisali3", "Sali5", "Tennissali9", "Muut paalloilulajitsalit6"};
        np3.setMinValue(0);
        np3.setMaxValue(valuess.length-1);
        np3.setDisplayedValues(valuess);
        np3.setWrapSelectorWheel(true);


        final
        NumberPicker np4 =  findViewById(R.id.np4);
        final String[] valuesss= {"Palloilulajit","Tennisvälineet", "Jalkapallot", "Pesäpallot", "Muut paalloilulajit"};
        np4.setMinValue(0);
        np4.setMaxValue(valuesss.length-1);
        np4.setDisplayedValues(valuesss);
        np4.setWrapSelectorWheel(true);

        TimePicker timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        TimePicker timePicker2 = findViewById(R.id.timePicker2);
        timePicker2.setIs24HourView(true);







    }


}