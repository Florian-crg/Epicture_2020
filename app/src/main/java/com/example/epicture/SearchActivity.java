package com.example.epicture;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SearchActivity extends AppCompatActivity {
    private MaterialButton home_btn;
    private MaterialButton favorites_btn;
    private MaterialButton search_btn;
    private MaterialButton profil_btn;
    private SearchView mSearchView;
    private TextView mTextView;

    private TextWatcher text = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        mSearchView = (SearchView) findViewById(R.id.search);
        mTextView = (TextView) findViewById(R.id.test);
        Log.d("TAG", "ok");
        text = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("TAG", "ok");
                // FIlters();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("TAG", mSearchView.getQuery().toString());
                mTextView.setText(mSearchView.getQuery().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("TAG", "ok");

            }
        };

        this.home_btn = findViewById(R.id.home_button);
        this.favorites_btn = findViewById(R.id.favorites_button);
        this.search_btn = findViewById(R.id.search_button);
        this.profil_btn = findViewById(R.id.profil_button);

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), PhotosActivity.class);
                startActivity(next_activity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        favorites_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(next_activity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(next_activity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        profil_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_activity = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(next_activity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
//        mSearchView.OnQueryTextListener(text);
    }


}
