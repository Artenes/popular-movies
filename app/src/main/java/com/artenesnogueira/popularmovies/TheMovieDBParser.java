package com.artenesnogueira.popularmovies;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser to convert JSONObjects to Movies objects
 */
class TheMovieDBParser {

    private static final Uri THUMBNAIL_IMAGE_PATH = Uri.parse("http://image.tmdb.org/t/p/w185");
    private static final Uri LARGE_IMAGE_PATH = Uri.parse("http://image.tmdb.org/t/p/w780");

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
     * @throws JSONException if the rawMovie does not have some of the parsed fields
     */
    private static Movie parseMovie(JSONObject rawMovie) throws JSONException {

        //this removes the first slash that appears in the relative path
        //if we left this here we will not be able to properly build the
        //uri to the image, the uri class will encode the slash in the path
        String thumbnailRelativePath = rawMovie.getString(POSTER_PATH_FIELD).replaceFirst("/", "");
        String backdropRelativePath = rawMovie.getString(BACKDROP_PATH_FIELD).replaceFirst("/", "");

        String posterThumbnailURL = THUMBNAIL_IMAGE_PATH.buildUpon().appendPath(thumbnailRelativePath).build().toString();
        String backdropLargeURL = LARGE_IMAGE_PATH.buildUpon().appendPath(backdropRelativePath).build().toString();

        return new Movie(
                rawMovie.getLong(VOTE_AVERAGE_FIELD),
                rawMovie.getString(TITLE_FIELD),
                posterThumbnailURL,
                rawMovie.getString(ORIGINAL_TITLE_FIELD),
                backdropLargeURL,
                rawMovie.getString(OVERVIEW_FIELD),
                rawMovie.getString(RELEASE_DATE_FIELD)
        );

    }

    /**
     * Parse an array of movies in json
     *
     * @param movies the array of movies in a json array object
     * @return a list with Movie instances
     * @throws JSONException if the rawMovie does not have some of the parsed fields
     */
    public static List<Movie> parseMoviesList(JSONArray movies) throws JSONException {
        List<Movie> topRatedMovies = new ArrayList<>(0);
        JSONObject rawMovie;
        for (int index = 0; index < movies.length(); index++) {
            rawMovie = movies.getJSONObject(index);
            topRatedMovies.add(TheMovieDBParser.parseMovie(rawMovie));
        }
        return topRatedMovies;
    }

}
