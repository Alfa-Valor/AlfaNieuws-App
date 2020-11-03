package com.alfa.alfanieuws.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alfa.alfanieuws.Helpers.DbBitmapUtility;
import com.alfa.alfanieuws.InfoConstructors.NewsInfo;
import com.alfa.alfanieuws.R;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    // We set a subclass called "NewsViewHolder" because recyclerview adapters are special and annoying as f-
    public class NewsViewHolder extends RecyclerView.ViewHolder {
        // All the fields
        protected TextView news_title;
        protected TextView news_text;
        protected TextView news_date;
        protected ImageView news_image;

        // Constructor sets all the elements used later
        public NewsViewHolder(View v){
            super(v);
            news_title =  v.findViewById(R.id.newsTitle);
            news_text =  v.findViewById(R.id.newsTitle);
            news_date =  v.findViewById(R.id.newsDate);
            news_image =  v.findViewById(R.id.newsImage);
        }
    }


    private ArrayList<NewsInfo> mData;
    private LayoutInflater mInflater;

    // CONSTRUCTOR
    public NewsListAdapter(Context context, ArrayList<NewsInfo> data) {
        // Get layoutinflater from given context
        this.mInflater = LayoutInflater.from(context);

        // Set data used later to populate the cardview
        this.mData = data;
    }

    @NonNull
    @Override
    public NewsListAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout and return the NewsViewHolder set earlier...
        View view = mInflater.inflate(R.layout.news_items, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        // When the ViewHolder gets bound we set all of our texts based on our current positon and use newsInfo for ease of access
        DbBitmapUtility bitmapUtility = new DbBitmapUtility();

        // Set texts and use our ArrayList (mdData) to populate newsInfo based on the position in the list
        NewsInfo newsInfo = mData.get(position);

        // Set the information
        holder.news_title.setText(newsInfo.getNews_title());
        holder.news_date.setText(newsInfo.getNews_date());
        holder.news_image.setImageBitmap(bitmapUtility.getImage(newsInfo.getNews_picture()));
    }



    // Required function, might never actually be used.. lol
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
