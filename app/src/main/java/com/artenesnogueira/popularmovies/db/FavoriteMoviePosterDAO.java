package com.artenesnogueira.popularmovies.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface FavoriteMoviePosterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteMoviePoster movie);

    @Delete
    void delete(FavoriteMoviePoster movie);

    @Query("SELECT * FROM favorite_movie_posters WHERE movie_id = :movieId")
    FavoriteMoviePoster getPoster(String movieId);

}
