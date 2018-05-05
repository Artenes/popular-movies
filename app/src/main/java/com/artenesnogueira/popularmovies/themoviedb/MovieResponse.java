package com.artenesnogueira.popularmovies.themoviedb;

import android.net.Uri;

import com.artenesnogueira.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A response from the /movie endpoint from the TheMovieDB API
 */
public class MovieResponse {

    //the base path used to fetch the images
    private static final Uri BASE_IMAGE_PATH = Uri.parse("http://image.tmdb.org/t/p");
    private static final Uri THUMBNAIL_IMAGE_PATH = BASE_IMAGE_PATH.buildUpon().appendPath("w185").build();
    private static final Uri FULL_SIZE_IMAGE_PATH = BASE_IMAGE_PATH.buildUpon().appendPath("w780").build();

    private static final String RESULTS_FIELD = "results";
    private static final String VOTE_AVERAGE_FIELD = "vote_average";
    private static final String TITLE_FIELD = "title";
    private static final String POSTER_PATH_FIELD = "poster_path";
    private static final String ORIGINAL_TITLE_FIELD = "original_title";
    private static final String BACKDROP_PATH_FIELD = "backdrop_path";
    private static final String OVERVIEW_FIELD = "overview";
    private static final String RELEASE_DATE_FIELD = "release_date";

    private final List<Movie> results;

    public static MovieResponse parse(String rawJsonResponse) throws JSONException {

        //create the json object from the string
        JSONObject jsonResponse = new JSONObject(rawJsonResponse);

        //get the raw results from the response
        JSONArray rawResults = jsonResponse.getJSONArray(RESULTS_FIELD);

        //initialize an empty list that will hold the result items
        List<Movie> results = new ArrayList<>(0);

        for (int index = 0; index < rawResults.length(); index++) {
            //get the movie data from the response
            JSONObject rawMovie = rawResults.getJSONObject(index);

            //we have to remove the first slash that appears in the relative path
            //before building the absolute path
            String relativePosterPath = rawMovie.getString(POSTER_PATH_FIELD).replaceFirst("/", "");
            String relativeBackdropPath = rawMovie.getString(BACKDROP_PATH_FIELD).replaceFirst("/", "");

            //and parse to the movie object
            Movie movie = new Movie(
                    rawMovie.getLong(VOTE_AVERAGE_FIELD),
                    rawMovie.getString(TITLE_FIELD),
                    THUMBNAIL_IMAGE_PATH.buildUpon().appendPath(relativePosterPath).build().toString(),
                    rawMovie.getString(ORIGINAL_TITLE_FIELD),
                    FULL_SIZE_IMAGE_PATH.buildUpon().appendPath(relativeBackdropPath).build().toString(),
                    rawMovie.getString(OVERVIEW_FIELD),
                    rawMovie.getString(RELEASE_DATE_FIELD)
            );

            //finally add in the results list
            results.add(movie);
        }

        return new MovieResponse(results);

    }

    //this is private so it can be instantiated only
    //by the static constructor
    private MovieResponse(List<Movie> movies) {
        this.results = movies;
    }

    public List<Movie> getResults() {
        return results;
    }

}
