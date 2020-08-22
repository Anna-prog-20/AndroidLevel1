package com.example.androidlevel1_lesson1;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import static com.example.androidlevel1_lesson1.FragmentWeather.dataKey;


public class MainActivity extends AppCompatActivity{
    private TextView town;
    private DataContainer currentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            setTitle(R.string.app_name);
        }else setTitle(R.string.itemTown);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(getResources().getConfiguration().orientation!= Configuration.ORIENTATION_LANDSCAPE)
            menu.findItem(R.id.itemSetting).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemInfoTown: {
                Uri uri = Uri.parse("https://ru.wikipedia.org/wiki/"+town.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            }
            case R.id.itemInfo: {
                Intent intent = new Intent(this, ActivityInfo.class);
                startActivity(intent);
                return true;
            }
            case R.id.itemSetting: {
                Intent intent = new Intent(this, ActivitySettings.class);
                currentData=(DataContainer) Objects.requireNonNull(getIntent().getExtras()).getSerializable(dataKey);
                intent.putExtra(dataKey,currentData);
                startActivity(intent);
                return true;
            }
           default: {
               return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==88){
            recreate();
        }
    }

    @Override
    public void onSaveInstanceState(@Nullable Bundle saveInstanceState) {
        assert saveInstanceState != null;
        saveInstanceState.putSerializable(dataKey,currentData);
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        assert savedInstanceState != null;
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initViews() {
        if(findViewById(R.id.townCurrent)!=null)
            town=findViewById(R.id.townCurrent);
        else
            town=findViewById(R.id.textTown);
    }
}