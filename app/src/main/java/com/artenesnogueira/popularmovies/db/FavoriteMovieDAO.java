package com.artenesnogueira.popularmovies.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface FavoriteMovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FavoriteMovie movie);

    @Delete
    void delete(FavoriteMovie movie);

    @Query("SELECT * FROM favorite_movies WHERE _id = :id")
    FavoriteMovie getMovie(String id);

    @Query("SELECT count(_id) > 0 FROM favorite_movies WHERE _id = :id")
    boolean isFavorite(String id);

}
