package com.amel.amovie.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amel on 28/07/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieEntry.MovieEntryData.TABLE_NAME + " (" +
                MovieEntry.MovieEntryData.COLUMN_MOVIE_ID + " INTEGER," +
                MovieEntry.MovieEntryData.COLUMN_MOVIE_TITLE + " TEXT NOT NULL," +
                MovieEntry.MovieEntryData.COLUMN_MOVIE_IMAGE + " TEXT" +
                ");";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.MovieEntryData.TABLE_NAME);
        onCreate(db);
    }
}
