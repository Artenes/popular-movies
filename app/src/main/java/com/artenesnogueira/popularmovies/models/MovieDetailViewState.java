package com.artenesnogueira.popularmovies.models;

/**
 * The state of the movie detail view. This is an immutable object.
 */
public class MovieDetailViewState implements State {

    private final String movieId;
    private final Movie movie;
    private final boolean loading;
    private final boolean hasError;

    public static MovieDetailViewState makeLoadingState(String id) {
        return new MovieDetailViewState(id, null, true, false);
    }

    public static MovieDetailViewState makeErrorState(String id) {
        return new MovieDetailViewState(id, null, false, true);
    }

    public static MovieDetailViewState makeViewMovieState(Movie movie, String id) {
        return new MovieDetailViewState(id, movie, false, false);
    }

    public String getMovieId() {
        return movieId;
    }

    public Movie getMovie() {
        return movie;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean hasError() {
        return hasError;
    }

    private MovieDetailViewState(String movieId, Movie movie, boolean loading, boolean hasError) {
        this.movieId = movieId;
        this.movie = movie;
        this.loading = loading;
        this.hasError = hasError;
    }

}
