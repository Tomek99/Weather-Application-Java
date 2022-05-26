package com.example.relativelayoutlearning;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class WeatherDataSerivce {

    private static final String API = "8544c10e7dc1de8a48fd3f18dd3c13ed";


    AppCompatActivity appCompatActivity;
    OkHttpClient client;

    public WeatherDataSerivce(AppCompatActivity appCompatActivity, OkHttpClient client) {
        this.appCompatActivity = appCompatActivity;
        this.client = client;
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
                        responseListener.onError(error);

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

                            String location = jObject.getString("name") + ", " + sys.getString("country");

                            String lastModified = "Ostatnia akutlizacja: " + (new SimpleDateFormat
                                    ("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format
                                    (new Date(jObject.getLong("dt") * 1000)));

                            //main
                            String temp = (Math.round(main.getDouble("temp"))) + "°C";
                            String tempMin = "Min temp: " + (main.getString("temp_min")) + "°C";
                            String tempMax = "Max temp: " + (main.getString("temp_max")) + "°C";
                            String pressure = main.getString("pressure");
                            String humidity = (main.getString("humidity")) + " %";


                            long sunrise = sys.getLong("sunrise");
                            long sunset = sys.getLong("sunset");

                            //wind
                            String windd = (wind.getString("speed")) + " km/h";
                            //weather
                            String description = weather.getString("description");

                            Gson gson = new Gson();

                            DataWarehouse dataWarehouse = new DataWarehouse(location, lastModified, description, temp,
                                    tempMin, tempMax, sunrise, sunset, windd, pressure, humidity);

                            String json = gson.toJson(dataWarehouse);

                            responseListener.onResponse(json, gson);


                        } catch (IOException | JSONException e) {
                            String error = "Niepoprawna miejscowość";
                            responseListener.onError(error);
                        }
                    }
                });
            }
        });
        client = new OkHttpClient();
    }
}
