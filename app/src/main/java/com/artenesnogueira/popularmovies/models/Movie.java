package com.artenesnogueira.popularmovies.models;

import java.util.List;

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
    private final List<YoutubeVideo> videos;
    private final List<MovieReview> reviews;

    public Movie(String id, long voteAverage, String title, String poster, String originalTitle, String backdrop, String overview, String releaseDate, boolean isFavorite, List<YoutubeVideo> videos, List<MovieReview> reviews) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.poster = poster;
        this.originalTitle = originalTitle;
        this.backdrop = backdrop;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.isFavorite = isFavorite;
        this.videos = videos;
        this.reviews = reviews;
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
        return new Movie(id, voteAverage, title, poster, originalTitle, backdrop, overview, releaseDate, isFavorite, videos, reviews);
    }

    public List<YoutubeVideo> getVideos() {
        return videos;
    }

    public List<MovieReview> getReviews() {
        return reviews;
    }

}
