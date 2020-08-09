package com.example.androidlevel1_lesson1;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.otto.Subscribe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class FragmentWeather extends Fragment {
    public static final String dataKey = "dataKey";
    private TextView date,time,temperature,town,windSpeed,pressure;

    public static FragmentWeather create(DataContainer currentData){
        FragmentWeather f=new FragmentWeather();
        Bundle args=new Bundle();
        args.putSerializable(dataKey,currentData);
        f.setArguments(args);
        return f;
    }

    public DataContainer getDataCurrent(){
        try {
            assert getArguments() != null;
            return (DataContainer) getArguments().getSerializable(dataKey);
        } catch (Exception e) {
            return (DataContainer) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(dataKey);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        initViews(view);
        outputData();
        return view;
    }

    private void initViews(View view) {
        date=view.findViewById(R.id.dateCurrent);
        time=view.findViewById(R.id.timeCurrent);
        temperature=view.findViewById(R.id.temperatureCurrent);
        town=view.findViewById(R.id.townCurrent);
        windSpeed=view.findViewById(R.id.windSpeed);
        pressure=view.findViewById(R.id.pressure);
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
        pressure.setText(pressureCurrent);
    }
    private void setWindSpeed(String windSpeedCurrent) {
        windSpeed.setText(windSpeedCurrent);
    }
    private void outputData(){
        DataContainer currentData = getDataCurrent();
        Date dateCurrent=new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(dateCurrent);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeText = timeFormat.format(dateCurrent);
        setDate(dateText);
        setTime(timeText);
        if (currentData!=null) {
            String txtTown = currentData.getTown();
            setTown(txtTown);
            setTemperature(getString(R.string.temperature,currentData.getTemperature()));
            setPressure(getString(R.string.txtPressure,currentData.getPressure()));
            setWindSpeed(getString(R.string.txtWindSpeed,currentData.getWindSpeed()));
        }
    }
}
