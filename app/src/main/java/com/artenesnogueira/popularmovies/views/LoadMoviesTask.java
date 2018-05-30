package com.artenesnogueira.popularmovies.views;

import android.os.AsyncTask;

import com.artenesnogueira.popularmovies.models.Filter;
import com.artenesnogueira.popularmovies.models.MoviePoster;
import com.artenesnogueira.popularmovies.models.MoviesRepository;
import com.artenesnogueira.popularmovies.models.PosterViewState;
import com.artenesnogueira.popularmovies.models.PostersView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Load some movies from a repository
 */
class LoadMoviesTask extends AsyncTask<Filter, Void, PosterViewState> {

    private final MoviesRepository mRepository;
    private final WeakReference<PostersView> mViewReference; //have to be a weak reference in case of an Activity

    LoadMoviesTask(MoviesRepository repository, PostersView view) {
        mRepository = repository;
        mViewReference = new WeakReference<>(view);
    }

    @Override
    protected PosterViewState doInBackground(Filter... filters) {
        Filter filter = MoviesRepository.DEFAULT_FILTER;
        //If a filter was provided, override the default filter
        if (filters != null && filters.length > 0 && filters[0] != null) {
            filter = filters[0];
        }
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
        PostersView view = mViewReference.get();
        //if the view is null, there is nothing more to do
        //we are probably in a system configuration change
        if (view == null) {
            return;
        }
        view.render(state);
    }

}