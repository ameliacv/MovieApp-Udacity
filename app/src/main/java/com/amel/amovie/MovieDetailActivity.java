package com.amel.amovie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amel.amovie.Data.MovieEntry;
import com.amel.amovie.Movie.MovieData;
import com.amel.amovie.Movie.MovieDetail;
import com.amel.amovie.Movie.Review;
import com.amel.amovie.Movie.Video;
import com.amel.amovie.R;
import com.amel.amovie.Util.NetworkUtils;
import com.amel.amovie.Util.Util;


import com.android.volley.RequestQueue;


import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailActivity extends AppCompatActivity implements MovieTrailerAdapter.OnItemClickListener,
        MovieReviewAdapter.OnItemClickListener {
    private MovieDetail movieDetail;
    private MovieData movieData;
    private MovieTrailerAdapter movieTrailerAdapter;
    private MovieReviewAdapter movieReviewAdapter;
    private MovieTrailerAdapter.OnItemClickListener mListener;
    private MovieReviewAdapter.OnItemClickListener mListenerReview;
    private List<Video> videoList = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();
    private String movieID;
    private MovieEntry mMovieEntry;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.year_movie)
    TextView mYearMovie;
    @BindView(R.id.duration_movie)
    TextView mDurationMovie;
    @BindView(R.id.rating_movie)
    TextView mRatingMovie;
    @BindView(R.id.content_movie)
    TextView mContentMovie;
    @BindView(R.id.image_movie)
    ImageView mImageMovie;
    @BindView(R.id.list_trailer)
    RecyclerView mListTrailer;
    @BindView(R.id.list_review)
    RecyclerView mListReview;
    @BindView(R.id.content_loading)
    RelativeLayout loadContent;
    @BindView(R.id.button_favorite)
    Button mButtonFav;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        init();
        if (Util.isOnline(this)) {
            makeRequestQuery();
        }
    }

    private void showDetailData() {
        loadContent.setVisibility(View.GONE);
//        mTitleMovie.setText(movieDetail.getOriginalTitle());

        Log.d("amel", movieDetail.getId());
        mYearMovie.setText(movieDetail.getReleaseDate());
        mCollapsingToolbar.setTitle(movieDetail.getOriginalTitle());
        mAppBar.setExpanded(false);

        mContentMovie.setText(movieDetail.getPlotSynopsis());
        mDurationMovie.setText(movieDetail.getRuntime() + " min");
        mRatingMovie.setText(movieDetail.getUserRating() + " /10");

        Picasso.with(this)
                .load(Util.BASE_URL_IMAGE + movieDetail.getImage())
                .fit().centerCrop()
                .into(mImageMovie);
        videoList.addAll(movieDetail.getVideoArrayList());
        reviewList.addAll(movieDetail.getReviewArrayList());
        movieTrailerAdapter.notifyDataSetChanged();
        movieReviewAdapter.notifyDataSetChanged();
    }

    private void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Movie Detail");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        movieData = (MovieData) i.getSerializableExtra(Util.PARAM_MOVIE_DETAIL);
        movieDetail = new MovieDetail();
        loadContent.setVisibility(View.VISIBLE);
        movieTrailerAdapter = new MovieTrailerAdapter(videoList, mListener);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mListTrailer.setLayoutManager(mLayoutManager);
//        mListTrailer.setItemAnimator(new DefaultItemAnimator());
        mListTrailer.setAdapter(movieTrailerAdapter);

        movieReviewAdapter = new MovieReviewAdapter(reviewList, mListenerReview);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        mListReview.setLayoutManager(mLayoutManager1);
//        mListTrailer.setItemAnimator(new DefaultItemAnimator());
        mListReview.setAdapter(movieReviewAdapter);


        mMovieEntry = new MovieEntry(getApplicationContext());
        mButtonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("amel movie", movieData.getTitle());
                if(movieData!=null){
                    try {
                        mMovieEntry.open();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    mMovieEntry.createItem(movieData);
                    Toast.makeText(MovieDetailActivity.this, "Added to Favourite", Toast.LENGTH_LONG).show();
                    mMovieEntry.close();
                }

            }
        });

    }

    private void makeRequestQuery() {

        URL requestUrl = NetworkUtils.buildUrl(movieData.getId());
        new MovieGetTask().execute(requestUrl);
    }

    @Override
    public void onItemClick(Review review) {
        Util.openWebPage(review.getUrl(), getApplicationContext());
    }

    @Override
    public void onItemClick(Video video) {
        Uri vidUrl = Uri.parse(Util.URL_YOUTUBE + video.getKey());

        Intent intent = new Intent(Intent.ACTION_VIEW, vidUrl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

    }


    private class MovieGetTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... params) {

            URL searchUrl = params[0];
            String response = null;
            try {
                response = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null && !response.equals("")) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    movieDetail = new MovieDetail(jObj);
                    showDetailData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
