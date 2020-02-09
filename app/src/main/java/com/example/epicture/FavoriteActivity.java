package com.example.epicture;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.epicture.HttpHandler.*;
import static com.example.epicture.LoginActivity.*;

public class FavoriteActivity extends AppCompatActivity {
    private MaterialButton home_btn;
    private MaterialButton favorites_btn;
    private MaterialButton search_btn;
    private MaterialButton profil_btn;
    private static JSONArray mItems;

    public static void callBackPhoto(JSONArray items)
    {
        mItems = items;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        this.home_btn = findViewById(R.id.home_button);
        this.favorites_btn = findViewById(R.id.favorites_button);
        this.search_btn = findViewById(R.id.search_button);
        this.profil_btn = findViewById(R.id.profil_button);
        HttpHandler httpHandler = new HttpHandler(FavoriteActivity.this, this);

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
                Intent next_activity = new Intent(getApplicationContext(), FavoriteActivity.class);
                finish();

                startActivity(next_activity);
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), SearchActivity.class);
                finish();
                startActivity(next_activity);
            }
        });
        profil_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), ProfileActivity.class);
                finish();
                startActivity(next_activity);
            }
        });
        Filters();
        httpHandler.fetchData();
    }


    public void build () {
        final List<Photo> photos = new ArrayList<Photo>();
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
                photos.add(photo);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        render(photos);
                    }
                });
            }

        } catch (Exception e) {
            Log.e("JSONerr" , "Something went wrong.");
        }
    }

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

        RecyclerView.Adapter<FavoriteActivity.PhotoVH> adapter = new RecyclerView.Adapter<FavoriteActivity.PhotoVH>() {
            @NonNull
            @Override
            public FavoriteActivity.PhotoVH onCreateViewHolder(ViewGroup parent, int viewType) {
                FavoriteActivity.PhotoVH vh = new FavoriteActivity.PhotoVH(getLayoutInflater().inflate(R.layout.item, null));
                vh.photo = (ImageView) vh.itemView.findViewById(R.id.photo);
                vh.title = (TextView) vh.itemView.findViewById(R.id.title);
                return vh;
            }

            @Override
            public void onBindViewHolder(FavoriteActivity.PhotoVH holder, int position) {
                Picasso.with(FavoriteActivity.this).load("https://i.imgur.com/" +
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

    public static void Filters() {

        base = "account/";
        section = account_username;
        sort = "/gallery_favorites";
        page = "";
        showV = "";
        mUrl = "";
    }
}