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

    final WeatherDataSerivce weatherDataSerivce = new WeatherDataSerivce(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCity = (EditText) findViewById(R.id.enterLocation);
        Button click = (Button) findViewById(R.id.searchLocation);



        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weatherDataSerivce.getWebservice(getCity.getText().toString());
                getCity.setText("");

                client = new OkHttpClient();
            }
        });


    }
}
