package com.artenesnogueira.popularmovies.views;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.artenesnogueira.popularmovies.models.Filter;
import com.artenesnogueira.popularmovies.models.MoviePoster;
import com.artenesnogueira.popularmovies.models.MoviesRepository;
import com.artenesnogueira.popularmovies.models.PosterViewState;

import java.io.IOException;
import java.util.List;

/**
 * Load some movies from a repository
 *
 * Needs to pass the Filter to the execute method
 */
public class LoadMoviesTask extends AsyncTask<Filter, Void, PosterViewState> {

    private final MoviesRepository mRepository;
    private final MutableLiveData<PosterViewState> mCurrentState;

    public LoadMoviesTask(MoviesRepository repository, MutableLiveData<PosterViewState> currentState) {
        mRepository = repository;
        mCurrentState = currentState;
    }

    @Override
    protected PosterViewState doInBackground(Filter... filters) {
        Filter filter = filters[0];

        try {
            List<MoviePoster> movies = mRepository.getMoviesPostersByFilter(filter);
            return PosterViewState.makeViewMoviesState(movies, filter);
        } catch (IOException exception) {
            exception.printStackTrace();
            return PosterViewState.makeErrorState(filter);
        }
    }

    @Override
    protected void onPostExecute(PosterViewState state) {
        mCurrentState.setValue(state);
    }

}