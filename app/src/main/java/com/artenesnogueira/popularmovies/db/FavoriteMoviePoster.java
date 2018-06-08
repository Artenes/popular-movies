package com.artenesnogueira.popularmovies.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favorite_movie_posters")
public class FavoriteMoviePoster {

    public static final int BACKDROP = 1;
    public static final int POSTER_THUMBNAIL = 2;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int _id;

    @NonNull
    public String movie_id;

    @NonNull
    public String path;

    @NonNull
    public int type;

    public FavoriteMoviePoster(@NonNull String movie_id, @NonNull String path, @NonNull int type) {
        this._id = 0;
        this.movie_id = movie_id;
        this.path = path;
        this.type = type;
    }

}