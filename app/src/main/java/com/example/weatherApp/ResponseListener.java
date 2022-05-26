package com.example.relativelayoutlearning;

public interface ResponseListener {

    void onError(String message);

    void onResponse(DataWarehouse dataWarehouse);
}
