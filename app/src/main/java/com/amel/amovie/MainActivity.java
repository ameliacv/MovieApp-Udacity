package com.amel.amovie;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.amel.amovie.Data.MovieDbHelper;
import com.amel.amovie.Data.MovieEntry;
import com.amel.amovie.Movie.MovieData;
import com.amel.amovie.Movie.MovieDetail;
import com.amel.amovie.Util.Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter mMovieAdapter;
    private MovieData currentMovie;
    private String id_movie;
    private ArrayList<MovieData> movieList;
    private MovieEntry mMovieEntry;
    private int total_pages;
    private boolean lastPage;
    private int page;
    private boolean loadData;

    @BindView(R.id.movie_grid)
    GridView mGridMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDb();
        init();
        if (Util.isOnline(this)) {
            fetchDataPopular();
        }

    }

    private void initDb() {
//        mMovieEntry = new MovieEntry(getApplicationContext());     mMovieEntry.createItem(movie);
    }

    private void init() {
        currentMovie = new MovieData();
        mMovieAdapter = new MovieAdapter(this);
        mGridMovie.setAdapter(mMovieAdapter);
        mGridMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieData movieData = (MovieData) parent.getItemAtPosition(position);
                Intent i = new Intent(MainActivity.this, MovieDetailActivity.class);
                i.putExtra(Util.PARAM_MOVIE_DETAIL, movieData);
                startActivity(i);
            }
        });
        mGridMovie.setOnScrollListener(listDataScroll);
        mMovieEntry = new MovieEntry(getApplicationContext());
    }

    private AbsListView.OnScrollListener listDataScroll = new AbsListView.OnScrollListener() {
        int currentScrollState, currentFirstVisibleItem, currentVisibleItemCount, treshold = 1;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (isLoadData()) {

            }
            currentScrollState = scrollState;
            isScrollCompleted();
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            currentFirstVisibleItem = firstVisibleItem;
            currentVisibleItemCount = visibleItemCount;
        }

        public void isScrollCompleted() {
            if (currentFirstVisibleItem + currentVisibleItemCount >= (mMovieAdapter.getCount() - treshold)) {
                if (isLoadData()) {
                    setLoadData(false);
                }
            }
        }
    };

    private void fetchDataPopular() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Util.URL_GET_POPULAR;
        mMovieAdapter.clear();
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            setPage(jsonObj.getInt(Util.PARAM_PAGE));
                            setTotal_pages(jsonObj.getInt(Util.PARAM_TOTAL_PAGE));
                            JSONArray jsonArray = jsonObj.getJSONArray(Util.PARAM_RESULT);
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    MovieData movie = new MovieData(jsonArray.getJSONObject(i));

                                    mMovieAdapter.add(movie);
                                    mMovieAdapter.notifyDataSetChanged();
                                }
                                setLoadData(true);
                            } else {
                                setLoadData(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Util.PARAM_API_KEY, Util.API_KEY);
                return params;
            }
        };
        queue.add(postRequest);
    }
    private void fetchDataFav() {
        mMovieAdapter.clear();
        try {
            mMovieEntry.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        movieList = mMovieEntry.getAllItem();
//        Log.d("amel", movieList.toString());
        mMovieAdapter.addAll(movieList);
        mMovieAdapter.notifyDataSetChanged();
        mMovieEntry.close();
    }

    private void fetchDataTop() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Util.URL_GET_TOP;
        mMovieAdapter.clear();
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray jsonArray = jsonObj.getJSONArray(Util.PARAM_RESULT);
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    MovieData movie = new MovieData(jsonArray.getJSONObject(i));

                                    mMovieAdapter.add(movie);
                                    mMovieAdapter.notifyDataSetChanged();
                                }
                                setLoadData(true);
                            } else {
                                setLoadData(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Util.PARAM_API_KEY, Util.API_KEY);
                return params;
            }
        };
        queue.add(postRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.most_popular) {
            fetchDataPopular();
            return true;
        }
        if (id == R.id.top_rated) {
            fetchDataTop();
            return true;
        }
        if (id == R.id.favourite) {
           fetchDataFav();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public boolean isLoadData() {
        return loadData;
    }

    public void setLoadData(boolean loaddData) {
        this.loadData = loadData;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

}
