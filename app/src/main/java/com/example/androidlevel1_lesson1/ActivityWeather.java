package com.example.androidlevel1_lesson1;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ActivityWeather extends AppCompatActivity {
    private TextView town;
    private Button infoTown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }
        setContentView(R.layout.activity_main_weather);
        if(savedInstanceState==null){
            FragmentWeather details=new FragmentWeather();
            details.setArguments(getIntent().getExtras());
        }
        initViews();
        setOnClickInfoTown();
    }
    private void initViews() {
       town=findViewById(R.id.townCurrent);
       infoTown=findViewById(R.id.infoTown);
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
