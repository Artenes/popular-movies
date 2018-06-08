package com.artenesnogueira.popularmovies.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favorite_movie_posters")
public class FavoriteMoviePoster {

    @PrimaryKey
    @NonNull
    public String movie_id;

    @NonNull
    public String path;

}
