package com.artenesnogueira.popularmovies.models;

import com.artenesnogueira.popularmovies.themoviedb.TheMovieDBRepository;
import com.artenesnogueira.popularmovies.utilities.HTTPURLConnectionClient;

/**
 * Singleton class to retrieve some common dependencies of the app
 */
public final class Dependencies {

    /**
     * The main repository to fetch movies from
     */
    private static MoviesRepository repository;

    /**
     * Get the main repository implementation to fetch movies from
     *
     * @return a MovieRepository implementation
     */
    public static MoviesRepository getRepository() {
        if (repository == null) {
            repository = new TheMovieDBRepository(new HTTPURLConnectionClient());
        }
        return repository;
    }

    private Dependencies() {
    }

}