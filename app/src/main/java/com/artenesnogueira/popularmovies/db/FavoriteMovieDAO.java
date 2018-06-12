package com.artenesnogueira.popularmovies.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
@Dao
public interface FavoriteMovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FavoriteMovie movie);

    @Delete
    void delete(FavoriteMovie movie);

    @Query("SELECT * FROM favorite_movies WHERE _id = :id")
    FavoriteMovie getMovie(String id);

    @Query("SELECT favorite_movies._id as _id, favorite_movie_posters.path as path FROM favorite_movies INNER JOIN favorite_movie_posters ON favorite_movie_posters.movie_id = favorite_movies._id WHERE favorite_movie_posters.type = :type")
    LiveData<List<FavoritePoster>> getAllWithImage(int type);

}
