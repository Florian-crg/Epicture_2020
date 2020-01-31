package com.example.epicture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
//    private OkHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        fetchData();
    }
//    private void fetchData() {
//        httpClient = new OkHttpClient.Builder().build();
//    }

}
