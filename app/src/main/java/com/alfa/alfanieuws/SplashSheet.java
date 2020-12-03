package com.alfa.alfanieuws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Space;

import com.alfa.alfanieuws.Interface.ServerCallback;
import com.alfa.alfanieuws.MainActivity;
import com.alfa.alfanieuws.R;
import com.alfa.alfanieuws.Services.SqlLiteHelper;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class SplashSheet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sheet);
        final Context context = this;

        // Call the volley request and wait for the response to load the news into a list
        News news = new News(this);
        if(Extras.isConnected(this)) {
            news.truncateTables(this);
            news.fetch_news(new ServerCallback() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            );
        } else {
            // We sleep before loading the data offline for user satisfaction (NOTE: WE DONT DO THIS WHEN THERE IS INTERNET)
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}