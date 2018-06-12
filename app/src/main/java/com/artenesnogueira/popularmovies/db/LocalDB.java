package com.artenesnogueira.popularmovies.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavoriteMovie.class, FavoriteMoviePoster.class}, version = 3)
public abstract class LocalDB extends RoomDatabase{

    private static final String DB_NAME = "popularmovies.db";
    private static LocalDB instance;

    public static LocalDB get(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, LocalDB.class, DB_NAME)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract FavoriteMovieDAO favoriteMovieDAO();

    public abstract FavoriteMoviePosterDAO favoriteMoviePosterDAO();

}
