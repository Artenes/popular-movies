package com.artenesnogueira.popularmovies.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "favorite_movies")
public class FavoriteMovie {

    @PrimaryKey
    @NonNull
    public String _id;

    @NonNull
    public String title;

    @NonNull
    public String synopsis;

    @NonNull
    public long rating;

    @NonNull
    public String release_date;

    public FavoriteMovie(@NonNull String _id, @NonNull String title, @NonNull String synopsis, @NonNull long rating, @NonNull String release_date) {
        this._id = _id;
        this.title = title;
        this.synopsis = synopsis;
        this.rating = rating;
        this.release_date = release_date;
    }

}
