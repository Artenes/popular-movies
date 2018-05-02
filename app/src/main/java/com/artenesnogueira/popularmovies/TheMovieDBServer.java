package com.artenesnogueira.popularmovies;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * The Movie DB Server implementation to get movie data
 */
public class TheMovieDBServer implements MoviesServer {

    private static final String TAG = TheMovieDBServer.class.getSimpleName();

    public static final String RESULTS_FIELD = "results";
    private static final Uri BASE_URI = Uri.parse("https://api.themoviedb.org/3/movie?api_key=" + BuildConfig.POPULAR_MOVIES_THE_MOVIE_DB_API_KEY);
    private static final String TOP_RATED_PATH = "top_rated";
    private static final String POPULAR_PATH = "popular";

    @Override
    public JSONObject getTopRatedMovies() throws IOException, JSONException {
        Uri topRatedURI = BASE_URI.buildUpon().appendPath(TOP_RATED_PATH).build();
        return fetch(topRatedURI);
    }

    @Override
    public JSONObject getPopularMovies() throws IOException, JSONException {
        Uri popularURI = BASE_URI.buildUpon().appendPath(POPULAR_PATH).build();
        return fetch(popularURI);
    }

    private JSONObject fetch(Uri uri) throws IOException, JSONException {
        URL popularURL = new URL(uri.toString());
        String rawJSONResponse = NetworkUtils.getResponseFromHttpUrlWithLog(TAG, popularURL);
        return new JSONObject(rawJSONResponse);
    }

}
