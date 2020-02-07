package com.example.epicture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity  extends AppCompatActivity {

    private ImageButton home_btn;
    private ImageButton favorites_btn;
    private ImageButton search_btn;
    private ImageButton profil_btn;
    private TextView id_profile;
    private TextView name_profile;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        this.home_btn = findViewById(R.id.home_button);
        this.favorites_btn = findViewById(R.id.favorites_button);
        this.search_btn = findViewById(R.id.search_button);
        this.profil_btn = findViewById(R.id.profil_button);
        this.name_profile = findViewById(R.id.name_profile);
        this.id_profile = findViewById(R.id.id_profile);


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
        id_profile.setText(R.string.id_not_found);
        name_profile.setText(R.string.name_not_found);
    }
}
