package com.artenesnogueira.popularmovies.themoviedb;

import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Contract that defines attributes from responses
 * and ways to generate/retrieve some data, e.g. how
 * to fetch small and big images
 */
abstract class TheMovieDBContract {

    //the base path used to fetch the images
    private static final Uri BASE_IMAGE_PATH = Uri.parse("http://image.tmdb.org/t/p");
    private static final Uri THUMBNAIL_IMAGE_PATH = BASE_IMAGE_PATH.buildUpon().appendPath("w185").build();
    private static final Uri FULL_SIZE_IMAGE_PATH = BASE_IMAGE_PATH.buildUpon().appendPath("w780").build();

    static final String ID_FIELD = "id";
    static final String RESULTS_FIELD = "results";
    static final String VOTE_AVERAGE_FIELD = "vote_average";
    static final String TITLE_FIELD = "title";
    static final String POSTER_PATH_FIELD = "poster_path";
    static final String ORIGINAL_TITLE_FIELD = "original_title";
    static final String BACKDROP_PATH_FIELD = "backdrop_path";
    static final String OVERVIEW_FIELD = "overview";
    static final String RELEASE_DATE_FIELD = "release_date";

    private TheMovieDBContract() {}

    /**
     * Get a thumbnail for the given relative path
     *
     * @param relativePath the relative path to the image
     * @return the fully qualified uri to the image
     */
    static Uri getThumbnailImageURL(@NonNull String relativePath) {

        String path = relativePath;

        if (relativePath.startsWith("/")) {
            path = relativePath.replaceFirst("/", "");
        }

        return THUMBNAIL_IMAGE_PATH.buildUpon().appendPath(path).build();

    }

    /**
     * Get a full sized for the given relative path
     *
     * @param relativePath the relative path to the image
     * @return the fully qualified uri to the image
     */
    static Uri getFullSizeImageURL(@NonNull String relativePath) {

        String path = relativePath;

        if (relativePath.startsWith("/")) {
            path = relativePath.replaceFirst("/", "");
        }

        return FULL_SIZE_IMAGE_PATH.buildUpon().appendPath(path).build();

    }

}
