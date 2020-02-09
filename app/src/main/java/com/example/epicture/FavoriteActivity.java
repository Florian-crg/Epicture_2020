package com.example.epicture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;


public class FavoriteActivity extends AppCompatActivity {
    private ImageButton home_btn;
    private ImageButton favorites_btn;
    private ImageButton search_btn;
    private ImageButton profil_btn;

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

    public static void Filters() {
        HttpHandler.base = "account/";
        HttpHandler.section = LoginActivity.account_username;
        HttpHandler.sort = "/gallery_favorites";
        HttpHandler.page = "";
        HttpHandler.showV = "";
    }
}