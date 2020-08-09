package com.example.androidlevel1_lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ActivitySettings extends AppCompatActivity {
    private ListView town;
    private EditText townSelected;
    private CheckBox windSpeed,pressure;
    private final String windSpeedDataKey = "windSpeedDataKey";
    private final String pressureDataKey = "pressureDataKey";
    final static String townKey="townKey";
    final static String windSpeedKey="windSpeedKey";
    final static String pressureKey="pressureKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printMessage("OnCreate");
        setContentView(R.layout.activity_settings);
        showBackBtn();
        initViews();
        showDataFromFirstActivity();
        selectTown();
    }

    @Override
    public void onSaveInstanceState(@Nullable Bundle saveInstanceState) {
        printMessage("onSaveInstanceState");
        boolean checkWindSpeed=windSpeed.isChecked();
        boolean checkPressure=pressure.isChecked();
        assert saveInstanceState != null;
        saveInstanceState.putBoolean(windSpeedDataKey,checkWindSpeed);
        saveInstanceState.putBoolean(pressureDataKey,checkPressure);
        saveInstanceState.putSerializable("someKey", DataContainerSingleton.getInstance());
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        assert savedInstanceState != null;
        super.onRestoreInstanceState(savedInstanceState);
        boolean checkWindSpeed=savedInstanceState.getBoolean(windSpeedDataKey);
        boolean checkPressure=savedInstanceState.getBoolean(pressureDataKey);
        windSpeed.setChecked(checkWindSpeed);
        pressure.setChecked(checkPressure);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivitySettings.this, MainActivity.class);
        intent.putExtra(townKey, townSelected.getText().toString());
        intent.putExtra(windSpeedKey,windSpeed.isChecked());
        intent.putExtra(pressureKey,pressure.isChecked());
        startActivity(intent);
    }

    private void printMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.d("Message",message);
    }

    private void initViews() {
        town=findViewById(R.id.listTown);
        townSelected=findViewById(R.id.textTown);
        windSpeed=findViewById(R.id.windSpeed);
        pressure=findViewById(R.id.pressure);
    }

   private void selectTown(){
       String[] townList = getResources().getStringArray(R.array.listTown);
       ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
               android.R.layout.simple_list_item_1, townList);
       town.setAdapter(adapter);
       town.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               townSelected.setText(parent.getAdapter().getItem(position).toString());
           }
       });
   }

    private void showBackBtn() {
        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ActivitySettings.this, MainActivity.class);
            intent.putExtra(townKey, townSelected.getText().toString());
            intent.putExtra(windSpeedKey,windSpeed.isChecked());
            intent.putExtra(pressureKey,pressure.isChecked());
            startActivity(intent);
        }
        return true;
    }
    private void showDataFromFirstActivity() {
        String txtTown = getIntent().getStringExtra(MainActivity.townKey);
        if (txtTown!=null)
            townSelected.setText(txtTown);
        boolean checkWindSpeed = getIntent().getBooleanExtra(MainActivity.windSpeedKey,false);
        windSpeed.setChecked(checkWindSpeed);
        boolean checkPressure = getIntent().getBooleanExtra(MainActivity.pressureKey,false);
        pressure.setChecked(checkPressure);
    }
}