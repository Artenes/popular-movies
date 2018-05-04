package com.artenesnogueira.popularmovies.repositories;

import com.artenesnogueira.popularmovies.models.Filter;
import com.artenesnogueira.popularmovies.models.Movie;

import java.io.IOException;
import java.util.List;

/**
 * A repository that hold movies
 */
public interface MoviesRepository {

    //helper member to define witch filter to use by default
    Filter DEFAULT_FILTER = Filter.POPULAR;

    /**
     * Get a list of movies by filter
     *
     * @param filter the filter to fetch the movies
     * @return filtered movies
     * @throws IOException in case of error while accessing the repository
     */
    List<Movie> getMoviesByFilter(Filter filter) throws IOException;

}
