package com.example.weatherApp;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;
import android.widget.*;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import okhttp3.*;


public class MainActivity extends AppCompatActivity {

    Button btn1;
    ImageButton btn2;
    EditText getCity;

    CurrentLocation currentLocation;

    OkHttpClient client = new OkHttpClient();

    final WeatherDataSerivce weatherDataSerivce = new WeatherDataSerivce(MainActivity.this,
            client);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GPS(MainActivity.this);

        getCity = (EditText) findViewById(R.id.enterLocation);
        btn1 = (Button) findViewById(R.id.searchLocation);

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
                        new UpdateElements(MainActivity.this, data);
                    }
                });
                getCity.setText("");
            }
        });

        btn2 = (ImageButton) findViewById(R.id.btn2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLocation = new CurrentLocation(MainActivity.this, weatherDataSerivce);
            }
        });
    }
}