package com.artenesnogueira.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.artenesnogueira.popularmovies.R;
import com.artenesnogueira.popularmovies.models.Image;
import com.artenesnogueira.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter that displays the posters in the UI
 */
public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterViewHolder> {

    private final int posterWidth;
    private final int posterHeight;

    /**
     * Creates a new instance of the class. The sizes are necessary to define a fixed size to all
     * posters so they can be resized to fit the whole screen properly. If each image had its own
     * size, this will not be possible.
     *
     * @param posterWidth  the width to resize the posters to
     * @param posterHeight the height to resize the posters to
     */
    public MoviePosterAdapter(int posterWidth, int posterHeight) {
        this.posterWidth = posterWidth;
        this.posterHeight = posterHeight;
    }

    private List<Movie> movies = new ArrayList<>(0);

    /**
     * Set the new data set and notify the adapter of the changes
     *
     * @param movies the new list of movies
     */
    public void setData(@NonNull List<Movie> movies) {
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
        Image poster = movies.get(position).getPoster();
        Picasso.get()
                .load(poster.getPath())
                .resize(posterWidth, posterHeight)
                .placeholder(R.drawable.loading_poster)
                .error(R.drawable.broken_poster)
                .into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    /**
     * The view holder for this adapter
     */
    class MoviePosterViewHolder extends RecyclerView.ViewHolder {

        final ImageView posterImageView;

        MoviePosterViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_movie_poster);
        }

    }

}
