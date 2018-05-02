package com.artenesnogueira.popularmovies;

import java.io.IOException;
import java.util.List;

/**
 * A repository that hold movies
 */
interface MoviesRepository {

    /**
     * Get a list of top rated movies
     *
     * @return top rated movies
     * @throws IOException in case of error while accessing the repository
     */
    List<Movie> getTopRatedMovies() throws IOException;

    /**
     * Get a list of popular movies
     *
     * @return popular movies
     * @throws IOException in case of error while accessing the repository
     */
    List<Movie> getPopularMovies() throws IOException;

}
