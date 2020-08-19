package com.example.androidlevel1_lesson1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import static com.example.androidlevel1_lesson1.FragmentWeather.dataKey;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

public class FragmentTownList extends Fragment implements IRVOnItemClick{
    private boolean isExistWeather=false;
    private DataContainer currentData;
    private String townKey="townKey";
    private RecyclerView town;
    private TextInputEditText townSelected;
    private ArrayList<String> arrayListTown;
    private String value;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_town,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        checkTown();
        setupRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isExistWeather = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null)
            currentData = (DataContainer) savedInstanceState.getSerializable(townKey);
        else {
            currentData = new DataContainer(getResources().getStringArray(R.array.listTown)[0]);
            getCurrentData(getResources().getStringArray(R.array.listTown)[0]);
        }
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
        onClick(itemText);
    }

    private DataContainer getCurrentData(String itemText){
        currentData=new DataContainer(itemText);
        return currentData;
    }

    private void onClick(final String itemText){
        townSelected.setText(itemText);
        showWeather(getCurrentData(itemText));
    }

    private void initViews(View view) {
        town=view.findViewById(R.id.listTown);
        townSelected=view.findViewById(R.id.textTown);
        townSelected.setText(getResources().getStringArray(R.array.listTown)[0]);
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
                hideError((TextView)townSelected);
                Intent intent=new Intent(getActivity(), ActivityWeather.class);
                intent.putExtra(dataKey,currentData);
                startActivity(intent);
            }
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerDataAdapterTown adapterTown = new RecyclerDataAdapterTown(arrayListTown, this);
        town.setLayoutManager(layoutManager);
        town.setAdapter(adapterTown);
    }

    private void checkTown() {
        townSelected.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    TextView tv = (TextView) v;
                    FragmentTownList.this.validate(tv,getResources().getString(R.string.messageTown));
                }
            }
        });
    }

    private void validate(final TextView tv, String message){
        value = tv.getText().toString();
        boolean check = true;
        for (String string : arrayListTown) {
            if(string.equalsIgnoreCase(value)) {
                value=string;
                check = true;
                break;
            }
            else{
                check =false;
            }
        }
        if(check){
            hideError(tv);
            onClick(value);
        } else {
            showError(tv, message);
                Snackbar.make(requireView(), "Хотите добавить город?", Snackbar.LENGTH_LONG)
                            .setAction("Добавить", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (connection()){
                                        arrayListTown.add(value);
                                        setupRecyclerView();
                                        town.scrollToPosition(arrayListTown.size()-1);
                                        FragmentTownList.this.hideError(tv);
                                        Snackbar.make(requireView(), "Город успешно добавлен!", Snackbar.LENGTH_LONG).show();
                                    }
                                    else{
                                        Snackbar.make(requireView(), "Такого города не существует!", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            }).show();
        }
    }

    private boolean connection(){
        try {
            URL uri = new URL(getString(R.string.weatherURL,townSelected.getText(), "80efcfee52d4195b8ef83e2e5b69a707"));
            HttpsURLConnection urlConnection = null;
            try {
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            } catch (Exception e) {
                return false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void showError(TextView view, String message) {
        view.setError(message);
    }

    private void hideError(TextView view) {
        view.setError(null);
    }
}
