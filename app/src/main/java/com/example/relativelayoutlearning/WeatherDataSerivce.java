package com.example.relativelayoutlearning;

import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WeatherDataSerivce  {

    private String cityID;

    private static final String API = "8544c10e7dc1de8a48fd3f18dd3c13ed";

    AppCompatActivity appCompatActivity;
    OkHttpClient client;

    public interface ResponseListener {
        void onError(String message);

        void onResponse(String location, String lastModified, String description, String temp,
                        String temp_min, String temp_max, long sunrise, long sunset, String windd,
                        String pressure, String humidity);
    }


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
                cityID = "";
                appCompatActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String jsonData = response.body().string();
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
                            cityID = jObject.getString("name") + ", " + sys.getString("country");


                            long sunrise = sys.getLong("sunrise");
                            long sunset = sys.getLong("sunset");

                            //wind
                            String windd = (wind.getString("speed")) + " km/h";
                            //weather
                            String description = weather.getString("description");

                            responseListener.onResponse(location, lastModified, description, temp,
                                    temp_min, temp_max, sunrise, sunset, windd,
                                    pressure, humidity);


                        } catch (IOException | JSONException e) {
                            String error = "Niepoprawna miejscowość";
                            ((TextView) appCompatActivity.findViewById(R.id.location)).setText(error);
                            responseListener.onError("Something wrong");
                        }
                    }
                });
            }
        });
        client = new OkHttpClient();
    }
}
