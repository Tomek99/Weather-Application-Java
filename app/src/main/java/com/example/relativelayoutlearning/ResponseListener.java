package com.example.relativelayoutlearning;

import com.google.gson.Gson;

public interface ResponseListener {

    void onError(String message);

    void onResponse(String json, Gson gson);
}
