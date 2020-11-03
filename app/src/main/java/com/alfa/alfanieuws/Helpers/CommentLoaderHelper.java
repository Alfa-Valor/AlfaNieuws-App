package com.alfa.alfanieuws.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alfa.alfanieuws.Services.SqlLiteHelper;

public class CommentLoaderHelper extends SqlLiteHelper {
    // CONSTRUCTOR
    public CommentLoaderHelper(Context context) {
        super(context);
    }

    // addComment, connecting to the database and linking the ContentValues to the correct columns..Ω
    public void addComment(String name, String post_id, String comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(RESPONSE_COLUMN_NAME, name);
        cv.put(NEWS_COLUMN__ID, post_id);
        cv.put(RESPONSE_COLUMN_TEXT, comment);
        long result = db.insert(RESPONSE_TABLE_NAME,null, cv);
        if(result == -1){
            System.out.println("Failed to add Comment");
        }else {
            System.out.println("Comment added");
        }
    }

    // getAllComments, getting the database connection and just a query..
    public Cursor getAllComments(String post_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + RESPONSE_TABLE_NAME + " WHERE _newsId = " + post_id, null);
        return res;
    }
}