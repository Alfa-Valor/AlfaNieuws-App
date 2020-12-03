package com.alfa.alfanieuws;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alfa.alfanieuws.Helpers.DbBitmapUtility;
import com.alfa.alfanieuws.Helpers.NewsLoaderHelper;
import com.alfa.alfanieuws.Interface.ServerCallback;
import com.alfa.alfanieuws.Services.SqlLiteHelper;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class News extends AppCompatActivity {

    //the URL having the json data
    final static String JSON_URL = "https://alfanieuws.christianletteboer.nl/articlejson";
    public Context context = this;
    public Context mContext;

    public News(Context context) {
        this.mContext = context;
        // TRUNCTUATE BOTH TABLES TO ACHIEVE FRESH DATA EACH TIME IF THERE IS INTERNET


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Loading news from database
    // NOTITIE: It may take a bit to load all the new news messages because where we get the sample data from is a bit slow....
    // ServerCallback is an interface callback
    protected void fetch_news(final ServerCallback serverCallback) {

        //initiating the newsloader
        final NewsLoaderHelper newsLoaderHelper = new NewsLoaderHelper(mContext);

        // Reinitialize database
        SqlLiteHelper database = new SqlLiteHelper(context);

        if (Extras.isConnected(mContext)) {
            Log.d("NEWS", "Loading news from JSON > Pushing it to the database");

            // Creating a string request to send the request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                    new Response.Listener < String > () {
                        @Override
                        public void onResponse(final String response) {
                            try { // Simple try catch
                                // Initiating the utilities
                                final DbBitmapUtility bitmapUtility = new DbBitmapUtility();

                                // Getting the whole JSON object from the url
                                JSONObject obj = new JSONObject(response);

                                // JSON is like a array, we have a array called news in there
                                // So here we are getting that JSON array
                                JSONArray newsArray = obj.getJSONArray("Articles");
                                //now lets loop through all the elements of the json array
                                for (int i = 0; i < newsArray.length(); i++) {
                                    // Getting the JSON object of the particular index inside the array
                                    final JSONObject newsObject = newsArray.getJSONObject(i);

                                    final Bitmap[] mBitmap = new Bitmap[1];
                                    Thread image_thread = new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                            URL imageUri = null;
                                            try {
                                                imageUri = new URL(newsObject.getString("image_path"));
                                                mBitmap[0] = Picasso.get().load(String.valueOf(imageUri)).get();

                                            } catch (JSONException | IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    image_thread.start();
                                    image_thread.join();

                                    Bitmap resized = Bitmap.createScaledBitmap(mBitmap[0], 500, 600, true);
                                    byte[] byte_array = bitmapUtility.getBytes(resized);
                                    // Adding the news article to the database. (Uncomment this to get sample data)
                                    Log.v("NEWS", String.valueOf(newsObject));
                                    newsLoaderHelper.add_news_message(newsObject.getString("name"), newsObject.getString("description"), newsObject.getString("created_at"), byte_array);
                                    serverCallback.onSuccess();
                                }
                            } catch (JSONException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Displaying the error in toast if occur
                            Log.v("NEWS", String.valueOf(error));
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            // Creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);

            // Adding the string request to request queue
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "No internet connection, we will show offline data", Toast.LENGTH_SHORT).show();
        }
    }

    public void truncateTables(Context context) {
        if(Extras.isConnected(context)) {
            SqlLiteHelper db = new SqlLiteHelper(context);
            db.truncateTable(db.NEWS_TABLE_NAME);
            db.truncateTable(db.RESPONSE_TABLE_NAME);
        }
    }
}