package com.example.relativelayoutlearning;

public interface ResponseListener {

    void setLocation(String location);

    void setLastModified(String lastModified);

    void setDescription(String description);

    void setTemp(String temp);

    void setTempMin(String tempMin);

    void setTempMax(String tempMax);

    void setSunrise(long sunrise);

    void setSunset(long sunset);

    void setWind(String wind);

    void setPressure(String pressure);

    void setHumidity(String humidity);

    void setError(String message);

}
