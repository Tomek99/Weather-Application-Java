package com.example.weatherApp;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import okhttp3.*;

public class MainActivity extends AppCompatActivity {

    private EditText getCity;

    OkHttpClient client = new OkHttpClient();

    final WeatherDataSerivce weatherDataSerivce = new WeatherDataSerivce(MainActivity.this,
            client);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCity = (EditText) findViewById(R.id.enterLocation);
        Button click = (Button) findViewById(R.id.searchLocation);


        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataSerivce.getWebservice(getCity.getText().toString().trim(), new ResponseListener() {
                    @Override
                    public void onError(String message) {
                        ((TextView) findViewById(R.id.location)).setText(message);
                    }

                    @Override
                    public void onResponse(DataWarehouse data) {
                        ((TextView) findViewById(R.id.location)).setText(data.location);
                        ((TextView) findViewById(R.id.last_updated)).setText(data.lastModified);
                        ((TextView) findViewById(R.id.weatherDescription)).setText(data.description);
                        ((TextView) findViewById(R.id.temperature)).setText(data.temp);
                        ((TextView) findViewById(R.id.minTemp)).setText(data.tempMin);
                        ((TextView) findViewById(R.id.maxTemp)).setText(data.tempMax);
                        ((TextView) findViewById(R.id.sunrise)).setText(data.sunrise);
                        ((TextView) findViewById(R.id.sunset)).setText(data.sunset);
                        ((TextView) findViewById(R.id.wind)).setText(data.speed);
                        ((TextView) findViewById(R.id.pressure)).setText(data.pressure);
                        ((TextView) findViewById(R.id.humidity)).setText(data.humidity);
                    }
                });
                getCity.setText("");
            }
        });
    }
}