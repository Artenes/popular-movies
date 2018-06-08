package com.artenesnogueira.popularmovies.views;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.artenesnogueira.popularmovies.BuildConfig;
import com.artenesnogueira.popularmovies.db.FavoriteMoviePoster;
import com.artenesnogueira.popularmovies.db.FavoriteMoviePosterDAO;
import com.artenesnogueira.popularmovies.db.LocalDB;
import com.artenesnogueira.popularmovies.models.MovieBitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Save the images of a movie to the disk for offline use
 *
 * Needs to pass the movie instance for the execute method
 */
public class SaveMovieImagesTask extends AsyncTask<MovieBitmap, Void, Void> {

    private static final String TAG = SaveMovieImagesTask.class.getSimpleName();

    private final LocalDB mDb;
    private final WeakReference<Context> mContextReference;

    public SaveMovieImagesTask(Context context, LocalDB db) {
        mContextReference = new WeakReference<>(context);
        mDb = db;
    }

    @Override
    protected Void doInBackground(MovieBitmap... bitmaps) {

        FavoriteMoviePosterDAO dao = mDb.favoriteMoviePosterDAO();
        File root = getRootDirectory();

        if (root == null || !root.exists()) {
            return null;
        }

        for (MovieBitmap bitmap : bitmaps) {

            File file = new File(root, bitmap.getUrl().replace("/", "_"));

            if (!file.exists()) {

                try {
                    file.createNewFile();
                } catch (IOException exception) {
                    Log.e(TAG, exception.getMessage());
                }

                try(FileOutputStream stream = new FileOutputStream(file)) {

                    bitmap.getBitmap().compress(Bitmap.CompressFormat.JPEG, 90, stream);

                    dao.insert(new FavoriteMoviePoster(
                            bitmap.getMovieId(),
                            file.getAbsolutePath(),
                            bitmap.getType()));

                } catch (IOException exception) {
                    Log.e(TAG, exception.getMessage());
                }

            }

        }

        return null;

    }

    private File getRootDirectory() {

        Context context = mContextReference.get();

        if (context == null) {
            return null;
        }

        File root = new File(context.getFilesDir()
                + File.separator + "images" + File.separator);

        root.mkdirs();

        return root;

    }

}