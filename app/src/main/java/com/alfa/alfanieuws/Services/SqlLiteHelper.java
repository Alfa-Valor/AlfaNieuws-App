package com.alfa.alfanieuws.Services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteHelper extends SQLiteOpenHelper {
    // Dont touch this number
    private static final int DATABASE_VERSION = 2;

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
    public static final String RESPONSE_COLUMN_FIRST_NAME = "first_name";
    public static final String RESPONSE_COLUMN_PREFIX = "prefix";
    public static final String RESPONSE_COLUMN_LASTNAME = "lastname";
    public static final String RESPONSE_COLUMN_TEXT = "text";
    public static final String RESPONSE_COLUMN_ACTIVE = "active";

    public SqlLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + NEWS_TABLE_NAME + " (" +
                NEWS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NEWS_COLUMN_TITLE + " TEXT" +
                NEWS_COLUMN_IMAGE + "TEXT" +
                NEWS_COLUMN_TEXT + "TEXT" +
                NEWS_COLUMN_POSTED_ON + "TEXT" +
                NEWS_COLUMN_LAST_EDITED + "TEXT" +
                NEWS_COLUMN_ACTIVE + "INTEGER" +")");
        db.execSQL("CREATE TABLE " + RESPONSE_TABLE_NAME + " (" +
                RESPONSE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NEWS_COLUMN__ID + " TEXT" +
                RESPONSE_COLUMN_FIRST_NAME + "TEXT" +
                RESPONSE_COLUMN_PREFIX + "TEXT" +
                RESPONSE_COLUMN_LASTNAME + "TEXT" +
                RESPONSE_COLUMN_TEXT + "TEXT" +
                RESPONSE_COLUMN_ACTIVE + "INTEGER" +")");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NEWS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RESPONSE_TABLE_NAME);
        onCreate(db);
    }
}
