package com.amel.amovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amel.amovie.Movie.MovieDetail;
import com.amel.amovie.Movie.Video;
import com.amel.amovie.Util.Util;


import java.util.List;

/**
 * Created by amel on 06/07/2017.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {

    private final List<Video> mVideo;
    private final OnItemClickListener mListener;

    public MovieTrailerAdapter(List<Video> items, OnItemClickListener listener) {
        mVideo = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mVideo.get(position);
        holder.mName.setText(holder.mItem.getName());
        holder.mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri vidUrl = Uri.parse(Util.URL_YOUTUBE + holder.mItem.getKey());

                Intent intent = new Intent(Intent.ACTION_VIEW, vidUrl);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                        v.getContext().startActivity(intent);
                    }
                }
            }
        });

//        holder.mContentView.setText(mValues.get(position).content);


    }

    @Override
    public int getItemCount() {
        return mVideo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final Button mButtonPlay;
        public Video mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.name_trailer);
            mButtonPlay = (Button) view.findViewById(R.id.button_play);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Video video);
    }
}
