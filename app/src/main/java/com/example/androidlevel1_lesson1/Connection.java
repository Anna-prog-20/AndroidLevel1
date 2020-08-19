package com.example.androidlevel1_lesson1;

import android.os.Handler;

import com.example.androidlevel1_lesson1.model.WeatherRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class Connection extends Thread implements Runnable{
    private URL uri;
    private Handler handler;
    private FragmentWeather fragmentWeather;

    public Connection(URL uri, Handler handler, FragmentWeather fragmentWeather) {
        this.uri = uri;
        this.handler = handler;
        this.fragmentWeather = fragmentWeather;
    }

    @Override
    public void run() {
        HttpsURLConnection urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection) uri.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String result = getLines(in);
            Gson gson = new Gson();
            final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        fragmentWeather.displayWeather(weatherRequest);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            Snackbar.make(fragmentWeather.requireView(), "Данных по данному городу нет!", Snackbar.LENGTH_LONG).show();
            fragmentWeather.requireActivity().finish();
            e.printStackTrace();
        }
        finally {
            if (null != urlConnection) {
                urlConnection.disconnect();
            }
        }
    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
}
