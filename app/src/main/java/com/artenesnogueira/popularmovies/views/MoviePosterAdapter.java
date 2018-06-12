package com.artenesnogueira.popularmovies.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.artenesnogueira.popularmovies.R;
import com.artenesnogueira.popularmovies.models.MoviePoster;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter that displays the posters in the UI
 */
public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterViewHolder> {

    //all the thumbnails must be resized to the same size
    //so all of them are distributed even in the screen
    private static final int DEFAULT_THUMBNAIL_WIDTH = 185;
    private static final int DEFAULT_THUMBNAIL_HEIGHT = 278;

    private List<MoviePoster> movies = new ArrayList<>(0);
    private final OnPosterClicked posterClickedCallback;

    public MoviePosterAdapter(OnPosterClicked posterClickedCallback) {
        this.posterClickedCallback = posterClickedCallback;
    }

    /**
     * Set the new data set and notify the adapter of the changes
     *
     * @param movies the new list of movies
     */
    public void setData(@NonNull List<MoviePoster> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster_view, parent, false);
        return new MoviePosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterViewHolder holder, int position) {
        //the only thing we have to do is load the poster
        Picasso.get()
                .load(movies.get(position).getPosterPath())
                .resize(DEFAULT_THUMBNAIL_WIDTH, DEFAULT_THUMBNAIL_HEIGHT)
                .placeholder(R.drawable.loading_poster)
                .error(R.drawable.broken_poster)
                .into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    /**
     * Interface for when an item is clicked
     */
    public interface OnPosterClicked {

        void onPosterClicked(MoviePoster movie);

    }

    /**
     * The view holder for this adapter
     */
    class MoviePosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView posterImageView;

        MoviePosterViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_movie_poster);
            posterImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            posterClickedCallback.onPosterClicked(movies.get(getAdapterPosition()));
        }

    }

}
