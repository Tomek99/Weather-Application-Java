package com.example.relativelayoutlearning;

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

    ResponseListener responseListener = new ResponseListener() {
        @Override
        public void setLocation(String location) {

        }

        @Override
        public void setLastModified(String lastModified) {

        }

        @Override
        public void setDescription(String description) {

        }

        @Override
        public void setTemp(String temp) {

        }

        @Override
        public void setTempMin(String tempMin) {

        }

        @Override
        public void setTempMax(String tempMax) {

        }

        @Override
        public void setSunrise(long sunrise) {

        }

        @Override
        public void setSunset(long sunset) {

        }

        @Override
        public void setWind(String wind) {

        }

        @Override
        public void setPressure(String pressure) {

        }

        @Override
        public void setHumidity(String humidity) {

        }

        @Override
        public void setError(String message) {

        }
    };

}
