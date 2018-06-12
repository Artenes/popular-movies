package com.artenesnogueira.popularmovies.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artenesnogueira.popularmovies.R;
import com.artenesnogueira.popularmovies.models.MovieReview;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter that displays a list of videos from youtube
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private List<MovieReview> reviews = new ArrayList<>(0);

    /**
     * Set the new data set and notify the adapter of the changes
     *
     * @param reviews the new list of reviews
     */
    public void setData(@NonNull List<MovieReview> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        MovieReview review = reviews.get(position);
        holder.authorTextView.setText(review.getAuthor());
        holder.contentTextView.setText(review.getContent());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    /**
     * The view holder for this adapter
     */
    class ReviewViewHolder extends RecyclerView.ViewHolder {

        final TextView authorTextView;
        final TextView contentTextView;

        ReviewViewHolder(View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.tv_author);
            contentTextView = itemView.findViewById(R.id.tv_review);
        }

    }

}
