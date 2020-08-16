package com.example.androidlevel1_lesson1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.example.androidlevel1_lesson1.MainActivity.townKey;

public class FragmentWeather extends Fragment{
    public static final String dataKey = "dataKey";
    private TextView date,time,temperature,town,windSpeed,pressure;
    private RecyclerView listWeather;
    private ArrayList<String> arrayListWeek;
    private ArrayList<String> arrayListTemperature;
    private DataContainer currentData;

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
            return (DataContainer) requireActivity().getIntent().getSerializableExtra(dataKey);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(townKey,currentData);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        outputData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initViews(View view) {
        date=view.findViewById(R.id.dateCurrent);
        time=view.findViewById(R.id.timeCurrent);
        temperature=view.findViewById(R.id.temperatureCurrent);
        town=view.findViewById(R.id.townCurrent);
        windSpeed=view.findViewById(R.id.windSpeed);
        pressure=view.findViewById(R.id.pressure);
        listWeather = view.findViewById(R.id.listWeather);
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
        currentData = getDataCurrent();
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
            arrayListWeek=new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listWeek)));
            arrayListTemperature=currentData.getListTemperature();
            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        RecyclerDataAdapter adapter = new RecyclerDataAdapter(arrayListWeek, arrayListTemperature);
        listWeather.setLayoutManager(layoutManager);
        listWeather.setAdapter(adapter);
    }
}
