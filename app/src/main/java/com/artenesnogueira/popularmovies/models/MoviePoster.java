package com.artenesnogueira.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A movie poster that has a path to a poster
 * and a id that identifies the movie of that poster
 */
public class MoviePoster implements Parcelable {

    private final String id;
    private final String posterPath;

    public MoviePoster(String id, String posterPath) {
        this.id = id;
        this.posterPath = posterPath;
    }

    public String getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    //*************************************
    // Parcelable interface implementation
    // ************************************

    private MoviePoster(Parcel in) {
        id = in.readString();
        posterPath = in.readString();
    }

    public static final Creator<MoviePoster> CREATOR = new Creator<MoviePoster>() {
        @Override
        public MoviePoster createFromParcel(Parcel in) {
            return new MoviePoster(in);
        }

        @Override
        public MoviePoster[] newArray(int size) {
            return new MoviePoster[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(posterPath);
    }

}
