package com.example.epicture;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpHandler {
    private static final String TAG = "HttpHandler";
    private static String clientId = "bb0c749c6403fd2";
    private static OkHttpClient httpClient;
    private static String mAccessToken;

    // URL BUILDER VARIABLES
    public static String section = "hot/";
    public static String sort = "viral/";
    public static String page;
    public static String showV;
    public static String mUrl;

    public static void fetchData() {
        httpClient = new OkHttpClient.Builder().build();
        PhotosActivity.Filters();
        Log.d("TAG", "0  " + sort);
        mUrl = "https://api.imgur.com/3/gallery/" + section + sort;
        Log.d("TAG", "Sort: " + sort + ": URl is" + mUrl);
        Request request = new Request.Builder()
                .url(mUrl + "0.json" + showV)
                .addHeader("Authorization", "Client-ID " + clientId)
                .header("User-Agent", "epicture")
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "An error has occurred " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject data = new JSONObject(response.body().string());
                    JSONArray items = data.getJSONArray("data");
                    final List<Photo> photos = new ArrayList<Photo>();
                    PhotosActivity.callBackPhoto(photos, items);
                } catch (Exception e) {
                    Log.e("JSONerr", "Something went wrong.");
                }
            }
        });
    }

    public static void getLoginData(String accessToken) {
        mAccessToken = accessToken;
    }
}
