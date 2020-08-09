package com.example.androidlevel1_lesson1;

import java.io.Serializable;

public class DataContainer implements Serializable {
    int index;
    int temperature;
    int pressure;
    int windSpeed;
    String town;

   public DataContainer(int index, String town) {
        this.index = index;
        this.town = town;
    }

    public String getTown() {
        return town;
    }
    public int getIndex() {
        return index;
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
}
