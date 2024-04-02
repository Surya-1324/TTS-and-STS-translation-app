package com.example.myapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiManager {
    private static final String API_URL = "https://localhost:8080/api/strings";

    private OkHttpClient client;
    private List<String> strings;

    public ApiManager() {
        client = new OkHttpClient();
    }

    public void fetchDataFromApi(final CallbackListener callbackListener) {
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    processData(responseData);
                    callbackListener.onSuccess(strings);
                } else {
                    Log.e("ApiManager", "Failed to get data from API.");
                    callbackListener.onFailure("Failed to get data from API.");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ApiManager", "Failed to connect to API.", e);
                callbackListener.onFailure("Failed to connect to API.");
            }
        });
    }

    private void processData(String responseData) {

        try {
            JSONObject jsonObject = new JSONObject(responseData);
            JSONArray jsonArray = jsonObject.getJSONArray("strings");


            strings = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                strings.add(jsonArray.getString(i));
            }

        } catch (JSONException e) {
            Log.e("ApiManager", "Failed to parse JSON response.", e);
        }
    }

    public interface CallbackListener {
        void onSuccess(List<String> strings);
        void onFailure(String errorMessage);
    }
}
