package com.example.androidlevel1_lesson1;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView date,time,temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        Date dateCurrent=new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(dateCurrent);

        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeText = timeFormat.format(dateCurrent);

        String temperatureText=getString(R.string.temperature,12);

        setDate(dateText);
        setTime(timeText);
        setTemperature(temperatureText);

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