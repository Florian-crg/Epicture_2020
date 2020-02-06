package com.example.epicture;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHandler {
    private static final String TAG = "HttpHandler";
    private static String clientId = "bb0c749c6403fd2";

    private static OkHttpClient httpClient;

    public static void fetchData() {
        httpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/gallery/user/rising/0.json")
                .addHeader("Authorization","Client-ID " + clientId )
                .header("User-Agent","epicture")
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
                    PhotosActivity.callBack(photos, items);
                }
                catch (Exception e) {
                    Log.e("JSONerr" , "Something went wrong.");
                }
            }
        });
    }

   /*public static void putFavorites() {
        httpClient = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).build();
        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/image/{{imageHash}}/favorite")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + "accessToken" )
                .build();
    }

    public static void FectchDataFav() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .build();
        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/album/{{albumHash}}/favorite")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + "accessToken" )
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
