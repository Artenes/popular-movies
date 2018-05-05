package com.artenesnogueira.popularmovies.views;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artenesnogueira.popularmovies.R;
import com.artenesnogueira.popularmovies.adapters.MoviePosterAdapter;
import com.artenesnogueira.popularmovies.models.Movie;
import com.artenesnogueira.popularmovies.tasks.TheMovieDBLoadMoviesTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Activity of the application, displays a grid of movies posters
 */
public class MainActivity extends AppCompatActivity implements PostersView {

    private RecyclerView mMoviesPostersRecyclerView;
    private MoviePosterAdapter mMovieAdapter;
    private GridLayoutManager mGridLayoutManager;

    private LinearLayout mErrorMessage;
    private TextView mLoadingMessageTextView;
    private ProgressBar mLoadingProgressBarr;

    //these variables are used to keep the state of the UI
    private List<Movie> mCurrentMovieList;
    private int mCurrentListPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesPostersRecyclerView = findViewById(R.id.rv_movies_list);
        mErrorMessage = findViewById(R.id.tv_error_message);
        mLoadingMessageTextView = findViewById(R.id.tv_loading_message);
        mLoadingProgressBarr = findViewById(R.id.pb_loading);

        //the adapter needs these sizes to properly layout the images in the grid
        //we have put this in a resource file so it can be possible to dynamically
        //change these values base on device resolution, size, orientation, etc
        int posterWidth = getResources().getInteger(R.integer.POSTER_THUMBNAIL_WIDTH);
        int posterHeight = getResources().getInteger(R.integer.POSTER_THUMBNAIL_HEIGHT);
        mMovieAdapter = new MoviePosterAdapter(posterWidth, posterHeight);

        mGridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.AMOUNT_OF_COLUMNS_IN_POSTER_GRID));

        mMoviesPostersRecyclerView.setAdapter(mMovieAdapter);
        mMoviesPostersRecyclerView.setLayoutManager(mGridLayoutManager);

        //restores the grid layout manager to its previous position
        if (savedInstanceState != null && savedInstanceState.get(KEY_CURRENT_LIST_POSITION) != null) {
            mCurrentListPosition = savedInstanceState.getInt(KEY_CURRENT_LIST_POSITION);
        }

        //we pass the previous state to the task so it can
        //decides if it needs to load something or not
        new TheMovieDBLoadMoviesTask(this, this).restoreStateAndExecute(savedInstanceState);
    }

    @Override
    public void showLoading() {
        mMoviesPostersRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        mLoadingProgressBarr.setVisibility(View.VISIBLE);
        mLoadingMessageTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        mLoadingProgressBarr.setVisibility(View.INVISIBLE);
        mLoadingMessageTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        //we will keep a reference to this list
        //so we can restore it in configuration changes
        mCurrentMovieList = movies;

        mMovieAdapter.setData(movies);
        mGridLayoutManager.scrollToPosition(mCurrentListPosition);

        mMoviesPostersRecyclerView.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError() {
        mMoviesPostersRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
        mLoadingProgressBarr.setVisibility(View.INVISIBLE);
        mLoadingMessageTextView.setVisibility(View.INVISIBLE);
    }

    @SuppressWarnings("unused")
    public void reload(View view) {
        new TheMovieDBLoadMoviesTask(this, this).execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            //this can possibly trows a ClassCastException since it can be any other kind of list
            outState.putParcelableArrayList(KEY_MOVIES_LIST, (ArrayList<? extends Parcelable>) mCurrentMovieList);
            //this line throws nothing. it is inside the try to look more nice than being outside the try block
            outState.putInt(KEY_CURRENT_LIST_POSITION, mGridLayoutManager.findFirstVisibleItemPosition());
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

}
