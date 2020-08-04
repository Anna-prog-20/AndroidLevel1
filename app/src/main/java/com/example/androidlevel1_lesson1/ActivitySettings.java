package com.example.androidlevel1_lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivitySettings extends AppCompatActivity {
    private ListView town;
    private EditText townSelected;
    private Button select;
    private CheckBox windSpeed,pressure;
    private final String windSpeedKey = "windSpeedKey";
    private final String pressureKey = "pressureKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printMessage("OnCreate");
        setContentView(R.layout.activity_settings);
        initViews();
        selectTown();
        setOnClickSelect();
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
        boolean checkWindSpeed=windSpeed.isChecked();
        boolean checkPressure=pressure.isChecked();
        assert saveInstanceState != null;
        saveInstanceState.putBoolean(windSpeedKey,checkWindSpeed);
        saveInstanceState.putBoolean(pressureKey,checkPressure);
        saveInstanceState.putSerializable("someKey", DataContainerSingleton.getInstance());
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        assert savedInstanceState != null;
        super.onRestoreInstanceState(savedInstanceState);
        boolean checkWindSpeed=savedInstanceState.getBoolean(windSpeedKey);
        boolean checkPressure=savedInstanceState.getBoolean(pressureKey);
        windSpeed.setChecked(checkWindSpeed);
        pressure.setChecked(checkPressure);
    }

    private void printMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.d("Message",message);
    }

    private void initViews() {
        town=findViewById(R.id.listTown);
        townSelected=findViewById(R.id.textTown);
        select = findViewById(R.id.select);
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
   private void setOnClickSelect(){
       select.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(ActivitySettings.this, MainActivity.class);
               intent.putExtra("town", townSelected.getText().toString());
               intent.putExtra("windSpeed",windSpeed.isChecked());
               intent.putExtra("pressure",pressure.isChecked());
               startActivity(intent);
           }
       });
   }
}