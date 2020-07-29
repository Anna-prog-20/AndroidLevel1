package com.example.androidlevel1_lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySettings extends AppCompatActivity {
    private ListView town;
    private EditText townSelected;
    private Button select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        selectTown();
        setOnClickSelect();
    }
    private void initViews() {
        town=findViewById(R.id.listTown);
        townSelected=findViewById(R.id.textTown);
        select = findViewById(R.id.select);
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
               startActivity(intent);
           }
       });
   }
}