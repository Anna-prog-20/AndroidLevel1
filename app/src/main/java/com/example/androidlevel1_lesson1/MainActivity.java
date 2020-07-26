package com.example.androidlevel1_lesson1;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView date,time,temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        Date dateCurrent=new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateText = dateFormat.format(dateCurrent);

        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String timeText = timeFormat.format(dateCurrent);

        setDate(dateText);
        setTime(timeText);
        setTemperature(String.format("%d \u2103",12));

    }

    private void initViews() {
        date=findViewById(R.id.textView);
        time=findViewById(R.id.textView3);
        temperature=findViewById(R.id.textView4);
    }

    private void setDate(String dateCurrent) {
        date.setText(dateCurrent);
    }
    private void setTime(String timeCurrent) {
        time.setText(timeCurrent);
    }
    private void setTemperature(String temperatureCurrent) {
        temperature.setText(temperatureCurrent);
    }
}