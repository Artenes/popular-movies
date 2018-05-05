package com.artenesnogueira.popularmovies.network;

import android.net.Uri;

import com.artenesnogueira.popularmovies.BuildConfig;
import com.artenesnogueira.popularmovies.models.Filter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * The Movie DB Server implementation to get movie data
 */
public class TheMovieDBServer implements MoviesServer {

    private static final String TAG = TheMovieDBServer.class.getSimpleName();

    //the field in the json response that contains the movie list
    public static final String RESULTS_FIELD = "results";

    //the base uri to make requests with the api key already appended for convenience
    private static final Uri BASE_URI = Uri.parse("https://api.themoviedb.org/3/movie?api_key=" + BuildConfig.POPULAR_MOVIES_THE_MOVIE_DB_API_KEY);

    //this hash maps the filters available in the model
    //to the ones that exists in TheMovieDB API
    //see static code below for initialization
    private static final Map<Filter, String> filterMapping = new HashMap<>();

    @Override
    public JSONObject getMovies(Filter filter) throws IOException, JSONException {
        //first we have to get an URL from an URI
        Uri uri = BASE_URI.buildUpon().appendPath(filterMapping.get(filter)).build();
        URL popularURL = new URL(uri.toString());
        //then we can make the request to get the json response
        String rawJSONResponse = NetworkUtils.getResponseFromHttpUrlWithLog(TAG, popularURL);
        return new JSONObject(rawJSONResponse);
    }

    static {
        //initialize the mapping from available filters in the model
        //to the ones that exists in TheMovieDB API
        filterMapping.put(Filter.TOP_RATED, "top_rated");
        filterMapping.put(Filter.POPULAR, "popular");
    }

}
