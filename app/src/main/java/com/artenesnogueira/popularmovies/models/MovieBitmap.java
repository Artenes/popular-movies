package com.artenesnogueira.popularmovies.models;

import android.graphics.Bitmap;

public class MovieBitmap {

    private final String movieId;
    private final String url;
    private final int type;
    private final Bitmap bitmap;

    public MovieBitmap(String movieId, String url, int type, Bitmap bitmap) {
        this.movieId = movieId;
        this.url = url;
        this.type = type;
        this.bitmap = bitmap;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getUrl() {
        return url;
    }

    public int getType() {
        return type;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}
