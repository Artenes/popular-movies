package com.artenesnogueira.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.artenesnogueira.popularmovies.models.Movie;
import com.artenesnogueira.popularmovies.repositories.MoviesRepository;
import com.artenesnogueira.popularmovies.R;
import com.artenesnogueira.popularmovies.repositories.TheMovieDBRepository;
import com.artenesnogueira.popularmovies.models.Filter;
import com.artenesnogueira.popularmovies.network.TheMovieDBServer;
import com.artenesnogueira.popularmovies.views.PostersView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Load some movies from TheMovieDB repository
 */
public class TheMovieDBLoadMoviesTask extends AsyncTask<Filter, Void, List<Movie>> {

    private final MoviesRepository mRepository;
    private final WeakReference<PostersView> mViewReference; //have to be a weak reference in case of an Activity

    public TheMovieDBLoadMoviesTask(Context context, PostersView view) {
        String posterSize = context.getString(R.string.THEMOVIEDBAPI_POSTER_THUMBNAIL_SIZE);
        String backdropSize = context.getString(R.string.THEMOVIEDBAPI_BACKDROP_SIZE);
        mRepository = new TheMovieDBRepository(new TheMovieDBServer(), posterSize, backdropSize);
        mViewReference = new WeakReference<>(view);
    }

    /**
     * Restores the previous state and alert the view of any changes
     *
     * @param bundle  the previous state
     * @param filters the filters to be used in case of a new load
     */
    public void restoreStateAndExecute(Bundle bundle, Filter... filters) {
        //if no bundle, nothing to retrieve
        if (bundle == null) {
            execute(filters);
            return;
        }

        //we try to get the list of movies from the previous state
        List<Movie> movies = bundle.getParcelableArrayList(PostersView.KEY_MOVIES_LIST);
        //if there is none, just execute and fetch the movies again
        if (movies == null) {
            execute(filters);
            return;
        }

        //otherwise just run the onPostExecute, we don`t need to fetch anything anymore
        onPostExecute(movies);
    }

    @Override
    protected void onPreExecute() {
        PostersView view = mViewReference.get();
        //if the view is null, there is nothing more to do
        //we are probably in a system configuration change
        if (view == null) {
            return;
        }

        //before doing anything set the view to loading
        view.showLoading();
    }

    @Override
    protected List<Movie> doInBackground(Filter... filters) {
        Filter filter = MoviesRepository.DEFAULT_FILTER;
        //If a filter was provided, override the default filter
        if (filters != null && filters.length > 0 && filters[0] != null) {
            filter = filters[0];
        }
        try {
            return mRepository.getMoviesByFilter(filter);
        } catch (IOException exception) {
            exception.printStackTrace();
            //we return null to indicate that something went wrong
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        PostersView view = mViewReference.get();
        //if the view is null, there is nothing more to do
        //we are probably in a system configuration change
        if (view == null) {
            return;
        }

        //stop loading right after the results are available
        view.stopLoading();

        //if movies are null, something went wrong, so display the error message
        if (movies == null) {
            view.showError();
            return;
        }

        view.showMovies(movies);
    }

}