package com.artenesnogueira.popularmovies;

/**
 * An immutable movie object
 */
class Movie {

    private final long voteAverage;
    private final String title;
    private final String posterPath;
    private final String originalTitle;
    private final String backdropPath;
    private final String overview;
    private final String releaseDate;

    public Movie(long voteAverage, String title, String posterPath, String originalTitle, String backdropPath, String overview, String releaseDate) {
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public long getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
