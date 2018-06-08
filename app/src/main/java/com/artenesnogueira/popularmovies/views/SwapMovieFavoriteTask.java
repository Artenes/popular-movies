package com.artenesnogueira.popularmovies.views;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.artenesnogueira.popularmovies.db.FavoriteMovie;
import com.artenesnogueira.popularmovies.db.FavoriteMovieDAO;
import com.artenesnogueira.popularmovies.db.LocalDB;
import com.artenesnogueira.popularmovies.models.Movie;
import com.artenesnogueira.popularmovies.models.MovieDetailViewState;

/**
 * Add or remove a movie from the favorites
 *
 * Needs to pass the movie instance for the execute method
 */
public class SwapMovieFavoriteTask extends AsyncTask<Movie, Void, MovieDetailViewState> {

    private final LocalDB mDb;
    private final MutableLiveData<MovieDetailViewState> mState;

    public SwapMovieFavoriteTask(LocalDB db, MutableLiveData<MovieDetailViewState> state) {
        mDb = db;
        mState = state;
    }

    @Override
    protected MovieDetailViewState doInBackground(Movie... movies) {

        Movie movie = movies[0];
        FavoriteMovieDAO dao = mDb.favoriteMovieDAO();

        FavoriteMovie favoriteMovie = dao.getMovie(movie.getId());

        if (favoriteMovie != null) {
            dao.delete(favoriteMovie);
            return MovieDetailViewState.makeViewMovieState(movie.setFavorite(false), movie.getId());
        }

        favoriteMovie = new FavoriteMovie(
                movie.getId(),
                movie.getTitle(),
                movie.getOverview(),
                movie.getVoteAverage(),
                movie.getReleaseDate()
        );

        mDb.favoriteMovieDAO().insert(favoriteMovie);

        return MovieDetailViewState.makeViewMovieState(movie.setFavorite(true), movie.getId());

    }

    @Override
    protected void onPostExecute(MovieDetailViewState state) {
        mState.setValue(state);
    }

}