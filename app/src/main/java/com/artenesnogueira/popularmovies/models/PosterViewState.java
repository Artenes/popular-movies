package com.artenesnogueira.popularmovies.models;

import java.util.List;

/**
 * The state of the poster view. This is an immutable object.
 */
public class PosterViewState implements State {

    private final List<MoviePoster> movies;
    private final Filter filter;
    private final boolean loading;
    private final boolean hasError;

    public static PosterViewState makeLoadingState(Filter filter) {
        return new PosterViewState(null, filter, true, false);
    }

    public static PosterViewState makeErrorState(Filter filter) {
        return new PosterViewState(null, filter, false, true);
    }

    public static PosterViewState makeViewMoviesState(List<MoviePoster> movies, Filter filter) {
        return new PosterViewState(movies, filter, false, false);
    }

    public List<MoviePoster> getMovies() {
        return movies;
    }

    public Filter getFilter() {
        return filter;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean hasError() {
        return hasError;
    }

    private PosterViewState(List<MoviePoster> movies, Filter filter, boolean loading, boolean hasError) {
        this.movies = movies;
        this.filter = filter;
        this.loading = loading;
        this.hasError = hasError;
    }

}
