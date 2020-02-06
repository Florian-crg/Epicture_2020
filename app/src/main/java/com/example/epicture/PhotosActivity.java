package com.example.epicture;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;

public class PhotosActivity extends AppCompatActivity {

    private OkHttpClient httpClient;
    private static final String TAG = "PhotoActivity";
    private ImageButton home_btn;
    private ImageButton favorites_btn;
    private ImageButton search_btn;
    private ImageButton profil_btn;
    private String clientId = "bb0c749c6403fd2";
    private static String userID;
    private static  List<Photo> mPhotos;
    private static JSONArray mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        this.home_btn = findViewById(R.id.home_button);
        this.favorites_btn = findViewById(R.id.favorites_button);
        this.search_btn = findViewById(R.id.search_button);
        this.profil_btn = findViewById(R.id.profil_button);

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), PhotosActivity.class);
                finish();
                startActivity(next_activity);
            }
        });
        favorites_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), PhotosActivity.class);
                finish();

                startActivity(next_activity);
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), PhotosActivity.class);
                finish();
                startActivity(next_activity);
            }
        });
        profil_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), PhotosActivity.class);
                finish();
                startActivity(next_activity);
            }
        });
        HttpHandler.fetchData();
        build();
    }

    private void build () {
        try {
            for(int i = 0; i < mItems.length(); i++) {
                JSONObject item = mItems.getJSONObject(i);
                Photo photo = new Photo();
                if(item.getBoolean("is_album")) {
                    photo.id = item.getString("cover");
                } else {
                    photo.id = item.getString("id");
                }
                photo.title = item.getString("title");
                mPhotos.add(photo);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        render(mPhotos);
                    }
                });
            }
        } catch (Exception e) {
            Log.e("JSONerr" , "Something went wrong.");
        }
    }
/*
    private void fetchData() {
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
                    for(int i=0; i<items.length();i++) {
                        JSONObject item = items.getJSONObject(i);
                        Photo photo = new Photo();
                        if(item.getBoolean("is_album")) {
                            photo.id = item.getString("cover");
                        } else {
                            photo.id = item.getString("id");
                        }
                        photo.title = item.getString("title");
                        photos.add(photo);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                render(photos);
                            }
                        });
                    }
                }
                catch (Exception e) {
                    Log.e("JSONerr" , "Something went wrong.");
                }
            }
        });
    }*/

    private static class PhotoVH extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView title;

        public PhotoVH(View itemView) {
            super(itemView);
        }
    }

    private void render(final List<Photo> photos) {
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv_of_photos);
        rv.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter<PhotoVH> adapter = new RecyclerView.Adapter<PhotoVH>() {
            @NonNull
            @Override
            public PhotoVH onCreateViewHolder(ViewGroup parent, int viewType) {
                PhotoVH vh = new PhotoVH(getLayoutInflater().inflate(R.layout.item, null));
                vh.photo = (ImageView) vh.itemView.findViewById(R.id.photo);
                vh.title = (TextView) vh.itemView.findViewById(R.id.title);
                return vh;
            }

            @Override
            public void onBindViewHolder(PhotoVH holder, int position) {
                Picasso.with(PhotosActivity.this).load("https://i.imgur.com/" +
                        photos.get(position).id + ".jpg").into(holder.photo);
                holder.title.setText(photos.get(position).title);
            }

            @Override
            public int getItemCount() {
                return photos.size();
            }
        };

        rv.setAdapter(adapter);
    }

    public static void getUserID(String UserID) {
        Log.d("TAG", UserID);
        userID = UserID;
    }

    public static void callBack( List<Photo> photos, JSONArray items)
    {
         mPhotos = photos;
         mItems = items;
    }
}
