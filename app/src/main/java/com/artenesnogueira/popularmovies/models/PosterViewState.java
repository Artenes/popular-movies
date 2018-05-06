package com.artenesnogueira.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * The state of the poster view. This is an immutable object.
 */
public class PosterViewState implements Parcelable {

    private final List<Movie> movies;
    private final int listPosition;
    private final Filter filter;
    private final boolean loading;
    private final boolean hasError;

    public static PosterViewState makeLoadingState(Filter filter) {
        return new PosterViewState(null, 0, filter, true, false);
    }

    public static PosterViewState makeErrorState(Filter filter) {
        return new PosterViewState(null, 0, filter, false, true);
    }

    public static PosterViewState makeViewMoviesState(List<Movie> movies, Filter filter) {
        return new PosterViewState(movies, 0, filter, false, false);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public int getListPosition() {
        return listPosition;
    }

    public Filter getFilter() {
        return filter;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean hasError() {
        return hasError;
    }

    public PosterViewState moveToPosition(int position) {
        return new PosterViewState(movies, position, filter, loading, hasError);
    }

    private PosterViewState(List<Movie> movies, int listPosition, Filter filter, boolean loading, boolean hasError) {
        this.movies = movies;
        this.listPosition = listPosition;
        this.filter = filter;
        this.loading = loading;
        this.hasError = hasError;
    }

    //Methods to implement the Parcelable interface

    private PosterViewState(Parcel in) {
        movies = in.createTypedArrayList(Movie.CREATOR);
        listPosition = in.readInt();
        loading = in.readByte() != 0;
        hasError = in.readByte() != 0;
        filter = (Filter) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(movies);
        dest.writeInt(listPosition);
        dest.writeByte((byte) (loading ? 1 : 0));
        dest.writeByte((byte) (hasError ? 1 : 0));
        dest.writeSerializable(filter);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PosterViewState> CREATOR = new Creator<PosterViewState>() {
        @Override
        public PosterViewState createFromParcel(Parcel in) {
            return new PosterViewState(in);
        }

        @Override
        public PosterViewState[] newArray(int size) {
            return new PosterViewState[size];
        }
    };

}
