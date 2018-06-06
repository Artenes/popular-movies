package com.artenesnogueira.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.artenesnogueira.popularmovies.models.Dependecies;
import com.artenesnogueira.popularmovies.models.MovieDetailViewState;
import com.artenesnogueira.popularmovies.views.LoadMovieDetailTask;

/**
 * ViewModel for DetailsActivity
 */
public class DetailsViewModel extends ViewModel {

    private final MutableLiveData<MovieDetailViewState> mCurrentState = new MutableLiveData<>();

    public LiveData<MovieDetailViewState> watchState(String id) {

        if (mCurrentState.getValue() == null) {

            load(id);

        }

        return mCurrentState;

    }

    /**
     * Try to fetch the movie data from the repository again
     */
    public void reload() {

        load(mCurrentState.getValue().getMovieId());

    }

    /**
     *  Load a movie by the given id
     *
     * @param id the movie`s id
     */
    private void load(String id) {

        mCurrentState.setValue(MovieDetailViewState.makeLoadingState(id));

        new LoadMovieDetailTask(Dependecies.getRepository(), mCurrentState).execute(id);

    }

}