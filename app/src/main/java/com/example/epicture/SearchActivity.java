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

public class SearchActivity extends AppCompatActivity {
    private ImageButton home_btn;
    private ImageButton favorites_btn;
    private ImageButton search_btn;
    private ImageButton profil_btn;
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
//        mSearchView.OnQueryTextListener(text);
    }


}
