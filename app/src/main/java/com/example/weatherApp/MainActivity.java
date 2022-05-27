package com.example.weatherApp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.*;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import android.sax.ElementListener;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CurrentLocation currentLocation;

    OkHttpClient client = new OkHttpClient();

    final WeatherDataSerivce weatherDataSerivce = new WeatherDataSerivce(MainActivity.this,
            client);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText getCity = (EditText) findViewById(R.id.enterLocation);
        Button btn1 = (Button) findViewById(R.id.searchLocation);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataSerivce.getWebservice(getCity.getText().toString().trim(), new ResponseListener() {
                    @Override
                    public void onError(String message) {
                        ((TextView) findViewById(R.id.location)).setText(message);
                    }

                    @Override
                    public void onResponse(DataWarehouse data) {
                        setValues(data);
                    }
                });
                getCity.setText("");
            }
        });
        ImageButton btn2 = (ImageButton) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLocation = new CurrentLocation(MainActivity.this, weatherDataSerivce);
            }
        });
    }


    private void setValues(DataWarehouse value) {
        ((TextView) findViewById(R.id.location)).setText(value.location);
        ((TextView) findViewById(R.id.last_updated)).setText(value.lastModified);
        ((TextView) findViewById(R.id.weatherDescription)).setText(value.description);
        ((TextView) findViewById(R.id.temperature)).setText(value.temp);
        ((TextView) findViewById(R.id.minTemp)).setText(value.tempMin);
        ((TextView) findViewById(R.id.maxTemp)).setText(value.tempMax);
        ((TextView) findViewById(R.id.sunrise)).setText(value.sunrise);
        ((TextView) findViewById(R.id.sunset)).setText(value.sunset);
        ((TextView) findViewById(R.id.wind)).setText(value.speed);
        ((TextView) findViewById(R.id.pressure)).setText(value.pressure);
        ((TextView) findViewById(R.id.humidity)).setText(value.humidity);
    }
}