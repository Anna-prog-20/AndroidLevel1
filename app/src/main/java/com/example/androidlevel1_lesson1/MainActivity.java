package com.example.androidlevel1_lesson1;


import android.content.Intent;
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
    private TextView date,time,temperature,town;
    private Button settings,update;
    private final String temperatureDataKey = "temperatureDataKey";
    private final String townDataKey = "townDataKey";
    private int temperatureRandom;
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
        String textTemperature = temperature.getText().toString();
        String textTown = town.getText().toString();
        assert saveInstanceState != null;
        saveInstanceState.putString(temperatureDataKey, textTemperature);
        saveInstanceState.putString(townDataKey, textTown);
        saveInstanceState.putSerializable("someKey", new DataContainer());
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        assert savedInstanceState != null;
        super.onRestoreInstanceState(savedInstanceState);
        String textTemperature = savedInstanceState.getString(temperatureDataKey);
        String textTown = savedInstanceState.getString(townDataKey);
        temperature.setText(textTemperature);
        town.setText(textTown);
        super.onRestoreInstanceState(savedInstanceState);
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
    private void outputDate(){
        Date dateCurrent=new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(dateCurrent);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeText = timeFormat.format(dateCurrent);
        temperatureRandom=random.nextInt(30);
        String temperatureText=getString(R.string.temperature,temperatureRandom);
        setDate(dateText);
        setTime(timeText);
        setTemperature(temperatureText);
        String txtTown = getIntent().getStringExtra("town");
        if (txtTown!=null)
            setTown(txtTown);
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
                temperatureRandom=random.nextInt(30);
                setTemperature(getString(R.string.temperature,temperatureRandom));
            }
        });
    }
}