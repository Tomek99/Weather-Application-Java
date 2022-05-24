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

    OkHttpClient client = new OkHttpClient();

    final WeatherDataSerivce weatherDataSerivce = new WeatherDataSerivce(MainActivity.this, client);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCity = (EditText) findViewById(R.id.enterLocation);
        Button click = (Button) findViewById(R.id.searchLocation);


        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataSerivce.getWebservice(getCity.getText().toString(), new WeatherDataSerivce.ResponseListener() {
                    @Override
                    public void onError(String message) {
                        ((TextView) findViewById(R.id.location)).setText(message);
                    }

                    @Override
                    public void onResponse(String location, String lastModified, String description, String temp,
                    String temp_min, String temp_max, long sunrise, long sunset, String windd, String pressure, String humidity) {
                        ((TextView) findViewById(R.id.last_updated)).setText(lastModified);
                        ((TextView) findViewById(R.id.location)).setText(location);

                        ((TextView) findViewById(R.id.weatherDescription)).setText(description);
                        ((TextView) findViewById(R.id.temperature)).setText(temp);

                        ((TextView) findViewById(R.id.minTemp)).setText(temp_min);
                        ((TextView) findViewById(R.id.maxTemp)).setText(temp_max);


                        ((TextView) findViewById(R.id.sunrise))
                                .setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                        .format(new Date(sunrise * 1000)));

                        ((TextView) findViewById(R.id.sunset))
                                .setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                        .format(new Date(sunset * 1000)));

                        ((TextView) findViewById(R.id.wind)).setText(windd);
                        ((TextView) findViewById(R.id.pressure)).setText(pressure);
                        ((TextView) findViewById(R.id.humidity)).setText(humidity);
                    }
                });


            }
        });


    }
}
