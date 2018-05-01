package com.artenesnogueira.popularmovies;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A remote server that contains movie data
 */
interface MoviesServer {

    /**
     * Get info about top rated movies
     *
     * @return top rated movies info
     * @throws IOException   in case of connection issues
     * @throws JSONException in case of error while parsing the results from the server
     */
    JSONObject getTopRatedMovies() throws IOException, JSONException;

    /**
     * Get info about popular movies
     *
     * @return popular movies info
     * @throws IOException   in case of connection issues
     * @throws JSONException in case of error while parsing the results from the server
     */
    JSONObject getPopularMovies() throws IOException, JSONException;

}
