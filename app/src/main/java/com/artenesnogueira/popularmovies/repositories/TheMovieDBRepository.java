package com.artenesnogueira.popularmovies.repositories;

import com.artenesnogueira.popularmovies.utilities.TheMovieDBParser;
import com.artenesnogueira.popularmovies.models.Filter;
import com.artenesnogueira.popularmovies.models.Movie;
import com.artenesnogueira.popularmovies.network.MoviesServer;
import com.artenesnogueira.popularmovies.network.TheMovieDBServer;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * A repository to fetch movies from The Movie DB service
 */
public class TheMovieDBRepository implements MoviesRepository {

    private final MoviesServer server;
    private final String posterThumbnailSize;
    private final String backDropSize;

    /**
     * Creates a new instance of the repository
     *
     * @param server              the server from which we will fetch the data
     * @param posterThumbnailSize the size of the poster thumbnail that will be retrieved from the server
     * @param backDropSize        the size of the back drop image that will be retrieved from the server
     */
    public TheMovieDBRepository(MoviesServer server, String posterThumbnailSize, String backDropSize) {
        this.server = server;
        this.posterThumbnailSize = posterThumbnailSize;
        this.backDropSize = backDropSize;
    }

    @Override
    public List<Movie> getMoviesByFilter(Filter filter) throws IOException {
        try {
            //we get the results from the server
            JSONArray movies = server.getMovies(filter).getJSONArray(TheMovieDBServer.RESULTS_FIELD);
            //then we parse to a list of movies
            return TheMovieDBParser.parseMoviesList(movies, posterThumbnailSize, backDropSize);
        } catch (JSONException exception) {
            throw new IOException("JSON result does not match the expected values");
        } catch (IllegalArgumentException exception) {
            throw new IOException("Invalid size provided for images poster = " + posterThumbnailSize + " backdrop = " + backDropSize);
        }
    }

}
