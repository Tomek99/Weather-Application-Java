package com.example.relativelayoutlearning;

import android.util.Log;
import com.google.gson.Gson;

public class DataWarehouse {

    String location;
    String lastModified;
    String description;
    String temp;
    String tempMin;
    String tempMax;
    long sunrise;
    long sunset;
    String wind;
    String pressure;
    String humidity;

    public DataWarehouse(String location, String lastModified, String description, String temp, String tempMin,
                         String tempMax, long sunrise, long sunset, String wind, String pressure, String humidity) {
        this.location = location;
        this.lastModified = lastModified;
        this.description = description;
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.wind = wind;
        this.pressure = pressure;
        this.humidity = humidity;
    }
}
