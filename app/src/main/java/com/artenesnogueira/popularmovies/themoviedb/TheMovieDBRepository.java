package com.artenesnogueira.popularmovies.themoviedb;

import android.net.Uri;

import com.artenesnogueira.popularmovies.BuildConfig;
import com.artenesnogueira.popularmovies.models.Filter;
import com.artenesnogueira.popularmovies.models.HTTPClient;
import com.artenesnogueira.popularmovies.models.Movie;
import com.artenesnogueira.popularmovies.models.MoviePoster;
import com.artenesnogueira.popularmovies.models.MoviesRepository;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A repository to fetch movies from The Movie DB service
 */
public class TheMovieDBRepository implements MoviesRepository {

    //the base uri to make requests with the api key already appended for convenience
    private static final Uri BASE_URI = Uri.parse("https://api.themoviedb.org/3/movie?api_key=" + BuildConfig.POPULAR_MOVIES_THE_MOVIE_DB_API_KEY);

    //this hash maps the filters available in the model
    //to the ones that exists in TheMovieDB API
    //see static code below for initialization
    private static final Map<Filter, String> filterMapping = new HashMap<>();

    private HTTPClient client;

    public TheMovieDBRepository(HTTPClient client) {
        this.client = client;
    }

    @Override
    public List<MoviePoster> getMoviesPostersByFilter(Filter filter) throws IOException {

        //first we have to get an URL from an URI
        Uri uri = BASE_URI.buildUpon().appendPath(filterMapping.get(filter)).build();

        //then we can make the request to get the json response
        String rawJSONResponse = client.get(uri.toString());

        List<MoviePoster> posters;
        try {
            posters = MoviePosterParser.parse(rawJSONResponse);
        } catch (JSONException exception) {
            throw new IOException("Error while parsing the response");
        }

        return posters;

    }

    @Override
    public Movie getMovieDetails(String id) throws IOException {

        //first we have to get an URL from an URI
        Uri uri = BASE_URI.buildUpon().appendPath(id).build();

        //then we can make the request to get the json response
        String rawJSONResponse = client.get(uri.toString());

        Movie movie;
        try {
            movie = MovieDetailParser.parse(rawJSONResponse);
        } catch (JSONException exception) {
            throw new IOException("Error while parsing the response");
        }

        return movie;

    }

    @Override
    public List<Movie> getMoviesByFilter(Filter filter) throws IOException {

        //first we have to get an URL from an URI
        Uri uri = BASE_URI.buildUpon().appendPath(filterMapping.get(filter)).build();
        //then we can make the request to get the json response
        String rawJSONResponse = client.get(uri.toString());

        List<Movie> movies;
        try {
            movies = MoviesParser.parse(rawJSONResponse);
        } catch (JSONException exception) {
            throw new IOException("Error while parsing the response");
        }

        return movies;

    }

    static {
        //initialize the mapping from available filters in the model
        //to the ones that exists in TheMovieDB API
        filterMapping.put(Filter.TOP_RATED, "top_rated");
        filterMapping.put(Filter.POPULAR, "popular");
    }

}
