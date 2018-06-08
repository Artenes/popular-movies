package com.artenesnogueira.popularmovies.models;

/**
 * An immutable movie object
 */
@SuppressWarnings("unused")
public class Movie {

    private final String id;
    private final long voteAverage;
    private final String title;
    private final String poster;
    private final String originalTitle;
    private final String backdrop;
    private final String overview;
    private final String releaseDate;
    private final boolean isFavorite;

    public Movie(String id, long voteAverage, String title, String poster, String originalTitle, String backdrop, String overview, String releaseDate, boolean isFavorite) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.poster = poster;
        this.originalTitle = originalTitle;
        this.backdrop = backdrop;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public long getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public Movie setFavorite(boolean isFavorite) {
        return new Movie(id, voteAverage, title, poster, originalTitle, backdrop, overview, releaseDate, isFavorite);
    }

}
