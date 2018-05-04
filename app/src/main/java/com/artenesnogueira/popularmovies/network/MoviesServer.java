package com.artenesnogueira.popularmovies.network;

import com.artenesnogueira.popularmovies.models.Filter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A remote server that contains movie data
 */
public interface MoviesServer {

    /**
     * Get info about movies filtered by a filter (eg. popular, top rated, etc.)
     *
     * @return top rated movies info
     * @throws IOException   in case of connection issues
     * @throws JSONException in case of error while parsing the results from the server
     */
    JSONObject getMovies(Filter filter) throws IOException, JSONException;

}
