package com.example.epicture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    private ImageButton login_button;
    private String clientId = "bb0c749c6403fd2";
    private String secretId = "f8f8fd3262e093a9b46074ddac708309c08ca71c";
    private TextView test;
    private Button continue_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.login_button = findViewById(R.id.login_button);
        String uri = getIntent().getDataString();
        String access_token = "";
        String expire_time = "";
        String token_type = "";
        String refresh_token = "";
        String account_username = "";
        String account_id = "";
        this.test = findViewById(R.id.tests);
        test.setText("Login");
        String n = "";
        this.continue_button = findViewById(R.id.con_button);

        if (uri != null){
            String mainPart = uri.toString().split("#")[1];
            String[] arguments = mainPart.split("&");
            String argument0 = arguments[0];
            String argument1 = arguments[1];
            String argument2 = arguments[2];
            String argument3 = arguments[3];
            String argument4 = arguments[4];
            String argument5 = arguments[5];

            access_token = argument0.split("=")[1];
            expire_time = argument1.split("=")[1];
            token_type = argument2.split("=")[1];
            refresh_token = argument3.split("=")[1];
            account_username = argument4.split("=")[1];
            account_id = argument5.split("=")[1];
            test.setText("Hi "+account_username +"\nYour id is : "+account_id+"\nYour ");
            continue_button.setText("Continue");
            continue_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent next_activity = new Intent(getApplicationContext(), PhotosActivity.class);
                    startActivity(next_activity);
                }
            });

//            List valeursPOST = new ArrayList(); // Création du tableau
//            valeursPOST.add(new BasicNameValuePair("token", token.toString())); // Ajoutons des valeurs
//            valeursPOST.add(new BasicNameValuePair("token", token.toString())); // Ajoutons des valeurs
//            valeursPOST.add(new BasicNameValuePair("token", token.toString())); // Ajoutons des valeurs
//            valeursPOST.add(new BasicNameValuePair("token", token.toString())); // Ajoutons des valeurs
        }
        try {
            URL url = new URL("https://google.com");
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            String urlParameters = "=" + access_token;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            String mainPart = uri.toString().split("#")[1];
            String[] arguments = mainPart.split("&");
            String argument = arguments[0];
            String token = argument.split("=")[5];
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