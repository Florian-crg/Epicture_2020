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
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    public static final int REQUEST_CODE= 1;
    private ImageButton login_button;
    private static String clientId = "bb0c749c6403fd2";
    private TextView test;
    private Button continue_button;
    private Button choose_image;
    private Button send_image;
    private  String tag = null;
    private ImageView Image;


    private static final String TAG = "HttpHandler";
    public static String access_token;
    public static String account_username;
    private static OkHttpClient httpClient;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button = findViewById(R.id.login_button);
        ObjectAnimator.ofFloat(button, "alpha", 1f).setDuration(3000).start();
        this.continue_button = findViewById(R.id.con_button);
        this.test = findViewById(R.id.tests);

        String uri = getIntent().getDataString();
        access_token = "";
        String refresh_token = "";
        account_username = "";
        String account_id = "";
        test.setText("Login");

        if (uri != null){
            String mainPart = uri.toString().split("#")[1];
            String[] arguments = mainPart.split("&");
            String argument0 = arguments[0];
            String argument3 = arguments[3];
            String argument4 = arguments[4];

            access_token = argument0.split("=")[1];
            refresh_token = argument3.split("=")[1];
            account_username = argument4.split("=")[1];

            test.setText(account_username);
            continue_button.setText("Continue");

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