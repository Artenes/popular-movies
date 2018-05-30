package com.artenesnogueira.popularmovies.themoviedb;

import com.artenesnogueira.popularmovies.models.MoviePoster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser that creates MoviePosters from a raw json response
 */
class MoviePosterParser {

    public static List<MoviePoster> parse(String rawJsonResponse) throws JSONException {

        //create the json object from the string
        JSONObject jsonResponse = new JSONObject(rawJsonResponse);

        //get the raw results from the response
        JSONArray rawResults = jsonResponse.getJSONArray(TheMovieDBContract.RESULTS_FIELD);

        //initialize an empty list that will hold the result items
        List<MoviePoster> results = new ArrayList<>(0);

        for (int index = 0; index < rawResults.length(); index++) {
            //get the movie data from the response
            JSONObject rawMovie = rawResults.getJSONObject(index);

            //build the path to the poster image
            String posterPath = TheMovieDBContract
                    .getThumbnailImageURL(rawMovie.getString(TheMovieDBContract.POSTER_PATH_FIELD))
                    .toString();

            //and parse to the movie object
            MoviePoster moviePoster = new MoviePoster(
                    rawMovie.getString(TheMovieDBContract.ID_FIELD),
                    posterPath
            );

            //finally add in the results list
            results.add(moviePoster);
        }

        return results;

    }

}
