package com.artenesnogueira.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.artenesnogueira.popularmovies.db.LocalDB;
import com.artenesnogueira.popularmovies.models.Dependecies;
import com.artenesnogueira.popularmovies.models.MovieDetailViewState;
import com.artenesnogueira.popularmovies.views.LoadMovieDetailTask;
import com.artenesnogueira.popularmovies.views.SwapMovieFavoriteTask;

/**
 * ViewModel for DetailsActivity
 */
public class DetailsViewModel extends AndroidViewModel {

    private final MutableLiveData<MovieDetailViewState> mCurrentState = new MutableLiveData<>();

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

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

    public boolean isFavorite() {

        if (mCurrentState.getValue().getMovie() == null) {
            return false;
        }

        return mCurrentState.getValue().getMovie().isFavorite();

    }

    public void swapFavorite() {

        new SwapMovieFavoriteTask(LocalDB.get(getApplication()), mCurrentState)
                .execute(mCurrentState.getValue().getMovie());

    }

    /**
     *  Load a movie by the given id
     *
     * @param id the movie`s id
     */
    private void load(String id) {

        mCurrentState.setValue(MovieDetailViewState.makeLoadingState(id));

        new LoadMovieDetailTask(
                Dependecies.getRepository(),
                LocalDB.get(getApplication()),
                mCurrentState).execute(id);

    }

}