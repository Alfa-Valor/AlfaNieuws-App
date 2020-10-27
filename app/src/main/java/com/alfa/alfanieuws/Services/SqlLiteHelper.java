package com.alfa.alfanieuws.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NEWS_TABLE_NAME + " (" +
                NEWS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NEWS_COLUMN_TITLE + " TEXT" +
                NEWS_COLUMN_IMAGE + "TEXT" +
                NEWS_COLUMN_TEXT + "TEXT" +
                NEWS_COLUMN_POSTED_ON + "TEXT" +
                NEWS_COLUMN_LAST_EDITED + "TEXT" +
                NEWS_COLUMN_ACTIVE + "INTEGER" +")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + RESPONSE_TABLE_NAME + " (" +
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

    // execute a query to the database
    // DB.execute("UPDATE " + tafel + " SET arg='argument' WHERE id =" + id);

    public void execute(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    // select records from the database
    // DB.select("SELECT * FROM " + tafel + " WHERE id =" + id);

    public Cursor select(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        db.close();
        return cursor;
    }

    // insert new record to the database
    // news = new News("title", "image", "text", 1)
    // DB.insert(tafel, news.toContentValues());

    public boolean insert(String tableName, ContentValues contentValues) {
        if (contentValues != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            long rowId = db.insert(tableName, null, contentValues);
            db.close();
            return rowId != -1;
        }
        return false;
    }

    // delete with where string in query
    // DB.delete(tafel, WHERE?);

    private void delete(String tableName, String where) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName + " " + where);
        db.close();
    }
}
