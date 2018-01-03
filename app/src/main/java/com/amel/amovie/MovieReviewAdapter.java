package com.amel.amovie;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.amel.amovie.Movie.MovieDetail;
import com.amel.amovie.Movie.Review;



import java.util.List;

/**
 * Created by amel on 06/07/2017.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {

    private final List<Review> mReview;
    private final OnItemClickListener mListener;

    public MovieReviewAdapter(List<Review> items, OnItemClickListener listener) {
        mReview = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mReview.get(position);
        holder.mAuthor.setText(holder.mItem.getAuthor());
        holder.mContent.setText(holder.mItem.getContent());
    }

    @Override
    public int getItemCount() {
        return mReview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mAuthor;
        public final TextView mContent;
        public Review mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAuthor = (TextView) view.findViewById(R.id.text_author);
            mContent = (TextView) view.findViewById(R.id.text_content);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Review review);
    }
}
