package com.artenesnogueira.popularmovies.views;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

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
    private final MutableLiveData<MovieDetailViewState> mState;

    public LoadMovieDetailTask(MoviesRepository repository, MutableLiveData<MovieDetailViewState> state) {
        mRepository = repository;
        mState = state;
    }

    @Override
    protected MovieDetailViewState doInBackground(String... ids) {
        String id = ids[0];

        try {
            Movie movie = mRepository.getMovieDetails(id);
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