package com.example.weatherApp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentLocation {

    FusedLocationProviderClient fusedLocationProviderClient;

    MainActivity mainActivity;

    public CurrentLocation(MainActivity mainActivity, WeatherDataSerivce weatherDataSerivce) {
        this.mainActivity = mainActivity;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mainActivity);

        if (ActivityCompat.checkSelfPermission(mainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation(new ResponseLocationName() {
                @Override
                public void onResponseLocation(String location) {
                    weatherDataSerivce.getWebservice(location, new ResponseListener() {
                        @Override
                        public void onError(String message) {

                        }
                        @Override
                        public void onResponse(DataWarehouse data) {
                            setValues(data);
                        }
                    });
                }
            });
        } else {
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44
            );
        }
    }

    private void getLocation(ResponseLocationName responseLocationName) {
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Location> task) {
                Location location = task.getResult();

                if (location != null) {
                    Geocoder geocoder = new Geocoder(mainActivity, Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);
                        responseLocationName.onResponseLocation(addresses.get(0).getLocality());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setValues(DataWarehouse value) {
        ((TextView) mainActivity.findViewById(R.id.location)).setText(value.location);
        ((TextView) mainActivity.findViewById(R.id.last_updated)).setText(value.lastModified);
        ((TextView) mainActivity.findViewById(R.id.weatherDescription)).setText(value.description);
        ((TextView) mainActivity.findViewById(R.id.temperature)).setText(value.temp);
        ((TextView) mainActivity.findViewById(R.id.minTemp)).setText(value.tempMin);
        ((TextView) mainActivity.findViewById(R.id.maxTemp)).setText(value.tempMax);
        ((TextView) mainActivity.findViewById(R.id.sunrise)).setText(value.sunrise);
        ((TextView) mainActivity.findViewById(R.id.sunset)).setText(value.sunset);
        ((TextView) mainActivity.findViewById(R.id.wind)).setText(value.speed);
        ((TextView) mainActivity.findViewById(R.id.pressure)).setText(value.pressure);
        ((TextView) mainActivity.findViewById(R.id.humidity)).setText(value.humidity);
    }
}
