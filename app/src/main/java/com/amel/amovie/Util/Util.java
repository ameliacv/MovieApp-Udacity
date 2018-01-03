package com.amel.amovie.Util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;


/**
 * Created by amel on 01/07/2017.
 */

public class Util {


    public static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w500/";
    public static final String URL_GET_POPULAR = "https://api.themoviedb.org/3/movie/popular";
    public static final String URL_GET_TOP = "https://api.themoviedb.org/3/movie/top_rated";
    public static final String URL_GET_DETAIL = "https://api.themoviedb.org/3/movie/";
    public static final String API_KEY = "";

    public static final String PARAM_ID = "id";
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_POSTER_PATH = "poster_path";
    public static final String PARAM_BACKDROP_PATH = "backdrop_path";
    public static final String PARAM_API_KEY = "api_key";
    public static final String PARAM_RESULT = "results";
    public static final String PARAM_ORIGINAL_TITLE = "original_title";
    public static final String PARAM_OVERVIEW = "overview";
    public static final String PARAM_RELEASE_DATE = "release_date";
    public static final String PARAM_RUNTIME = "runtime";
    public static final String PARAM_VOTE_AVERAGE = "vote_average";
    public static final String PARAM_MOVIE_DETAIL = "movie_detail";
    public static final String PARAM_KEY = "key";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_SITE = "site";
    public static final String PARAM_SIZE = "size";
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_VIDEO = "videos";
    public static final String URL_YOUTUBE = "http://www.youtube.com/watch?v=";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_TOTAL_PAGE = "total_pages";
    public static final String PARAM_URL = "url";
    public static final String PARAM_CONTENT = "content";
    public static final String PARAM_AUTHOR = "author";
    public static final String PARAM_REVIEW = "reviews";


    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void openWebPage(String url, Context context) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            }
        }
    }

}
