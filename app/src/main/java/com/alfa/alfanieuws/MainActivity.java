package com.alfa.alfanieuws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.alfa.alfanieuws.Helpers.NewsLoaderHelper;
import com.alfa.alfanieuws.InfoConstructors.NewsInfo;
import com.alfa.alfanieuws.Adapters.NewsListAdapter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetch_news_list();
    }

    private void fetch_news_list() {

        NewsLoaderHelper newsLoaderHelper = new NewsLoaderHelper(context);

        // Get all the news messages currently stored and load them....
        // This is based on NewsInfo objects put into one array for ease of access
        // We put this here because even if no new articles are added we still load the current news
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