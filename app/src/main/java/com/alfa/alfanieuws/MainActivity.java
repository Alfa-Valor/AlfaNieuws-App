package com.alfa.alfanieuws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.alfa.alfanieuws.Helpers.DbBitmapUtility;
import com.alfa.alfanieuws.Helpers.NewsLoaderHelper;
import com.alfa.alfanieuws.InfoConstructors.NewsInfo;
import com.alfa.alfanieuws.Adapters.NewsListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the NewsLoaderHelper we are gonna use to get the news and add new news news_items
        NewsLoaderHelper newsLoaderHelper = new NewsLoaderHelper(this);


        // NOTE: THIS IS FOR SAMPLE DATA PURELY THIS HAS NO OTHER FUNCTIONAL USE....
        // THIS WILL BE REMOVED AFTER WE START GETTING NEWS INFO FROM THE WEB API
        // CHANGE THIS BOOLEAN TO TRUE IF YOU WANT TO ADD SOME SAMPLE DATA, MAKE SURE TO TURN IT OFF AFTER YOU HAVE LAUNCHED THE APP THEN RELOAD
        boolean adding = false;
        if(adding == true) {
            DbBitmapUtility bitmapUtility = new DbBitmapUtility();
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.logo);
            byte[] photo = bitmapUtility.getBytes(bitmap);
            newsLoaderHelper.add_news_message("Yeet", "We have some yeetings", "10-10-2020", photo);
        }

        // Get all the news messages currently stored and load them....
        // This is based on NewsInfo objects put into one array for ease of access
        ArrayList<NewsInfo> newsInfoArray = newsLoaderHelper.get_news_messages();

        // Load all the news based on this array
        load_news(newsInfoArray);
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

}