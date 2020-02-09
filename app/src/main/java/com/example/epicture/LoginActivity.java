package com.example.epicture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private MaterialButton login_button;
    private static String clientId = "bb0c749c6403fd2";
    private Button continue_button;
    private TextView mTextView;


    private static final String TAG = "HttpHandler";
    public static String access_token;
    public static String account_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.continue_button = findViewById(R.id.continue_button);
        this.login_button = findViewById(R.id.login_button);
        this.mTextView = findViewById(R.id.text_login);

        continue_button.setVisibility(View.GONE);


        String uri = getIntent().getDataString();
        access_token = "";
        String refresh_token = "";
        account_username = "";
        String account_id = "";

        if (uri != null){
            continue_button.setVisibility(View.VISIBLE);
            login_button.setText("CONTINUE");
            String mainPart = uri.toString().split("#")[1];
            String[] arguments = mainPart.split("&");
            String argument0 = arguments[0];
            String argument3 = arguments[3];
            String argument4 = arguments[4];

            access_token = argument0.split("=")[1];
            refresh_token = argument3.split("=")[1];
            account_username = argument4.split("=")[1];
            mTextView.setText("You are logged as " + account_username);
            continue_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent next_activity = new Intent(getApplicationContext(), PhotosActivity.class);
                    startActivity(next_activity);
                }
            });

      }
   }

     public void login(View view) {

         Uri login_Uri = Uri.parse("https://api.imgur.com/oauth2/authorize?client_id=" + clientId + "&response_type=" + "token");
        Intent connectIntent = new Intent(Intent.ACTION_VIEW, login_Uri);
// Verify it resolves
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(connectIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

// Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(connectIntent);
            finish();
        }
    }
}