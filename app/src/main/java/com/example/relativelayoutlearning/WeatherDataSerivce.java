package com.example.relativelayoutlearning;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
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

    private static final String API = "8544c10e7dc1de8a48fd3f18dd3c13ed";

    AppCompatActivity appCompatActivity;
    public WeatherDataSerivce(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }


    public void getWebservice(String city) {

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&lang=pl&appid=" + API;
        final Request request = new Request.Builder().url(url).build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                appCompatActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String error = "Brak internetu";

                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
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

                            long sunrise = sys.getLong("sunrise");
                            long sunset = sys.getLong("sunset");

                            //wind
                            String windd = (wind.getString("speed")) + " km/h";
                            //weather
                            String description = weather.getString("description");

                            ((TextView) appCompatActivity.findViewById(R.id.last_updated)).setText(lastModified);
                            ((TextView) appCompatActivity.findViewById(R.id.location)).setText(location);

                            ((TextView) appCompatActivity.findViewById(R.id.weatherDescription)).setText(description);
                            ((TextView) appCompatActivity.findViewById(R.id.temperature)).setText(temp);

                            ((TextView) appCompatActivity.findViewById(R.id.minTemp)).setText(temp_min);
                            ((TextView) appCompatActivity.findViewById(R.id.maxTemp)).setText(temp_max);


                            ((TextView) appCompatActivity.findViewById(R.id.sunrise))
                                    .setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                    .format(new Date(sunrise * 1000)));

                            ((TextView) appCompatActivity.findViewById(R.id.sunset))
                                    .setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                    .format(new Date(sunset * 1000)));

                            ((TextView) appCompatActivity.findViewById(R.id.wind)).setText(windd);
                            ((TextView) appCompatActivity.findViewById(R.id.pressure)).setText(pressure);
                            ((TextView) appCompatActivity.findViewById(R.id.humidity)).setText(humidity);


                        } catch (IOException | JSONException e) {
                            String error = "Niepoprawna miejscowość";
                            ((TextView) appCompatActivity.findViewById(R.id.location)).setText(error);
                        }
                    }
                });
            }
        });
    }
}
