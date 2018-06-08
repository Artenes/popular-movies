package com.artenesnogueira.popularmovies.views;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.artenesnogueira.popularmovies.db.FavoriteMovie;
import com.artenesnogueira.popularmovies.db.LocalDB;
import com.artenesnogueira.popularmovies.models.Movie;
import com.artenesnogueira.popularmovies.models.MovieDetailViewState;
import com.artenesnogueira.popularmovies.models.MoviesRepository;

import java.io.IOException;

/**
 * Load the details of a movie from a repository
 *
 * Needs to pass the movie id for the execute method
 */
public class LoadMovieDetailTask extends AsyncTask<String, Void, MovieDetailViewState> {

    private final MoviesRepository mRepository;
    private final LocalDB mDB;
    private final MutableLiveData<MovieDetailViewState> mState;

    public LoadMovieDetailTask(MoviesRepository repository, LocalDB db, MutableLiveData<MovieDetailViewState> state) {
        mRepository = repository;
        mDB = db;
        mState = state;
    }

    @Override
    protected MovieDetailViewState doInBackground(String... ids) {
        String id = ids[0];

        FavoriteMovie localMovie = mDB.favoriteMovieDAO().getMovie(id);

        try {
            Movie movie = mRepository.getMovieDetails(id);
            movie = movie.setFavorite(localMovie != null);
            return MovieDetailViewState.makeViewMovieState(movie, id);
        } catch (IOException exception) {
            exception.printStackTrace();
            return MovieDetailViewState.makeErrorState(id);
        }
    }

    @Override
    protected void onPostExecute(MovieDetailViewState state) {
        mState.setValue(state);
    }

}