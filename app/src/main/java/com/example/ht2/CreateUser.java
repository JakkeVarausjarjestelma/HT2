package com.example.ht2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

public class CreateUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);


        final TextView tv =  findViewById(R.id.tv);
        NumberPicker np =  findViewById(R.id.np);


        //tv.setTextColor(Color.parseColor("##EC7469"));


        final String[] values= {"Nuuksion pallloiijat","Greenstep", "Blues", "YellowPantters", "Magenta"};

        np.setMinValue(0);
        np.setMaxValue(values.length-1);


        np.setDisplayedValues(values);

        np.setWrapSelectorWheel(true);
    }
}