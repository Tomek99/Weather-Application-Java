package com.example.relativelayoutlearning;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class WeatherDataSerivce  {

    private static final String API = "8544c10e7dc1de8a48fd3f18dd3c13ed";


    AppCompatActivity appCompatActivity;
    OkHttpClient client;
    DataWarehouse dataWarehouse;

    public WeatherDataSerivce(AppCompatActivity appCompatActivity, OkHttpClient client, DataWarehouse dataWarehouse) {
        this.appCompatActivity = appCompatActivity;
        this.client = client;
        this.dataWarehouse = dataWarehouse;
    }

    public void getWebservice(String city, final ResponseListener responseListener) {


        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&lang=pl&appid=" + API;
        final Request request = new Request.Builder().url(url).build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                appCompatActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String error = "Brak internetu";
                        responseListener.setError(error);

                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {

                appCompatActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String jsonData = Objects.requireNonNull(response.body()).string();
                            JSONObject jObject = new JSONObject(jsonData);

                            JSONObject main = jObject.getJSONObject("main");
                            JSONObject sys = jObject.getJSONObject("sys");
                            JSONObject wind = jObject.getJSONObject("wind");
                            JSONObject weather = jObject.getJSONArray("weather").getJSONObject(0);

                            String lastModified = "Ostatnia akutlizacja: " + (new SimpleDateFormat
                                    ("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format
                                    (new Date(jObject.getLong("dt") * 1000)));

                            //main
                            String temp = (Math.round(main.getDouble("temp"))) + "°C";
                            String temp_min = "Min temp: " + (main.getString("temp_min")) + "°C";
                            String temp_max = "Max temp: " + (main.getString("temp_max")) + "°C";
                            String pressure = main.getString("pressure");
                            String humidity = (main.getString("humidity")) + " %";

                            String location = jObject.getString("name") + ", " + sys.getString("country");

                            long sunrise = sys.getLong("sunrise");
                            long sunset = sys.getLong("sunset");

                            //wind
                            String windd = (wind.getString("speed")) + " km/h";
                            //weather
                            String description = weather.getString("description");

//                            responseListener.onResponse(location, lastModified, description, temp,
//                                    temp_min, temp_max, sunrise, sunset, windd,
//                                    pressure, humidity);
                            dataWarehouse.responseListener.setLocation(location);
                            responseListener.setLastModified(lastModified);
                            responseListener.setDescription(description);
                            responseListener.setTemp(temp);
                            responseListener.setTempMin(temp_min);
                            responseListener.setTempMax(temp_max);
                            responseListener.setSunrise(sunrise);
                            responseListener.setSunset(sunset);
                            responseListener.setWind(windd);
                            responseListener.setPressure(pressure);
                            responseListener.setHumidity(humidity);


                        } catch (IOException | JSONException e) {
                            String error = "Niepoprawna miejscowość";
                            responseListener.setError(error);
                        }
                    }
                });
            }
        });
        client = new OkHttpClient();
    }
}
