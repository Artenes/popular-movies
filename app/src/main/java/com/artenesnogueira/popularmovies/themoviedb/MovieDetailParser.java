package com.artenesnogueira.popularmovies.themoviedb;

import com.artenesnogueira.popularmovies.models.Movie;
import com.artenesnogueira.popularmovies.models.MovieReview;
import com.artenesnogueira.popularmovies.models.YoutubeVideo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Parser that creates a Movie from a raw json response
 */
class MovieDetailParser {

    public static Movie parse(String rawJsonResponse, List<YoutubeVideo> videos, List<MovieReview> reviews) throws JSONException {

        //create the json object from the string
        JSONObject rawMovie = new JSONObject(rawJsonResponse);

        //prepare the paths for the images
        String posterPath = TheMovieDBContract
                .getThumbnailImageURL(rawMovie.getString(TheMovieDBContract.POSTER_PATH_FIELD))
                .toString();
        String backdropPath = TheMovieDBContract
                .getFullSizeImageURL(rawMovie.getString(TheMovieDBContract.BACKDROP_PATH_FIELD))
                .toString();

        //and parse to the movie object
        return new Movie(
                rawMovie.getString(TheMovieDBContract.ID_FIELD),
                rawMovie.getLong(TheMovieDBContract.VOTE_AVERAGE_FIELD),
                rawMovie.getString(TheMovieDBContract.TITLE_FIELD),
                posterPath,
                rawMovie.getString(TheMovieDBContract.ORIGINAL_TITLE_FIELD),
                backdropPath,
                rawMovie.getString(TheMovieDBContract.OVERVIEW_FIELD),
                rawMovie.getString(TheMovieDBContract.RELEASE_DATE_FIELD),
                false,
                videos,
                reviews
        );

    }

}
