package com.example.epicture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
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
    private static final int PICK_IMAGE_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    public static String account_username;
    private static OkHttpClient httpClient;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.login_button = findViewById(R.id.login_button);
        this.choose_image = findViewById(R.id.choose_image);
        this.send_image = findViewById(R.id.send_image);
        this.continue_button = findViewById(R.id.con_button);
        this.test = findViewById(R.id.tests);
        this.Image = findViewById(R.id.image_taken);

        String uri = getIntent().getDataString();
        String access_token = "";
        String refresh_token = "";
        account_username = "";
        String account_id = "";
        test.setText("Login");
        String n = "";

        if (uri != null){
            String mainPart = uri.toString().split("#")[1];
            String[] arguments = mainPart.split("&");
            String argument0 = arguments[0];
            String argument3 = arguments[3];
            String argument4 = arguments[4];
            String argument5 = arguments[5];

            access_token = argument0.split("=")[1];
            refresh_token = argument3.split("=")[1];
            account_username = argument4.split("=")[1];
            account_id = argument5.split("=")[1];

            test.setText(account_username);
            continue_button.setText("Continue");

            continue_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent next_activity = new Intent(getApplicationContext(), PhotosActivity.class);
                    startActivity(next_activity);
                }
            });
            choose_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                            requestPermissions(permissions, PERMISSION_CODE);
                        }
                        else {
                            pickImageFromGallery();
                        }
                    }

                }
            });
            final String finalAccess_token = access_token;
            send_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    MediaType mediaType = MediaType.parse("text/plain");
                    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("image", "/storage/emulated/0/AnimeX/AnimeX_1022141.jpg")
                            .build();
                    Request request = new Request.Builder()
                            .url("https://api.imgur.com/3/image")
                            .method("POST", body)
                            .addHeader("Authorization", "Bearer " + finalAccess_token)
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }

    public static void Avatar() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/account/"+account_username)
                .method("GET", null)
                .addHeader("Authorization", "Client-ID "+clientId)
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(this, "Permission denied..!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            Image.setImageURI(data.getData());
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