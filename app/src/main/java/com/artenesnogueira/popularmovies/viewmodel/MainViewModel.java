package com.artenesnogueira.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import com.artenesnogueira.popularmovies.db.FavoriteMoviePoster;
import com.artenesnogueira.popularmovies.db.FavoritePoster;
import com.artenesnogueira.popularmovies.db.LocalDB;
import com.artenesnogueira.popularmovies.models.Dependencies;
import com.artenesnogueira.popularmovies.models.Filter;
import com.artenesnogueira.popularmovies.models.MoviePoster;
import com.artenesnogueira.popularmovies.models.PosterViewState;
import com.artenesnogueira.popularmovies.views.LoadMoviesTask;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for MainActivity
 */
public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<PosterViewState> mCurrentState = new MutableLiveData<>();
    private LiveData<List<FavoritePoster>> mObservableMovies;

    //the first filter to shown when the screen is open after a fresh start
    private static final Filter DEFAULT_FILTER = Filter.POPULAR;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<PosterViewState> watchState() {

        //the default state is to start loading the default filter
        //this only happens if we did not have a state previously
        if (mCurrentState.getValue() == null) {
            reload();
        }

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
     * Load favorite movies
     */
    public void loadFavorites() {

        load(Filter.FAVORITE);

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

        //if it is a favorite filter, we have to get the data from the local database
        if (filter == Filter.FAVORITE) {
            mObservableMovies = LocalDB.get(getApplication()).favoriteMovieDAO().getAllWithImage(FavoriteMoviePoster.POSTER_THUMBNAIL);
            mObservableMovies.observeForever(mObserver);
            return;
        } else {
            //if it is not, just remove the observer
            removeFavoritesObserver();
        }

        new LoadMoviesTask(Dependencies.getRepository(), mCurrentState).execute(filter);

    }

    @Override
    protected void onCleared() {
        removeFavoritesObserver();
    }

    /**
     * Remove the observer from the live date object
     * that references favorite movies
     */
    private void removeFavoritesObserver() {
        if (mObservableMovies != null) {
            mObservableMovies.removeObserver(mObserver);
            mObservableMovies = null;
        }
    }

    /**
     * Observe for changes in favorite movies
     */
    private final Observer<List<FavoritePoster>> mObserver = favoriteMovies -> {

        //we have to convert from on type to another to pass it down to the state
        List<MoviePoster> movies = new ArrayList<>();
        if (favoriteMovies != null) {
            for (FavoritePoster movie : favoriteMovies) {
                movies.add(new MoviePoster(movie._id, movie.path));
            }
        }
        mCurrentState.setValue(PosterViewState.makeViewMoviesState(movies, Filter.FAVORITE));

    };


}