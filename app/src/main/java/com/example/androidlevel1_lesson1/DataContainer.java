package com.example.androidlevel1_lesson1;

import java.io.Serializable;
import java.util.ArrayList;

public class DataContainer implements Serializable {
    private int temperature;
    private int pressure;
    private int windSpeed;
    private ArrayList<String> listTemperature;
    private String town;

    public DataContainer(String town) {
        this.town = town;
    }

    public String getTown() {
       return town;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public ArrayList<String> getListTemperature() {
        return listTemperature;
    }

    public void setListTemperature(ArrayList<String> listTemperature) {
        this.listTemperature = listTemperature;
    }

}
