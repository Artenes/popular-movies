package com.artenesnogueira.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * A repository to fetch movies from The Movie DB service
 */
public class TheMovieDBRepository implements MoviesRepository {

    private final MoviesServer server;

    public TheMovieDBRepository(MoviesServer server) {
        this.server = server;
    }

    @Override
    public List<Movie> getTopRatedMovies() throws IOException {
        try {
            JSONArray movies = server.getTopRatedMovies().getJSONArray(TheMovieDBServer.RESULTS_FIELD);
            return TheMovieDBParser.parseMoviesList(movies);
        } catch (JSONException exception) {
            throw new IOException("JSON result does not match the expected values");
        }
    }

    @Override
    public List<Movie> getPopularMovies() throws IOException {
        try {
            JSONArray movies = server.getPopularMovies().getJSONArray(TheMovieDBServer.RESULTS_FIELD);
            return TheMovieDBParser.parseMoviesList(movies);
        } catch (JSONException exception) {
            throw new IOException("JSON result does not match the expected values");
        }
    }

}
