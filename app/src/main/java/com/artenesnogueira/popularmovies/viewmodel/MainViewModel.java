package com.artenesnogueira.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.artenesnogueira.popularmovies.models.Dependecies;
import com.artenesnogueira.popularmovies.models.Filter;
import com.artenesnogueira.popularmovies.models.PosterViewState;
import com.artenesnogueira.popularmovies.views.LoadMoviesTask;

/**
 * ViewModel for MainActivity
 */
public class MainViewModel extends ViewModel {

    private MutableLiveData<PosterViewState> mCurrentState = new MutableLiveData<>();

    //the first filter to shown when the screen is open after a fresh start
    private static final Filter DEFAULT_FILTER = Filter.POPULAR;

    public LiveData<PosterViewState> watchState() {

        //the default state is to start loading the default filter
        //this only happens if we did not have a state previously
        reload();

        return mCurrentState;

    }

    /**
     * Load popular movies
     */
    public void loadPopular() {

        load(Filter.POPULAR);

    }

    /**
     * Load top rated movies
     */
    public void loadTopRated() {

        load(Filter.TOP_RATED);

    }

    /**
     * Reload the current state if one is available
     * if not, load the default filter
     */
    public void reload() {

        PosterViewState state = mCurrentState.getValue();

        Filter filter = state == null ? DEFAULT_FILTER : state.getFilter();

        load(filter);

    }

    /**
     * Load the given filter in a new state
     *
     * @param filter the filter to load
     */
    private void load(Filter filter) {

        mCurrentState.setValue(PosterViewState.makeLoadingState(filter));

        new LoadMoviesTask(Dependecies.getRepository(), mCurrentState).execute(filter);

    }

}