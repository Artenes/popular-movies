package com.artenesnogueira.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.artenesnogueira.popularmovies.db.FavoriteMoviePoster;
import com.artenesnogueira.popularmovies.db.LocalDB;
import com.artenesnogueira.popularmovies.models.Dependencies;
import com.artenesnogueira.popularmovies.models.Movie;
import com.artenesnogueira.popularmovies.models.MovieBitmap;
import com.artenesnogueira.popularmovies.models.MovieDetailViewState;
import com.artenesnogueira.popularmovies.views.LoadMovieDetailTask;
import com.artenesnogueira.popularmovies.views.SaveMovieImagesTask;
import com.artenesnogueira.popularmovies.views.SwapMovieFavoriteTask;

/**
 * ViewModel for DetailsActivity
 */
@SuppressWarnings("ConstantConditions")
public class DetailsViewModel extends AndroidViewModel {

    private final MutableLiveData<MovieDetailViewState> mCurrentState = new MutableLiveData<>();

    private MovieBitmap cachedPoster;
    private MovieBitmap cachedBackdrop;

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

        return mCurrentState.getValue().getMovie() != null && mCurrentState.getValue().getMovie().isFavorite();

    }

    public void swapFavorite() {

        new SwapMovieFavoriteTask(LocalDB.get(getApplication()), mCurrentState)
                .execute(mCurrentState.getValue().getMovie());

    }

    public void cacheImages(Bitmap poster, Bitmap backdrop) {

        if (cachedPoster != cachedBackdrop) {
            return;
        }

        Movie movie = mCurrentState.getValue().getMovie();

        if (cachedBackdrop == null) {

            cachedBackdrop = new MovieBitmap(movie.getId(), movie.getBackdrop(),
                    FavoriteMoviePoster.BACKDROP, backdrop);

        }

        if (cachedPoster == null) {

            cachedPoster = new MovieBitmap(movie.getId(), movie.getPoster(),
                    FavoriteMoviePoster.POSTER_THUMBNAIL, poster);

        }

        new SaveMovieImagesTask(getApplication(), LocalDB.get(getApplication()))
                .execute(cachedPoster, cachedBackdrop);

    }

    /**
     *  Load a movie by the given id
     *
     * @param id the movie`s id
     */
    private void load(String id) {

        mCurrentState.setValue(MovieDetailViewState.makeLoadingState(id));

        new LoadMovieDetailTask(
                Dependencies.getRepository(),
                LocalDB.get(getApplication()),
                mCurrentState).execute(id);

    }

}