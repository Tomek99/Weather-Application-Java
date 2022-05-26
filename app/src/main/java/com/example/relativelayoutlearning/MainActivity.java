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

import com.google.gson.Gson;
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

    DataWarehouse dataWarehouse = new DataWarehouse();

    Gson gson = new Gson();

    String json = gson.toJson(dataWarehouse);

    final WeatherDataSerivce weatherDataSerivce = new WeatherDataSerivce(MainActivity.this,
            client, dataWarehouse);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCity = (EditText) findViewById(R.id.enterLocation);
        Button click = (Button) findViewById(R.id.searchLocation);


        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataSerivce.getWebservice(getCity.getText().toString(), new ResponseListener() {
                    @Override
                    public void setLocation(String location) {
                        ((TextView) findViewById(R.id.location)).setText(location);
                    }

                    @Override
                    public void setLastModified(String lastModified) {
                        ((TextView) findViewById(R.id.last_updated)).setText(lastModified);
                    }

                    @Override
                    public void setDescription(String description) {
                        ((TextView) findViewById(R.id.weatherDescription)).setText(description);
                    }

                    @Override
                    public void setTemp(String temp) {
                        ((TextView) findViewById(R.id.temperature)).setText(temp);
                    }

                    @Override
                    public void setTempMin(String tempMin) {
                        ((TextView) findViewById(R.id.minTemp)).setText(tempMin);
                    }

                    @Override
                    public void setTempMax(String tempMax) {
                        ((TextView) findViewById(R.id.maxTemp)).setText(tempMax);
                    }

                    @Override
                    public void setSunrise(long sunrise) {
                        ((TextView) findViewById(R.id.sunrise))
                                .setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                        .format(new Date(sunrise * 1000)));
                    }

                    @Override
                    public void setSunset(long sunset) {
                        ((TextView) findViewById(R.id.sunset))
                                .setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                        .format(new Date(sunset * 1000)));
                    }

                    @Override
                    public void setWind(String wind) {
                        ((TextView) findViewById(R.id.wind)).setText(wind);
                    }

                    @Override
                    public void setPressure(String pressure) {
                        ((TextView) findViewById(R.id.pressure)).setText(pressure);
                    }

                    @Override
                    public void setHumidity(String humidity) {
                        ((TextView) findViewById(R.id.humidity)).setText(humidity);
                    }

                    @Override
                    public void setError(String message) {
                        ((TextView) findViewById(R.id.location)).setText(message);
                    }
                });
            }
        });
    }
}
