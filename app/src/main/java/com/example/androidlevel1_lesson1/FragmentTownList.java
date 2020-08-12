package com.example.androidlevel1_lesson1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.androidlevel1_lesson1.FragmentWeather.dataKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class FragmentTownList extends Fragment implements IRVOnItemClick{
    private boolean isExistWeather=false;
    private DataContainer currentData;
    private String townKey="townKey";
    private RecyclerView town;
    private EditText townSelected;;
    private ArrayList<String> arrayListTown;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_town,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isExistWeather = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null)
            currentData = (DataContainer) savedInstanceState.getSerializable(townKey);
        else
            currentData=new DataContainer(getResources().getStringArray(R.array.listTown)[0]);
       if (isExistWeather){
            showWeather(currentData);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(townKey,currentData);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClicked(String itemText) {
        int temperatureRandom,pressureRandom,windSpeedRandom;
        Random random=new Random();
        temperatureRandom=random.nextInt(30);
        pressureRandom=random.nextInt(720);
        windSpeedRandom=random.nextInt(40);
        String[] t=new String[7];
        for(int i=0;i<7;i++)
            t[i]=getString(R.string.templateTemperature,random.nextInt(30));
        ArrayList<String> arrayListTemperature = new ArrayList<String>(Arrays.asList(t));
        townSelected.setText(itemText);
        currentData=new DataContainer(itemText);
        currentData.setTemperature(temperatureRandom);
        currentData.setPressure(pressureRandom);
        currentData.setWindSpeed(windSpeedRandom);
        currentData.setListTemperature(arrayListTemperature);
        showWeather(currentData);
    }

    private void initViews(View view) {
        town=view.findViewById(R.id.listTown);
        townSelected=view.findViewById(R.id.textTown);
        arrayListTown=new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listTown)));
    }

    private void showWeather(DataContainer currentData){
        if(isExistWeather) {
            assert getFragmentManager() != null;
            FragmentWeather detail = (FragmentWeather) getFragmentManager().findFragmentById(R.id.fragmentMainWeather);
            if (detail == null|| !detail.getDataCurrent().getTown().equals(currentData.getTown())) {
                detail = FragmentWeather.create(currentData);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentMainWeather, detail);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack("Some_Key");
                ft.commit();
            }
        }
            else {
                Intent intent=new Intent();
                intent.setClass(Objects.requireNonNull(getActivity()), ActivityWeather.class);
                intent.putExtra(dataKey,currentData);
                startActivity(intent);
            }
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        RecyclerDataAdapterTown adapterTown = new RecyclerDataAdapterTown(arrayListTown, this);
        town.setLayoutManager(layoutManager);
        town.setAdapter(adapterTown);
    }
}
