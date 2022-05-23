package com.example.relativelayoutlearning;


import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final String API = "8544c10e7dc1de8a48fd3f18dd3c13ed";

    private EditText getCity;
    private OkHttpClient client;


    private String location;
    private Long sunset;
    private Long sunrise;
    private String windd;
    private String description;
    private String lastModified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCity = (EditText) findViewById(R.id.enterLocation);
        Button click = (Button) findViewById(R.id.searchLocation);



        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWebservice(getCity.getText().toString());
                getCity.setText("");
            }
        });
        client = new OkHttpClient();
    }


    private void getWebservice(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&lang=pl&appid=" + API;
        final Request request = new Request.Builder().url(url).build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String error = "Brak internetu";
                        ((TextView) findViewById(R.id.location)).setText(error);
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String jsonData = response.body().string();
                            JSONObject jObject = new JSONObject(jsonData);

                            JSONObject main = jObject.getJSONObject("main");
                            JSONObject sys = jObject.getJSONObject("sys");
                            JSONObject wind = jObject.getJSONObject("wind");
                            JSONObject weather = jObject.getJSONArray("weather").getJSONObject(0);

                            lastModified = "Ostatnia akutlizacja: " + (new SimpleDateFormat("dd/MM/yyyy hh:mm a",
                                    Locale.ENGLISH).format(new Date(jObject.getLong("dt") * 1000)));

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

                            ((TextView) findViewById(R.id.last_updated)).setText(lastModified);
                            ((TextView) findViewById(R.id.location)).setText(location);

                            ((TextView) findViewById(R.id.weatherDescription)).setText(description);
                            ((TextView) findViewById(R.id.temperature)).setText(temp);

                            ((TextView) findViewById(R.id.minTemp)).setText(temp_min);
                            ((TextView) findViewById(R.id.maxTemp)).setText(temp_max);


                            ((TextView) findViewById(R.id.sunrise)).setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                    .format(new Date(sunrise * 1000)));

                            ((TextView) findViewById(R.id.sunset)).setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                    .format(new Date(sunset * 1000)));

                            ((TextView) findViewById(R.id.wind)).setText(windd);
                            ((TextView) findViewById(R.id.pressure)).setText(pressure);
                            ((TextView) findViewById(R.id.humidity)).setText(humidity);


                        } catch (IOException | JSONException e) {
                            String error = "Bład danych";
                            ((TextView) findViewById(R.id.location)).setText(error);
                        }
                    }
                });
            }
        });
    }
}
                    }