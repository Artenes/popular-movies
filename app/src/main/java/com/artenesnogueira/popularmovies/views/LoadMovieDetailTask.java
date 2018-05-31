package com.artenesnogueira.popularmovies.views;

import android.os.AsyncTask;

import com.artenesnogueira.popularmovies.models.Movie;
import com.artenesnogueira.popularmovies.models.MovieDetailViewState;
import com.artenesnogueira.popularmovies.models.MoviesRepository;
import com.artenesnogueira.popularmovies.models.View;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Load the details of a movie from a repository
 *
 * Needs to pass the movie id for the execute method
 */
class LoadMovieDetailTask extends AsyncTask<String, Void, MovieDetailViewState> {

    private final MoviesRepository mRepository;
    private final WeakReference<View> mViewReference; //have to be a weak reference in case of an Activity

    LoadMovieDetailTask(MoviesRepository repository, View view) {
        mRepository = repository;
        mViewReference = new WeakReference<>(view);
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
        View view = mViewReference.get();
        //if the view is null, there is nothing more to do
        //we are probably in a system configuration change
        if (view == null) {
            return;
        }
        view.render(state);
    }

}