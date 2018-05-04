package com.artenesnogueira.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * An immutable movie object
 */
@SuppressWarnings("unused")
public class Movie implements Parcelable {

    private final long voteAverage;
    private final String title;
    private final Image poster;
    private final String originalTitle;
    private final Image backdrop;
    private final String overview;
    private final String releaseDate;

    public Movie(long voteAverage, String title, Image poster, String originalTitle, Image backdrop, String overview, String releaseDate) {
        this.voteAverage = voteAverage;
        this.title = title;
        this.poster = poster;
        this.originalTitle = originalTitle;
        this.backdrop = backdrop;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public long getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public Image getPoster() {
        return poster;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Image getBackdrop() {
        return backdrop;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    private Movie(Parcel in) {
        voteAverage = in.readLong();
        title = in.readString();
        poster = in.readParcelable(Image.class.getClassLoader());
        originalTitle = in.readString();
        backdrop = in.readParcelable(Image.class.getClassLoader());
        overview = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(voteAverage);
        dest.writeString(title);
        dest.writeParcelable(poster, flags);
        dest.writeString(originalTitle);
        dest.writeParcelable(poster, flags);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

}
