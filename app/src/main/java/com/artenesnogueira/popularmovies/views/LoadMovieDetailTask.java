package com.artenesnogueira.popularmovies.views;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.artenesnogueira.popularmovies.db.FavoriteMovie;
import com.artenesnogueira.popularmovies.db.FavoriteMoviePoster;
import com.artenesnogueira.popularmovies.db.LocalDB;
import com.artenesnogueira.popularmovies.models.Movie;
import com.artenesnogueira.popularmovies.models.MovieDetailViewState;
import com.artenesnogueira.popularmovies.models.MoviesRepository;

import java.io.IOException;
import java.util.List;

/**
 * Load the details of a movie from a repository
 * <p>
 * Needs to pass the movie id for the execute method
 */
public class LoadMovieDetailTask extends AsyncTask<String, Void, MovieDetailViewState> {

    private final MoviesRepository mRepository;
    private final LocalDB mDB;
    private final MutableLiveData<MovieDetailViewState> mState;

    public LoadMovieDetailTask(MoviesRepository repository, LocalDB db, MutableLiveData<MovieDetailViewState> state) {
        mRepository = repository;
        mDB = db;
        mState = state;
    }

    @Override
    protected MovieDetailViewState doInBackground(String... ids) {
        String id = ids[0];

        FavoriteMovie localMovie = mDB.favoriteMovieDAO().getMovie(id);

        try {
            Movie movie = mRepository.getMovieDetails(id);
            movie = movie.setFavorite(localMovie != null);
            return MovieDetailViewState.makeViewMovieState(movie, id);
        } catch (IOException exception) {
            if (localMovie != null) {
                return MovieDetailViewState
                        .makeViewMovieState(loadMovieFromLocalDatabase(localMovie), id);
            }
            exception.printStackTrace();
            return MovieDetailViewState.makeErrorState(id);
        }
    }

    @Override
    protected void onPostExecute(MovieDetailViewState state) {
        mState.setValue(state);
    }

    /**
     * Load a movie from the local database
     *
     * @param localMovie the favorite movie instance
     * @return the movie instance
     */
    private Movie loadMovieFromLocalDatabase(FavoriteMovie localMovie) {

        String backdrop = "";
        String poster = "";
        //we get the cached images for this movie
        List<FavoriteMoviePoster> images = mDB.favoriteMoviePosterDAO().getImages(localMovie._id);

        //we have to check each image type and associate it with the right attribute
        for (FavoriteMoviePoster image : images) {
            switch (image.type) {
                case FavoriteMoviePoster.BACKDROP:
                    backdrop = image.path;
                    break;
                case FavoriteMoviePoster.POSTER_THUMBNAIL:
                    poster = image.path;
                    break;
            }
        }

        //a local movie will always be a favorite movie
        return new Movie(localMovie._id, localMovie.rating, localMovie.title,
                poster, localMovie.title, backdrop, localMovie.synopsis,
                localMovie.release_date, true);

    }
}