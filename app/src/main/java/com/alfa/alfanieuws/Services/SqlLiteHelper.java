package com.alfa.alfanieuws.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteHelper extends SQLiteOpenHelper {
    // Dont touch this number; meh sorry ... (Christian)
    private static final int DATABASE_VERSION = 9;

    public static final String DATABASE_NAME = "alfa_nieuws";


    public static final String NEWS_TABLE_NAME = "news";
    public static final String NEWS_COLUMN_ID = "_id";
    public static final String NEWS_COLUMN_TITLE = "title";
    public static final String NEWS_COLUMN_IMAGE = "image";
    public static final String NEWS_COLUMN_TEXT = "text";
    public static final String NEWS_COLUMN_POSTED_ON = "posted_on";
    public static final String NEWS_COLUMN_LAST_EDITED = "last_edited";
    public static final String NEWS_COLUMN_ACTIVE = "active";

    public static final String RESPONSE_TABLE_NAME = "responses";
    public static final String RESPONSE_COLUMN_ID = "_id";
    public static final String NEWS_COLUMN__ID = "_newsId";
    public static final String RESPONSE_COLUMN_NAME = "name";
    public static final String RESPONSE_COLUMN_TEXT = "text";
    public static final String RESPONSE_COLUMN_ACTIVE = "active";

    public SqlLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NEWS_TABLE_NAME + " (" +
                NEWS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NEWS_COLUMN_TITLE + " TEXT," +
                NEWS_COLUMN_IMAGE + " TEXT," +
                NEWS_COLUMN_TEXT + " TEXT," +
                NEWS_COLUMN_POSTED_ON + " TEXT," +
                NEWS_COLUMN_LAST_EDITED + " TEXT," +
                NEWS_COLUMN_ACTIVE + " INTEGER" +")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + RESPONSE_TABLE_NAME + " (" +
                RESPONSE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NEWS_COLUMN__ID + " TEXT," +
                RESPONSE_COLUMN_NAME + " TEXT," +
                RESPONSE_COLUMN_TEXT + " TEXT," +
                RESPONSE_COLUMN_ACTIVE + " INTEGER" +")");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NEWS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RESPONSE_TABLE_NAME);
        onCreate(db);
    }

    public void deleteTable(String tableName) {
        if(tableName != null){
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL("DELETE FROM " + tableName);
                db.close();
            } catch (Exception e) {
                System.out.println("Something went wrong using the delete SQL functionality");
            }
        }
        System.out.println("I need you to enter a table name as STRING and the where clausule as STRING");

    }
}