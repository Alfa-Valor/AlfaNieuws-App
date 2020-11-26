package com.alfa.alfanieuws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alfa.alfanieuws.Helpers.DbBitmapUtility;
import com.alfa.alfanieuws.Helpers.NewsLoaderHelper;
import com.alfa.alfanieuws.InfoConstructors.NewsInfo;
import com.alfa.alfanieuws.Adapters.NewsListAdapter;
import com.alfa.alfanieuws.Services.SqlLiteHelper;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context = this;
    FloatingActionButton iconBtn;
    SqlLiteHelper db;

    //the URL having the json data
    final static String JSON_URL = "https://simplifiedcoding.net/demos/view-flipper/heroes.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CHANGE THIS BOOLEAN TO TRUE IF YOU WISH TO DROP BOTH TABLES IN ORDER TO LOAD WEB API DATA,
        // WITHOUT PUTTING IT BACK TO FALSE THE DATABASE KEEPS DROPPING AND BUILDING AGAIN WITH NEW FRESH DATA.
        boolean adding = false;
        if(adding == true) {
            db = new SqlLiteHelper(context);
            db.deleteTable(db.NEWS_TABLE_NAME);
            db.deleteTable(db.RESPONSE_TABLE_NAME);

        }
        // icon for the comments (Could be removed later)
        iconBtn = findViewById(R.id.iconBtn);

        // Load all the news
        loadNews();
    }

    // Loading news from database
    // NOTITIE: It may take a bit to load all the new news messages because where we get the sample data from is a bit slow....
    private void loadNews() {
        //initiating the newsloader
        final NewsLoaderHelper newsLoaderHelper = new NewsLoaderHelper(context);
        if (Extras.isConnected(context)) {
            Log.d("NEWS", "Loading news from JSON > Pushing it to the database");

            // Creating a string request to send the request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            // Run a new thread for fetching all the data later, i.e. the images (Future proof also)
                            Thread json_thread = new Thread(new Runnable() {
                                @Override
                                public void run(){
                                    try { // Simple try catch
                                        // Initiating the utilities
                                        final DbBitmapUtility bitmapUtility = new DbBitmapUtility();

                                        // Getting the whole JSON object from the url
                                        JSONObject obj = new JSONObject(response);

                                        // JSON is like a array, we have a array called news in there
                                        // So here we are getting that JSON array
                                        JSONArray newsArray = obj.getJSONArray("heroes");
                                        //now lets loop through all the elements of the json array
                                        for (int i = 0; i < newsArray.length(); i++) {
                                            // Getting the JSON object of the particular index inside the array
                                            JSONObject newsObject = newsArray.getJSONObject(i);

                                            URL imageUri = new URL(newsObject.getString("imageurl"));
                                            Bitmap mBitmap = Picasso.get().load(String.valueOf(imageUri)).get();
                                             byte[] byte_array = bitmapUtility.getBytes(mBitmap);
                                             // Adding the news article to the database. (Uncomment this to get sample data)
                                             newsLoaderHelper.add_news_message(newsObject.getString("name"), "text", "2020/11/24", byte_array);
                                        }

                                    } catch (IOException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            json_thread.start();

                            // Get all the news messages currently stored and load them....
                            // This is based on NewsInfo objects put into one array for ease of access
                            // We put this here because even if no new articles are added we still load the current news
                            ArrayList<NewsInfo> newsInfoArray = newsLoaderHelper.get_news_messages();

                            // Load all the news based on this array
                            load_news(newsInfoArray);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Displaying the error in toast if occur
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            // Creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            // Adding the string request to request queue
            requestQueue.add(stringRequest);
        } else {
            // Get all the news messages currently stored and load them....
            // This is based on NewsInfo objects put into one array for ease of access
            ArrayList<NewsInfo> newsInfoArray = newsLoaderHelper.get_news_messages();

            // Load all the news based on this array
            load_news(newsInfoArray);

            // Display message
            Toast.makeText(getApplicationContext(), "No internet connection, we will show offline data", Toast.LENGTH_SHORT).show();
        }
    }

    // Loads all the news given
    public void load_news(ArrayList<NewsInfo> newsInfoArray) {

        // Get and set the recycler view
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // Call the layout manager and set it
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Set the adapter to our custom NewsListAdapter for recyclerview(s)
        mAdapter = new NewsListAdapter(this, newsInfoArray);
        recyclerView.setAdapter(mAdapter);
    }

    // JUST FOR WORKING WITH THE COMMENTS -> Quinn (No you, love quinn) needs to add this to every news post in order to get the correct comments and add them correct.
    public void iconClicked(View v){
        Intent myIntent = new Intent(context, CommentActivity.class);
        myIntent.putExtra("post_id",  "1");
        startActivity(myIntent);
    }
}