package com.artenesnogueira.popularmovies.models;

import java.io.IOException;
import java.util.List;

/**
 * A repository that hold movies
 */
public interface MoviesRepository {

    //helper member to define witch filter to use by default
    Filter DEFAULT_FILTER = Filter.POPULAR;

    /**
     * Get a list of movie posters by filter
     *
     * @param filter the filter to fetch the movies
     * @return filtered posters movies
     * @throws IOException in case of error while accessing the repository
     */
    List<MoviePoster> getMoviesPostersByFilter(Filter filter) throws IOException;

    /**
     * Fetch the details from a movie
     *
     * @param id the id of the movie
     * @return the movie details
     * @throws IOException in case of error while accessing the repository
     */
    Movie getMovieDetails(String id) throws IOException;

    /**
     * Get a list of movies by filter
     *
     * @param filter the filter to fetch the movies
     * @return filtered movies
     * @throws IOException in case of error while accessing the repository
     */
    List<Movie> getMoviesByFilter(Filter filter) throws IOException;

}
