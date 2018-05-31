package com.artenesnogueira.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The state of the movie detail view. This is an immutable object.
 */
public class MovieDetailViewState implements Parcelable, State {

    private final String movieId;
    private final Movie movie;
    private final int scrollPosition;
    private final boolean loading;
    private final boolean hasError;

    public static MovieDetailViewState makeLoadingState(String id) {
        return new MovieDetailViewState(id, null, 0, true, false);
    }

    public static MovieDetailViewState makeErrorState(String id) {
        return new MovieDetailViewState(id, null, 0, false, true);
    }

    public static MovieDetailViewState makeViewMovieState(Movie movie, String id) {
        return new MovieDetailViewState(id, movie, 0, false, false);
    }

    public String getMovieId() {
        return movieId;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean hasError() {
        return hasError;
    }

    public MovieDetailViewState scrollToPosition(int position) {
        return new MovieDetailViewState(movieId, movie, position, loading, hasError);
    }

    private MovieDetailViewState(String movieId, Movie movie, int scrollPosition, boolean loading, boolean hasError) {
        this.movieId = movieId;
        this.movie = movie;
        this.scrollPosition = scrollPosition;
        this.loading = loading;
        this.hasError = hasError;
    }

    //Methods to implement the Parcelable interface

    private MovieDetailViewState(Parcel in) {
        movieId = in.readString();
        movie = in.readParcelable(Movie.class.getClassLoader());
        scrollPosition = in.readInt();
        loading = in.readByte() != 0;
        hasError = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieId);
        dest.writeParcelable(movie, flags);
        dest.writeInt(scrollPosition);
        dest.writeByte((byte) (loading ? 1 : 0));
        dest.writeByte((byte) (hasError ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieDetailViewState> CREATOR = new Creator<MovieDetailViewState>() {
        @Override
        public MovieDetailViewState createFromParcel(Parcel in) {
            return new MovieDetailViewState(in);
        }

        @Override
        public MovieDetailViewState[] newArray(int size) {
            return new MovieDetailViewState[size];
        }
    };

}
