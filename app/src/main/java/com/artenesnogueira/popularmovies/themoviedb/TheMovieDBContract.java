package com.artenesnogueira.popularmovies.themoviedb;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.artenesnogueira.popularmovies.BuildConfig;

/**
 * Contract that defines attributes from responses
 * and ways to generate/retrieve some data, e.g. how
 * to fetch small and big images
 */
abstract class TheMovieDBContract {

    //the base path used to fetch movie details
    private static final Uri MOVIE_URI = Uri.parse("https://api.themoviedb.org/3/movie?api_key=" + BuildConfig.POPULAR_MOVIES_THE_MOVIE_DB_API_KEY);
    private static final String VIDEOS_PATH = "videos";
    private static final String REVIEWS_PATH = "reviews";

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
    static final String KEY_FIELD = "key";
    static final String NAME_FIELD = "name";
    static final String TYPE_FIELD = "type";
    static final String AUTHOR_FIELD = "author";
    static final String CONTENT_FIELD = "content";
    static final String SITE_FIELD = "site";

    static final String YOUTUBE_SITE = "YouTube";

    private TheMovieDBContract() {}

    static Uri getMovieDetailsUri(@NonNull String id) {

        return MOVIE_URI.buildUpon().appendPath(id).build();

    }

    static Uri getMovieVideosUri(@NonNull String id) {

        return getMovieDetailsUri(id).buildUpon().appendPath(VIDEOS_PATH).build();

    }

    static Uri getMovieReviewsUri(@NonNull String id) {

        return getMovieDetailsUri(id).buildUpon().appendPath(REVIEWS_PATH).build();

    }

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
