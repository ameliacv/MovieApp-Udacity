package com.amel.amovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.amel.amovie.Movie.MovieData;
import com.amel.amovie.Util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amel on 02/07/2017.
 */

public class MovieAdapter extends ArrayAdapter<MovieData> {
    Context context;

    public MovieAdapter(Context context) {
        super(context, 0, new ArrayList<MovieData>());
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MovieData movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        ImageView mPosterMovie = (ImageView) convertView.findViewById(R.id.movie_image);
        Picasso.with(context)
                .load(Util.BASE_URL_IMAGE + movie.getImage())
//                .fit().centerInside()

                .into(mPosterMovie);

        return convertView;
    }
}
