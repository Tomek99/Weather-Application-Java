//package com.example.relativelayoutlearning;
//
//import android.app.Activity;
//import android.content.Context;
//import android.util.Log;
//import android.widget.EditText;
//import android.widget.TextView;
//
//
//import androidx.appcompat.app.AppCompatActivity;
//import okhttp3.*;
//import org.jetbrains.annotations.NotNull;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Locale;
//
//public class WeatherRepository extends AppCompatActivity {
//
//    private static final String API = "8544c10e7dc1de8a48fd3f18dd3c13ed";
//
//    public String temp_min;
//    public String temp_max;
//    public String pressure;
//    public String humidity;
//
//    private String location;
//    private String temp;
//    private Long sunset;
//    private Long sunrise;
//    private String windd;
//    public String description;
//    public String lastModified = "fafa";
//
//    protected void getWebservice(String city) {
//
//        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&lang=pl&appid=" + API;
//        final Request request = new Request.Builder().url(url).build();
//
//        OkHttpClient client = new OkHttpClient();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String error = "Brak internetu";
//
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            String jsonData = response.body().string();
//                            JSONObject jObject = new JSONObject(jsonData);
//
//                            JSONObject main = jObject.getJSONObject("main");
//                            JSONObject sys = jObject.getJSONObject("sys");
//                            JSONObject wind = jObject.getJSONObject("wind");
//                            JSONObject weather = jObject.getJSONArray("weather").getJSONObject(0);
//
//                            lastModified = "Ostatnia akutlizacja: " + (new SimpleDateFormat("dd/MM/yyyy hh:mm a",
//                                    Locale.ENGLISH).format(new Date(jObject.getLong("dt") * 1000)));
//
//                            //main
//                            temp = (Math.round(main.getDouble("temp"))) + "°C";
//                            temp_min = "Min temp: " + (main.getString("temp_min")) + "°C";
//                            temp_max = "Max temp: " + (main.getString("temp_max")) + "°C";
//                            pressure = main.getString("pressure");
//                            humidity = (main.getString("humidity")) + " %";
//
//
//                            location = jObject.getString("name") + ", " + sys.getString("country");
//
//                            sunrise = sys.getLong("sunrise");
//                            sunset = sys.getLong("sunset");
//
//                            //wind
//                            windd = (wind.getString("speed")) + " km/h";
//                            //weather
//                            description = weather.getString("description");
//
//                        } catch (IOException | JSONException e) {
//                            String error = "Zła lokalizacja";
//                        }
//                    }
//                });
//            }
//        });
//    }
//}
