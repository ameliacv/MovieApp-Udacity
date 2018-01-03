package com.amel.amovie.Movie;

import android.database.Cursor;

import com.amel.amovie.Data.MovieEntry;
import com.amel.amovie.Util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by amel on 01/07/2017.
 */

public class MovieData implements Serializable {

    private String title;
    private String id;
    private String image;
    private transient JSONObject json;



    public MovieData(){

    }

    public MovieData(JSONObject json) {
        setJson(json);
        try {
            if (!json.isNull(Util.PARAM_ID)) {
                this.setId(json.getString(Util.PARAM_ID));
            }
            if (!json.isNull(Util.PARAM_TITLE)) {
                this.setTitle(json.getString(Util.PARAM_TITLE));
            }
            if (!json.isNull(Util.PARAM_POSTER_PATH)) {
                this.setImage(json.getString(Util.PARAM_POSTER_PATH));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public MovieData(Cursor cursor) {
        this.setDataFromCursor(cursor);
    }

    private void setDataFromCursor(Cursor cursor) {
        this.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(MovieEntry.MovieEntryData.COLUMN_MOVIE_ID))));
        this.setTitle(cursor.getString(cursor.getColumnIndex(MovieEntry.MovieEntryData.COLUMN_MOVIE_TITLE)));
        this.setImage(cursor.getString(cursor.getColumnIndex(MovieEntry.MovieEntryData.COLUMN_MOVIE_IMAGE)));
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(Util.PARAM_ID, this.getId());
            jsonObj.put(Util.PARAM_TITLE, this.getTitle());
            jsonObj.put(Util.PARAM_POSTER_PATH, this.getImage());
            this.setJson(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setJson(JSONObject json) {
        this.json = json;
    }
    public JSONObject getJson() {
        return json;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
