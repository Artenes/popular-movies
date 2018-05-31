package com.artenesnogueira.popularmovies.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artenesnogueira.popularmovies.R;
import com.artenesnogueira.popularmovies.models.Filter;
import com.artenesnogueira.popularmovies.models.MoviePoster;
import com.artenesnogueira.popularmovies.models.MoviesRepository;
import com.artenesnogueira.popularmovies.models.PosterViewState;
import com.artenesnogueira.popularmovies.models.State;
import com.artenesnogueira.popularmovies.models.View;
import com.artenesnogueira.popularmovies.themoviedb.TheMovieDBRepository;
import com.artenesnogueira.popularmovies.utilities.HTTPURLConnectionClient;

/**
 * Main Activity of the application, displays a grid of movies posters
 */
public class MainActivity extends AppCompatActivity implements View, MoviePosterAdapter.OnPosterClicked {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String KEY_STATE = "state";

    private RecyclerView mMoviesPostersRecyclerView;
    private MoviePosterAdapter mMovieAdapter;
    private GridLayoutManager mGridLayoutManager;

    private LinearLayout mErrorMessage;
    private TextView mLoadingMessageTextView;
    private ProgressBar mLoadingProgressBarr;

    private PosterViewState mCurrentState;

    //the repository containing the movies to load
    private final MoviesRepository mRepository = new TheMovieDBRepository(new HTTPURLConnectionClient());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesPostersRecyclerView = findViewById(R.id.rv_movies_list);
        mErrorMessage = findViewById(R.id.tv_error_message);
        mLoadingMessageTextView = findViewById(R.id.tv_loading_message);
        mLoadingProgressBarr = findViewById(R.id.pb_loading);
        findViewById(R.id.bt_try_again).setOnClickListener(view -> reload());

        mMovieAdapter = new MoviePosterAdapter(this);

        //we use an integer resource for this so it can be
        //changed dynamically depending on screen orientation
        mGridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.AMOUNT_OF_COLUMNS_IN_POSTER_GRID));

        mMoviesPostersRecyclerView.setAdapter(mMovieAdapter);
        mMoviesPostersRecyclerView.setLayoutManager(mGridLayoutManager);

        //if there is no saved state, this is the first time this is being opened
        //so load the default filter for movies
        if (savedInstanceState == null) {
            render(PosterViewState.makeLoadingState(Filter.POPULAR));
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.get(KEY_STATE) != null) {
            mCurrentState = savedInstanceState.getParcelable(KEY_STATE);
        }

        render(mCurrentState);

    }

    private void showLoading() {
        mMoviesPostersRecyclerView.setVisibility(android.view.View.INVISIBLE);
        mErrorMessage.setVisibility(android.view.View.INVISIBLE);
        mLoadingProgressBarr.setVisibility(android.view.View.VISIBLE);
        mLoadingMessageTextView.setVisibility(android.view.View.VISIBLE);
        invalidateOptionsMenu();
    }

    private void showError() {
        mMoviesPostersRecyclerView.setVisibility(android.view.View.INVISIBLE);
        mErrorMessage.setVisibility(android.view.View.VISIBLE);
        mLoadingProgressBarr.setVisibility(android.view.View.INVISIBLE);
        mLoadingMessageTextView.setVisibility(android.view.View.INVISIBLE);
    }

    private void showMovies() {
        mLoadingProgressBarr.setVisibility(android.view.View.INVISIBLE);
        mLoadingMessageTextView.setVisibility(android.view.View.INVISIBLE);
        mErrorMessage.setVisibility(android.view.View.INVISIBLE);
        mMoviesPostersRecyclerView.setVisibility(android.view.View.VISIBLE);

        mMovieAdapter.setData(mCurrentState.getMovies());
        mGridLayoutManager.scrollToPosition(mCurrentState.getListPosition());

        supportInvalidateOptionsMenu();
        if (mCurrentState.getFilter() == Filter.TOP_RATED) {
            setTitle(R.string.top_rated_movies);
        } else {
            setTitle(R.string.popular_movies);
        }
    }

    private void reload() {
        render(PosterViewState.makeLoadingState(mCurrentState.getFilter()));
    }

    @Override
    public void onPosterClicked(MoviePoster movie) {
        DetailsActivity.start(this, movie.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //if there are movies, there is something to filter, so we show the option to filter
        menu.findItem(R.id.action_filter).setVisible(mCurrentState.getMovies() != null);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                render(PosterViewState.makeLoadingState(Filter.POPULAR));
                return true;
            case R.id.action_top_rated:
                render(PosterViewState.makeLoadingState(Filter.TOP_RATED));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void render(State state) {

        //this method is the only entry point ot update the UI
        //since this view has 3 states, it was getting kinda ugly
        //to only use member variables to control the state
        //now the state is isolated in a class and the only thing the
        //view has to worry is to get this state and render on the screen
        //this idea was borrowed from the MVI pattern
        //the DetailsActivity does not use this method because it has only 1 state
        mCurrentState = (PosterViewState) state;

        if (mCurrentState.isLoading()) {
            showLoading();
            new LoadMoviesTask(mRepository, this).execute(mCurrentState.getFilter());
            return;
        }

        if (mCurrentState.hasError()) {
            showError();
            return;
        }

        if (mCurrentState.getMovies() != null) {
            showMovies();
            return;
        }

        //by default show the error state
        showError();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //get the current grid position and save the new state instance to the bundle
        int itemPosition = mGridLayoutManager.findFirstCompletelyVisibleItemPosition();
        Log.i(TAG, "Position saved at : " + itemPosition);
        PosterViewState state = mCurrentState.moveToPosition(itemPosition);
        outState.putParcelable(KEY_STATE, state);
    }

}
