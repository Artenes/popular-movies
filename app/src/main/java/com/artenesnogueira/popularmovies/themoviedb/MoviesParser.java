package com.artenesnogueira.popularmovies.themoviedb;

import com.artenesnogueira.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser that creates Movies from a raw json response
 */
class MoviesParser {

    public static List<Movie> parse(String rawJsonResponse) throws JSONException {

        //create the json object from the string
        JSONObject jsonResponse = new JSONObject(rawJsonResponse);

        //get the raw results from the response
        JSONArray rawResults = jsonResponse.getJSONArray(TheMovieDBContract.RESULTS_FIELD);

        //initialize an empty list that will hold the result items
        List<Movie> results = new ArrayList<>(0);

        for (int index = 0; index < rawResults.length(); index++) {
            //get the movie data from the response
            JSONObject rawMovie = rawResults.getJSONObject(index);

            //we have to remove the first slash that appears in the relative path
            //before building the absolute path
            String posterPath = TheMovieDBContract
                    .getThumbnailImageURL(rawMovie.getString(TheMovieDBContract.POSTER_PATH_FIELD))
                    .toString();
            String backdropPath = TheMovieDBContract
                    .getFullSizeImageURL(rawMovie.getString(TheMovieDBContract.BACKDROP_PATH_FIELD))
                    .toString();

            //and parse to the movie object
            Movie movie = new Movie(
                    rawMovie.getString(TheMovieDBContract.ID_FIELD),
                    rawMovie.getLong(TheMovieDBContract.VOTE_AVERAGE_FIELD),
                    rawMovie.getString(TheMovieDBContract.TITLE_FIELD),
                    posterPath,
                    rawMovie.getString(TheMovieDBContract.ORIGINAL_TITLE_FIELD),
                    backdropPath,
                    rawMovie.getString(TheMovieDBContract.OVERVIEW_FIELD),
                    rawMovie.getString(TheMovieDBContract.RELEASE_DATE_FIELD),
                    false
            );

            //finally add in the results list
            results.add(movie);
        }

        return results;

    }

}
