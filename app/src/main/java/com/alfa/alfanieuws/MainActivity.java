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

//import com.alfa.alfanieuws.Helpers.CustomVolleyRequest;
import com.alfa.alfanieuws.Helpers.DbBitmapUtility;
import com.alfa.alfanieuws.Helpers.NewsLoaderHelper;
import com.alfa.alfanieuws.InfoConstructors.NewsInfo;
import com.alfa.alfanieuws.Adapters.NewsListAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context = this;
    private NetworkImageView imageView;
    private ImageLoader imageLoader;
    FloatingActionButton iconBtn;

    //the URL having the json data
    final static String JSON_URL = "https://simplifiedcoding.net/demos/view-flipper/heroes.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // icon for the comments (Could be removed later)
        iconBtn = findViewById(R.id.iconBtn);

        if (Extras.isConnected(this)) {
            loadNewsFromDatabase();
        } else {
            loadNewsFromDatabase();
            Toast.makeText(getApplicationContext(), "No internet connection, we will show offline data", Toast.LENGTH_SHORT).show();
        }
    }




    // Loading news from database
    private void loadNewsFromDatabase() {
        //initiating the utilities
        final DbBitmapUtility bitmapUtility = new DbBitmapUtility();
        //initiating the newsloader
        final NewsLoaderHelper newsLoaderHelper = new NewsLoaderHelper(context);
        if (Extras.isConnected(context)) {
            Log.d("NEWS", "Loading news from JSON > Pushing it to the database");

            // creating a string request to send the request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //try catch to check if our response is indeed a JSON format document
                            try {
                                //getting the whole JSON object from the url
                                JSONObject obj = new JSONObject(response);

                                //json is like a array, we have a array called news in there
                                //so here we are getting that JSON array
                                JSONArray newsArray = obj.getJSONArray("heroes");

                                //now lets loop through all the elements of the json array
                                for (int i = 0; i < newsArray.length(); i++) {

                                    //getting the JSON object of the particular index inside the array
                                    JSONObject newsObject = newsArray.getJSONObject(i);

                                    //getting the url image to bitmap
                                    //getting the bytes from the image with the bitmapUtility

                                    //TODO HANDLING BITMAPS FROM IMAGES

                                    //adding the news article to the database.
//                                      newsLoaderHelper.add_news_message(newsObject.getString("name"), "text", "allwaysss", photo);
//                                    newsLoaderHelper.add_news_message(newsObject.getString("title"), newsObject.getString("text"), newsObject.getString("data"), image);
                                }
                                // Get all the news messages currently stored and load them....
                                // This is based on NewsInfo objects put into one array for ease of access
                                ArrayList<NewsInfo> newsInfoArray = newsLoaderHelper.get_news_messages();

                                // Load all the news based on this array
                                load_news(newsInfoArray);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occur
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);
        } else {
            // Get all the news messages currently stored and load them....
            // This is based on NewsInfo objects put into one array for ease of access
            ArrayList<NewsInfo> newsInfoArray = newsLoaderHelper.get_news_messages();

            // Load all the news based on this array
            load_news(newsInfoArray);
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

    // JUST FOR WORKING WITH THE COMMENTS -> Quinn needs to add this to every news post in order to get the correct comments and add them correct.
    public void iconClicked(View v){
        Intent myIntent = new Intent(context, CommentActivity.class);
        myIntent.putExtra("post_id",  "1");
        startActivity(myIntent);
    }
}