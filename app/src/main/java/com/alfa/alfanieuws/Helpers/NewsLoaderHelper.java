package com.alfa.alfanieuws.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alfa.alfanieuws.InfoConstructors.NewsInfo;
import com.alfa.alfanieuws.Services.SqlLiteHelper;

import java.util.ArrayList;

public class NewsLoaderHelper extends SqlLiteHelper {


    public NewsLoaderHelper(Context context) {
        super(context);
    }


    // Add a new news message to the database
    public void add_news_message(String title, String newsText, String date, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Put all info into content values to insert later
        cv.put(this.NEWS_COLUMN_TITLE, title);
        cv.put(this.NEWS_COLUMN_TEXT, newsText);
        cv.put(this.NEWS_COLUMN_POSTED_ON, date);
        cv.put(this.NEWS_COLUMN_IMAGE, image);
        // Insert the data into the news_table
        db.insert(this.NEWS_TABLE_NAME, null, cv);
    }

    // Fetches all current news messages from the database
    public ArrayList<NewsInfo> get_news_messages() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<NewsInfo> newsInfoArray = new ArrayList<NewsInfo>();

        // Projection explained below...
        String[] projection = {
                this.NEWS_COLUMN_ID,
                this.NEWS_COLUMN_TITLE,
                this.NEWS_COLUMN_TEXT,
                this.NEWS_COLUMN_POSTED_ON,
                this.NEWS_COLUMN_IMAGE,
        };

        String selection = null;

        String[] selectionArgs = {};

        Cursor cursor = db.query(
                this.NEWS_TABLE_NAME,   // The table to query
                projection,             // The columns to return
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,           // Don't group the rows
                null,            // Don't filter by row groups
                null            // Don't sort
        );

        // If data has been found
        if(cursor.moveToFirst()) {
            do {
                // Get all the indexes of the different columns
                Integer news_id_index = cursor.getColumnIndex(this.NEWS_COLUMN_ID);
                Integer news_title_index  = cursor.getColumnIndex(this.NEWS_COLUMN_TITLE);
                Integer news_text_index  = cursor.getColumnIndex(this.NEWS_COLUMN_TEXT);
                Integer news_date_index  = cursor.getColumnIndex(this.NEWS_COLUMN_POSTED_ON);
                Integer news_image_index  = cursor.getColumnIndex(this.NEWS_COLUMN_IMAGE);

                // Set the data based on the given index number
                int news_id = cursor.getInt(news_id_index);
                String news_title = cursor.getString(news_title_index);
                String news_text = cursor.getString(news_text_index);
                String news_date = cursor.getString(news_date_index);

                // We use a blob for images
                byte[] news_image = cursor.getBlob(news_image_index);

                // Set new NewsInfo object that is going to store all of our data for later.
                NewsInfo newsInfo = new NewsInfo(news_id, news_title, news_text, news_date, news_image);

                // Add it to a big newsInfoArray with all the news messages in one place
                newsInfoArray.add(newsInfo);
            } while(cursor.moveToNext());
        }

        // Return the newsInfoArray
        return newsInfoArray;
    }

}
