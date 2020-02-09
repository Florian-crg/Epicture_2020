package com.example.epicture;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;


public class PhotosActivity extends AppCompatActivity {

    // Local variable
    private ImageButton home_btn;
    private ImageButton favorites_btn;
    private ImageButton search_btn;
    private ImageButton profil_btn;
    // Constante variable
    private static final String TAG = "PhotoActivity";
    private static final String clientId = "bb0c749c6403fd2";

    // Private shared variable
    private static  List<Photo> mPhotos;
    private static JSONArray mItems;
    private static String mAccessToken;
    private static String userID;

    // Shared variable
    public static String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        this.home_btn = findViewById(R.id.home_button);
        this.favorites_btn = findViewById(R.id.favorites_button);
        this.search_btn = findViewById(R.id.search_button);
        this.profil_btn = findViewById(R.id.profil_button);
//        HttpHandler.fetchData();
//        build();
//        activity = this;

//        HttpHandler.activity = this;
        final HttpHandler httpHandler = new HttpHandler(PhotosActivity.this, this);

        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        String[] filters=getResources().getStringArray(R.array.filters);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner,R.id.text, filters);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedItem = parent.getItemAtPosition(position).toString();
                Filters();
                httpHandler.fetchData();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                Log.d("TAG", "Error");
            }
        });

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
    }

    public static void Filters() {
        HttpHandler.base = "gallery/";
        HttpHandler.page = "0.json";
        HttpHandler.showV = "?showViral=false";
        if(selectedItem != null) {
            if (selectedItem.equals("Most Viral")) {
                HttpHandler.section = "hot/";
                HttpHandler.sort = "viral/";
                HttpHandler.showV = "?showViral=true";
            } else if (selectedItem.equals("Newest")) {
                HttpHandler.section = "top/";
                HttpHandler.sort = "time/";
            } else if (selectedItem.equals("Rising")) {
                HttpHandler.section = "user/";
                HttpHandler.sort = "rising/";
            } else {
                Log.d(TAG, "Might be a problem");
            }
        }
    }
 }




