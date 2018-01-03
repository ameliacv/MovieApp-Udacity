package com.amel.amovie.Movie;

import com.amel.amovie.Util.Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amel on 28/07/2017.
 */

public class Review {
    String author, content, url;

    public Review() {

    }

    public Review(JSONObject json) {

        try {
            if (!json.isNull(Util.PARAM_AUTHOR)) {
                this.setAuthor(json.getString(Util.PARAM_AUTHOR));
            }
            if (!json.isNull(Util.PARAM_CONTENT)) {
                this.setContent(json.getString(Util.PARAM_CONTENT));
            }
            if (!json.isNull(Util.PARAM_URL)) {
                this.setUrl(json.getString(Util.PARAM_URL));
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
