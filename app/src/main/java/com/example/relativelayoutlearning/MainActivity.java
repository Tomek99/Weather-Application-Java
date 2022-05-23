package com.example.relativelayoutlearning;


import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {


    private static String city = "Jaroslaw";
    private static final String API = "8544c10e7dc1de8a48fd3f18dd3c13ed";

    EditText getCity;
    Button click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric" + "&lang=pl&appid=" + API;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    public void onResponse (JSONObject response) {
                        try {
                            JSONObject main = response.getJSONObject("main");
                            JSONObject sys = response.getJSONObject("sys");
                            JSONObject wind = response.getJSONObject("wind");
                            JSONObject weather = response.getJSONArray("weather").getJSONObject(0);

                            String lastModified = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
                                    .format(new Date(response.getLong("dt") * 1000));



                            //main
                            Long temp = Math.round(main.getDouble("temp"));
                            String temp_min = main.getString("temp_min");
                            String temp_max = main.getString("temp_max");
                            String pressure = main.getString("pressure");
                            String humidity = main.getString("humidity");

                            //sys
                            String location = response.getString("name") + ", " + sys.getString("country");

                            long sunrise = sys.getLong("sunrise");
                            long sunset = sys.getLong("sunset");

                            //wind
                            String wind1 = wind.getString("speed");
                            //weather
                            String description = weather.getString("description");

                            ((TextView) findViewById(R.id.last_updated)).setText("Ostatnia aktualizacja: " +lastModified);
                            ((TextView) findViewById(R.id.location)).setText(location);

                            ((TextView) findViewById(R.id.weatherDescription)).setText(description);
                            ((TextView) findViewById(R.id.temperature)).setText(temp + "°C");

                            ((TextView) findViewById(R.id.minTemp)).setText("Min temp: " + temp_min + "°C");
                            ((TextView) findViewById(R.id.maxTemp)).setText("Max temp: " + temp_max + "°C");


                            ((TextView) findViewById(R.id.sunrise)).setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                    .format(new Date(sunrise * 1000)));

                            ((TextView) findViewById(R.id.sunset)).setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                    .format(new Date(sunset  * 1000)));

                            ((TextView) findViewById(R.id.wind)).setText(wind1 + " km/h");
                            ((TextView) findViewById(R.id.pressure)).setText(pressure);
                            ((TextView) findViewById(R.id.humidity)).setText(humidity + "%");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(objectRequest);


//        getCity = (EditText) findViewById(R.id.enterLocation);
//        click = (Button) findViewById(R.id.searchLocation);


//        click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                city = getCity.getText().toString();
//
//            }
//        });


    }


}