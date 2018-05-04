package com.artenesnogueira.popularmovies.utilities;

import com.artenesnogueira.popularmovies.models.Image;
import com.artenesnogueira.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser to convert JSONObjects to Movies objects
 */
public class TheMovieDBParser {

    private static final String VOTE_AVERAGE_FIELD = "vote_average";
    private static final String TITLE_FIELD = "title";
    private static final String POSTER_PATH_FIELD = "poster_path";
    private static final String ORIGINAL_TITLE_FIELD = "original_title";
    private static final String BACKDROP_PATH_FIELD = "backdrop_path";
    private static final String OVERVIEW_FIELD = "overview";
    private static final String RELEASE_DATE_FIELD = "release_date";

    /**
     * Parse a movie object
     *
     * @param rawMovie the json object with the movie information
     * @return the Movie instance
     * @throws JSONException            if the rawMovie does not have some of the parsed fields
     * @throws IllegalArgumentException if the given image sizes are invalid
     */
    private static Movie parseMovie(JSONObject rawMovie, String posterThumbnailSize, String backDropSize) throws JSONException, IllegalArgumentException {

        Image posterThumbnail = TheMovieDBImageFactory.make(rawMovie.getString(POSTER_PATH_FIELD), posterThumbnailSize);
        Image backdropLarge = TheMovieDBImageFactory.make(rawMovie.getString(BACKDROP_PATH_FIELD), backDropSize);

        return new Movie(
                rawMovie.getLong(VOTE_AVERAGE_FIELD),
                rawMovie.getString(TITLE_FIELD),
                posterThumbnail,
                rawMovie.getString(ORIGINAL_TITLE_FIELD),
                backdropLarge,
                rawMovie.getString(OVERVIEW_FIELD),
                rawMovie.getString(RELEASE_DATE_FIELD)
        );

    }

    /**
     * Parse an array of movies in json
     *
     * @param movies the array of movies in a json array object
     * @return a list with Movie instances
     * @throws JSONException            if the rawMovie does not have some of the parsed fields
     * @throws IllegalArgumentException if the given image sizes are invalid
     */
    public static List<Movie> parseMoviesList(JSONArray movies, String posterThumbnailSize, String backDropSize) throws JSONException, IllegalArgumentException {
        List<Movie> topRatedMovies = new ArrayList<>(0);
        JSONObject rawMovie;
        for (int index = 0; index < movies.length(); index++) {
            rawMovie = movies.getJSONObject(index);
            topRatedMovies.add(TheMovieDBParser.parseMovie(rawMovie, posterThumbnailSize, backDropSize));
        }
        return topRatedMovies;
    }

}
