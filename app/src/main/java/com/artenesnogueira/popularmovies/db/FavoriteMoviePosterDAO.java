package com.artenesnogueira.popularmovies.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteMoviePosterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteMoviePoster movie);

    @Query("SELECT * FROM favorite_movie_posters WHERE movie_id = :movieId")
    List<FavoriteMoviePoster> getImages(String movieId);

}
