package com.example.androidlevel1_lesson1;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView date,time,temperature,town,windSpeed,pressure;
    private Button settings,update,infoTown;
    private final String temperatureDataKey = "temperatureDataKey";
    private final String townDataKey = "townDataKey";
    private final String pressureDataKey = "pressureDataKey";
    private final String windSpeedDataKey = "windSpeedDataKey";
    private Random random=new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printMessage("onCreate");
        setContentView(R.layout.activity_main);
        initViews();
        outputDate();
        setOnClickSettings();
        setOnClickUpdate();
        setOnClickInfoTown();
    }

    @Override
    protected void onStart() {
        super.onStart();
        printMessage("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        printMessage("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        printMessage("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        printMessage("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        printMessage("onDestroy");
    }

    @Override
    public void onSaveInstanceState(@Nullable Bundle saveInstanceState) {
        printMessage("onSaveInstanceState");
        assert saveInstanceState != null;
        putSaveInstanceState(saveInstanceState,temperature,temperatureDataKey);
        putSaveInstanceState(saveInstanceState,pressure,pressureDataKey);
        putSaveInstanceState(saveInstanceState,windSpeed,windSpeedDataKey);
        putSaveInstanceState(saveInstanceState,town,townDataKey);
        super.onSaveInstanceState(saveInstanceState);
    }

    private void putSaveInstanceState(Bundle saveInstanceState, TextView textView,String key){
        String text = textView.getText().toString();
        saveInstanceState.putString(key,text);
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        assert savedInstanceState != null;
        super.onRestoreInstanceState(savedInstanceState);
        setSaveInstanceState(savedInstanceState,temperature,temperatureDataKey);
        setSaveInstanceState(savedInstanceState,pressure,pressureDataKey);
        setSaveInstanceState(savedInstanceState,windSpeed,windSpeedDataKey);
        setSaveInstanceState(savedInstanceState,town,townDataKey);
        super.onRestoreInstanceState(savedInstanceState);
    }
    private void setSaveInstanceState(Bundle savedInstanceState,TextView textView,String key){
        String text = savedInstanceState.getString(key);
        textView.setText(text);
    }
    private void printMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.d("Message",message);
    }

    private void initViews() {
        date=findViewById(R.id.dateCurrent);
        time=findViewById(R.id.timeCurrent);
        temperature=findViewById(R.id.temperatureCurrent);
        town=findViewById(R.id.townCurrent);
        town.setText(R.string.town);
        settings=findViewById(R.id.setting);
        update=findViewById(R.id.update);
        infoTown=findViewById(R.id.infoTown);
        windSpeed=findViewById(R.id.windSpeed);
        pressure=findViewById(R.id.pressure);
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
    private void setTown(String townCurrent) {
        town.setText(townCurrent);
    }
    private void setPressure(String pressureCurrent) {
        pressure.setVisibility(View.VISIBLE);
        pressure.setText(pressureCurrent);
    }
    private void setWindSpeed(String windSpeedCurrent) {
        windSpeed.setVisibility(View.VISIBLE);
        windSpeed.setText(windSpeedCurrent);
    }

    private void outputDate(){
        Date dateCurrent=new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(dateCurrent);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeText = timeFormat.format(dateCurrent);
        setDate(dateText);
        setTime(timeText);
        String txtTown = getIntent().getStringExtra("town");
        if (txtTown!=null)
            setTown(txtTown);
        setRandom();
    }
    private void setOnClickSettings(){
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivitySettings.class);
                startActivity(intent);
            }
        });
    }
    private void setOnClickUpdate(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRandom();
            }
        });
    }
    private void setRandom(){
        int temperatureRandom,pressureRandom,windSpeedRandom;
        temperatureRandom=random.nextInt(30);
        pressureRandom=random.nextInt(720);
        windSpeedRandom=random.nextInt(40);
        setTemperature(getString(R.string.temperature,temperatureRandom));
        boolean checkPressure=getIntent().getBooleanExtra("pressure",false);
        if (checkPressure)
            setPressure(getString(R.string.txtPressure,pressureRandom));
        boolean checkWindSpeed=getIntent().getBooleanExtra("windSpeed",false);
        if (checkWindSpeed)
            setWindSpeed(getString(R.string.txtWindSpeed,windSpeedRandom));
    }
    private void setOnClickInfoTown(){
        infoTown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://ru.wikipedia.org/wiki/"+town.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}