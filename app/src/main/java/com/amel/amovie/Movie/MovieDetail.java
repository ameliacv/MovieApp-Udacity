package com.amel.amovie.Movie;

import com.amel.amovie.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by amel on 02/07/2017.
 */

public class MovieDetail implements Serializable{
    private String title;
    private String id;
    private String originalTitle;
    private String plotSynopsis;
    private String userRating;
    private String releaseDate;
    private String runtime;
    private String image;
    private JSONObject json;

    private ArrayList<Video> videoArrayList = new ArrayList<Video>();

    private ArrayList<Review> reviewArrayList = new ArrayList<>();

    public MovieDetail() {

    }

    public MovieDetail(JSONObject json) {

        try {
            if (!json.isNull(Util.PARAM_ID)) {
                this.setId(json.getString(Util.PARAM_ID));
            }
            if (!json.isNull(Util.PARAM_ORIGINAL_TITLE)) {
                this.setOriginalTitle(json.getString(Util.PARAM_ORIGINAL_TITLE));
            }
            if (!json.isNull(Util.PARAM_OVERVIEW)) {
                this.setPlotSynopsis(json.getString(Util.PARAM_OVERVIEW));
            }
            if (!json.isNull(Util.PARAM_VOTE_AVERAGE)) {
                this.setUserRating(json.getString(Util.PARAM_VOTE_AVERAGE));
            }
            if (!json.isNull(Util.PARAM_RUNTIME)) {
                this.setRuntime(json.getString(Util.PARAM_RUNTIME));
            }
            if (!json.isNull(Util.PARAM_POSTER_PATH)) {
                this.setImage(json.getString(Util.PARAM_POSTER_PATH));
            }
            if (!json.isNull(Util.PARAM_RELEASE_DATE)) {
                this.setReleaseDate(json.getString(Util.PARAM_RELEASE_DATE));
            }
            if (!json.isNull(Util.PARAM_VIDEO)) {
                JSONArray vidArr = json.getJSONObject(Util.PARAM_VIDEO).getJSONArray(Util.PARAM_RESULT);
                ArrayList<Video> videoArrayList1 = new ArrayList<>();
                for (int i = 0; i < vidArr.length(); i++) {
                    videoArrayList1.add(new Video(vidArr.getJSONObject(i)));
                }
                this.setVideoArrayList(videoArrayList1);
            }
            if (!json.isNull(Util.PARAM_REVIEW)) {
                JSONArray reviewArr = json.getJSONObject(Util.PARAM_REVIEW).getJSONArray(Util.PARAM_RESULT);
                ArrayList<Review> reviewArrayList1 = new ArrayList<>();
                for (int i = 0; i < reviewArr.length(); i++) {
                    reviewArrayList1.add(new Review(reviewArr.getJSONObject(i)));
                }
                this.setReviewArrayList(reviewArrayList1);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        try {
            this.releaseDate = releaseDate.substring(0, 4);
        } catch (java.lang.StringIndexOutOfBoundsException e) {
            this.releaseDate = null;
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public ArrayList<Video> getVideoArrayList() {
        return videoArrayList;
    }

    public void setVideoArrayList(ArrayList<Video> videoArrayList) {
        this.videoArrayList = videoArrayList;
    }
    public ArrayList<Review> getReviewArrayList() {
        return reviewArrayList;
    }

    public void setReviewArrayList(ArrayList<Review> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;
    }

}
