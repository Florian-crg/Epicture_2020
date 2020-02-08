package com.example.epicture;

import android.app.Activity;
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

import static com.example.epicture.LoginActivity.account_username;


public class HttpHandler {
    private static final String TAG = "HttpHandler";
    private static String clientId = "bb0c749c6403fd2";
    private static OkHttpClient httpClient;
    private static String mAccessToken;

    public static Activity activity;

    // URL BUILDER VARIABLES
    public static String base = "gallery/";
    public static String section = "hot/";
    public static String sort = "viral/";
    public static String page = "0.json";
    public static String showV = "";
    public static String mUrl = "";


    public static void fetchData() {
        final String currentAct = activity.toString();
        httpClient = new OkHttpClient.Builder().build();
        if (currentAct.contains("PhotosActivity")) {
            PhotosActivity.Filters();
        } else if (currentAct.contains("FavoriteActivity")) {
            FavoriteActivity.Filters();
        }
        mUrl = "https://api.imgur.com/3/" + base + section + sort + page + showV;
        Log.d("TAG", "Sort: " + sort + ": URl is" + mUrl);
        Request request = new Request.Builder()
                .url(mUrl)
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
                    if (currentAct.contains("PhotosActivity")) {
                        PhotosActivity.callBackPhoto(items);
                    } else if (currentAct.contains("FavoriteActivity")) {
                        FavoriteActivity.callBackPhoto(items);
                    }

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
