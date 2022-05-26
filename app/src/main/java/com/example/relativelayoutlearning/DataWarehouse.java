package com.example.relativelayoutlearning;

public class DataWarehouse {

    protected String location;
    protected String lastModified;
    protected String description;
    protected String temp;
    protected String tempMin;
    protected String tempMax;
    protected long sunrise;
    protected long sunset;
    protected String wind;
    protected String pressure;
    protected String humidity;

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
