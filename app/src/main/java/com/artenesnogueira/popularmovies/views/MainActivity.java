package com.artenesnogueira.popularmovies.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artenesnogueira.popularmovies.R;
import com.artenesnogueira.popularmovies.models.Filter;
import com.artenesnogueira.popularmovies.models.MoviePoster;
import com.artenesnogueira.popularmovies.models.PosterViewState;
import com.artenesnogueira.popularmovies.models.State;
import com.artenesnogueira.popularmovies.models.View;
import com.artenesnogueira.popularmovies.viewmodel.MainViewModel;

import java.util.List;

/**
 * Main Activity of the application, displays a grid of movies posters
 */
public class MainActivity extends AppCompatActivity implements View, MoviePosterAdapter.OnPosterClicked {

    private RecyclerView mMoviesPostersRecyclerView;
    private MoviePosterAdapter mMovieAdapter;
    private GridLayoutManager mGridLayoutManager;

    private LinearLayout mErrorMessage;
    private TextView mLoadingMessageTextView;
    private ProgressBar mLoadingProgressBarr;

    private MainViewModel mMainViewModel;
    private boolean showFilterAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mMoviesPostersRecyclerView = findViewById(R.id.rv_movies_list);
        mErrorMessage = findViewById(R.id.tv_error_message);
        mLoadingMessageTextView = findViewById(R.id.tv_loading_message);
        mLoadingProgressBarr = findViewById(R.id.pb_loading);
        findViewById(R.id.bt_try_again).setOnClickListener(view -> mMainViewModel.reload());

        mMovieAdapter = new MoviePosterAdapter(this);

        //we use an integer resource for this so it can be
        //changed dynamically depending on screen orientation
        mGridLayoutManager = new GridLayoutManager(this, getResources()
                .getInteger(R.integer.AMOUNT_OF_COLUMNS_IN_POSTER_GRID));

        mMoviesPostersRecyclerView.setAdapter(mMovieAdapter);
        mMoviesPostersRecyclerView.setLayoutManager(mGridLayoutManager);

        //this will trigger the first load of the screen
        mMainViewModel.watchState().observe(this, this::render);

    }

    private void showLoading() {
        mMoviesPostersRecyclerView.setVisibility(android.view.View.INVISIBLE);
        mErrorMessage.setVisibility(android.view.View.INVISIBLE);
        mLoadingProgressBarr.setVisibility(android.view.View.VISIBLE);
        mLoadingMessageTextView.setVisibility(android.view.View.VISIBLE);
        supportInvalidateOptionsMenu();
    }

    private void showError() {
        mMoviesPostersRecyclerView.setVisibility(android.view.View.INVISIBLE);
        mErrorMessage.setVisibility(android.view.View.VISIBLE);
        mLoadingProgressBarr.setVisibility(android.view.View.INVISIBLE);
        mLoadingMessageTextView.setVisibility(android.view.View.INVISIBLE);
        supportInvalidateOptionsMenu();
    }

    private void showMovies(Filter filter, List<MoviePoster> movies) {
        mLoadingProgressBarr.setVisibility(android.view.View.INVISIBLE);
        mLoadingMessageTextView.setVisibility(android.view.View.INVISIBLE);
        mErrorMessage.setVisibility(android.view.View.INVISIBLE);
        mMoviesPostersRecyclerView.setVisibility(android.view.View.VISIBLE);

        mMovieAdapter.setData(movies);

        supportInvalidateOptionsMenu();
        if (filter == Filter.TOP_RATED) {
            setTitle(R.string.top_rated_movies);
        } else {
            setTitle(R.string.popular_movies);
        }
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
        menu.findItem(R.id.action_filter).setVisible(showFilterAction);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                mMainViewModel.loadPopular();
                return true;
            case R.id.action_top_rated:
                mMainViewModel.loadTopRated();
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
        PosterViewState currentSate = (PosterViewState) state;

        //this has to be done outside the other ifs so it
        //can have its value updated in any state change
        showFilterAction = currentSate.getMovies() != null;

        if (currentSate.isLoading()) {
            showLoading();
            return;
        }

        if (currentSate.hasError()) {
            showError();
            return;
        }

        if (currentSate.getMovies() != null) {
            showMovies(currentSate.getFilter(), currentSate.getMovies());
            return;
        }

        //by default show the error state
        showError();

    }

}
