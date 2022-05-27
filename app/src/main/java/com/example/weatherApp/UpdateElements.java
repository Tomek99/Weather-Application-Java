package com.example.weatherApp;

import android.widget.TextView;

public class UpdateElements {
    public UpdateElements(MainActivity m, DataWarehouse value) {
        ((TextView) m.findViewById(R.id.location)).setText(value.location);
        ((TextView) m.findViewById(R.id.last_updated)).setText(value.lastModified);
        ((TextView) m.findViewById(R.id.weatherDescription)).setText(value.description);
        ((TextView) m.findViewById(R.id.temperature)).setText(value.temp);
        ((TextView) m.findViewById(R.id.minTemp)).setText(value.tempMin);
        ((TextView) m.findViewById(R.id.maxTemp)).setText(value.tempMax);
        ((TextView) m.findViewById(R.id.sunrise)).setText(value.sunrise);
        ((TextView) m.findViewById(R.id.sunset)).setText(value.sunset);
        ((TextView) m.findViewById(R.id.wind)).setText(value.speed);
        ((TextView) m.findViewById(R.id.pressure)).setText(value.pressure);
        ((TextView) m.findViewById(R.id.humidity)).setText(value.humidity);
    }
}
