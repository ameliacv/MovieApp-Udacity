package com.amel.amovie.Movie;

import com.amel.amovie.Util.Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amel on 06/07/2017.
 */

public class Video {
    String id, key, name, site, size, type;

    public Video() {

    }

    public Video(JSONObject json) {

        try {
            if (!json.isNull(Util.PARAM_ID)) {
                this.setId(json.getString(Util.PARAM_ID));
            }
            if (!json.isNull(Util.PARAM_KEY)) {
                this.setKey(json.getString(Util.PARAM_KEY));
            }
            if (!json.isNull(Util.PARAM_NAME)) {
                this.setName(json.getString(Util.PARAM_NAME));
            }
            if (!json.isNull(Util.PARAM_SITE)) {
                this.setSite(json.getString(Util.PARAM_SITE));
            }
            if (!json.isNull(Util.PARAM_SIZE)) {
                this.setSize(json.getString(Util.PARAM_SIZE));
            }
            if (!json.isNull(Util.PARAM_TYPE)) {
                this.setType(json.getString(Util.PARAM_TYPE));
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
