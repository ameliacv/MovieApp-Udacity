package com.amel.amovie.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.amel.amovie.Movie.MovieData;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by amel on 28/07/2017.
 */

public class MovieEntry {
    private SQLiteDatabase database;
    private MovieDbHelper mDb;

    public static final class MovieEntryData implements BaseColumns {
        // COMPLETED (2) Inside create a static final members for the table name and each of the db columns
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_MOVIE_ID = "movieid";
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_IMAGE = "image";
    }

    public MovieEntry(Context context) {
        mDb = new MovieDbHelper(context);
    }

    public void createItem(MovieData movieData) {
        ContentValues values = new ContentValues();
        values.put(MovieEntryData.COLUMN_MOVIE_ID, movieData.getId());
        values.put(MovieEntryData.COLUMN_MOVIE_TITLE, movieData.getTitle());
        values.put(MovieEntryData.COLUMN_MOVIE_IMAGE, movieData.getImage());
        if (isItemExist(movieData.getId())) {
            deleteItem(Long.parseLong(movieData.getId()));
        }
        database.insert(MovieEntryData.TABLE_NAME, null, values);
    }
    public boolean deleteItem(long id) {
        return database.delete(MovieEntryData.TABLE_NAME, MovieEntryData.COLUMN_MOVIE_ID + "=" + id, null) > 0;
    }
    public void open() throws SQLException {
        database = mDb.getReadableDatabase();
    }

    public void close() {
        if (database != null && database.isOpen()) {
            mDb.close();
        }
    }

    public boolean isItemExist(String id) {
        if (!database.isOpen()) {
            try {
                open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Cursor cursor = database.query(
                MovieEntryData.TABLE_NAME,
                null,
                MovieEntryData.COLUMN_MOVIE_ID + " = '" + id + "'",
                null,
                null,
                null,
               null);
        //database.close();
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }

        cursor.close();
        //close();
        return false;
    }
    public ArrayList<MovieData> getAllItem() {
        if (!database.isOpen()) {
            try {
                open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Cursor cursor = database.query(
                MovieEntryData.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieEntryData.COLUMN_MOVIE_TITLE);

        if (cursor.getCount() > 0) {
            ArrayList<MovieData> arrayListItem = new ArrayList<>();
            while (cursor.moveToNext()) {
                MovieData item = new MovieData(cursor);
                arrayListItem.add(item);
            }
            //Log.d("Photo URL DB", member.getPhotoUrl());
            cursor.close();
            return arrayListItem;
        }

        cursor.close();
        return null;
    }
}
