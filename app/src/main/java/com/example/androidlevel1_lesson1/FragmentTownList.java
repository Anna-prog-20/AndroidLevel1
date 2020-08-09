package com.example.androidlevel1_lesson1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.otto.Subscribe;

import static com.example.androidlevel1_lesson1.FragmentWeather.dataKey;

import java.util.Objects;
import java.util.Random;

public class FragmentTownList extends Fragment {
    private boolean isExistWeather=false;
    private DataContainer currentData;
    private int currentPosition;
    private String townKey="townKey";
    private ListView town;
    private EditText townSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_town,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        selectTown();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isExistWeather = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null)
            currentData = (DataContainer) savedInstanceState.getSerializable(townKey);
        else
            currentData=new DataContainer(0,getResources().getStringArray(R.array.listTown)[0]);
       if (isExistWeather){
            town.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showWeather(currentData);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(townKey,currentData);
        super.onSaveInstanceState(outState);
    }

    private void initViews(View view) {
        town=view.findViewById(R.id.listTown);
        townSelected=view.findViewById(R.id.textTown);
    }
    private void selectTown(){
        String[] townList = getResources().getStringArray(R.array.listTown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_list_item_1, townList);
        town.setAdapter(adapter);
        town.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int temperatureRandom,pressureRandom,windSpeedRandom;
                Random random=new Random();
                temperatureRandom=random.nextInt(30);
                pressureRandom=random.nextInt(720);
                windSpeedRandom=random.nextInt(40);
                townSelected.setText(parent.getAdapter().getItem(position).toString());
                currentPosition=position;
                currentData=new DataContainer(currentPosition,getResources().getStringArray(R.array.listTown)[position]);
                currentData.setTemperature(temperatureRandom);
                currentData.setPressure(pressureRandom);
                currentData.setWindSpeed(windSpeedRandom);
                showWeather(currentData);
            }
        });
    }
    private void showWeather(DataContainer currentData){
        if(isExistWeather) {
            assert getFragmentManager() != null;
            FragmentWeather detail = (FragmentWeather) getFragmentManager().findFragmentById(R.id.fragmentMainWeather);
            if (detail == null||detail.getDataCurrent().getIndex()!=currentData.getIndex()) {
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
}
