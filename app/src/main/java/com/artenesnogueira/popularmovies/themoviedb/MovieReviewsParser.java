package com.artenesnogueira.popularmovies.themoviedb;

import com.artenesnogueira.popularmovies.models.MovieReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser that creates a list of Movie reviews from a raw json response
 */
class MovieReviewsParser {

    public static List<MovieReview> parseList(String jsonString) throws JSONException {

        JSONObject rawResponse = new JSONObject(jsonString);
        JSONArray rawReviews = rawResponse.getJSONArray(TheMovieDBContract.RESULTS_FIELD);
        List<MovieReview> reviews = new ArrayList<>(0);

        for (int index = 0; index < rawReviews.length(); index++) {

            reviews.add(new MovieReview(
                    rawReviews.getJSONObject(index).getString(TheMovieDBContract.AUTHOR_FIELD),
                    rawReviews.getJSONObject(index).getString(TheMovieDBContract.CONTENT_FIELD)
            ));

        }

        return reviews;

    }

}
