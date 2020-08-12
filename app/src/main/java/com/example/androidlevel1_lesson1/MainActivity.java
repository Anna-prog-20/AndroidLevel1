package com.example.androidlevel1_lesson1;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity{
    private TextView town;
    private Button infoTown;
    final static String townKey="townKey";
    final static String windSpeedKey="windSpeedKey";
    final static String pressureKey="pressureKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setOnClickInfoTown();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int countOfFragmentInManager = getSupportFragmentManager().getBackStackEntryCount();
        if(countOfFragmentInManager > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void initViews() {
        if(findViewById(R.id.townCurrent)!=null)
            town=findViewById(R.id.townCurrent);
        else
            town=findViewById(R.id.textTown);
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