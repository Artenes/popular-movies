package com.artenesnogueira.popularmovies.views;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artenesnogueira.popularmovies.R;
import com.artenesnogueira.popularmovies.models.Filter;
import com.artenesnogueira.popularmovies.models.Movie;
import com.artenesnogueira.popularmovies.models.MoviesRepository;
import com.artenesnogueira.popularmovies.models.PostersView;
import com.artenesnogueira.popularmovies.themoviedb.TheMovieDBRepository;
import com.artenesnogueira.popularmovies.utilities.HTTPURLConnectionClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Activity of the application, displays a grid of movies posters
 */
public class MainActivity extends AppCompatActivity implements PostersView, MoviePosterAdapter.OnPosterClicked {

    private RecyclerView mMoviesPostersRecyclerView;
    private MoviePosterAdapter mMovieAdapter;
    private GridLayoutManager mGridLayoutManager;

    private LinearLayout mErrorMessage;
    private TextView mLoadingMessageTextView;
    private ProgressBar mLoadingProgressBarr;

    //these variables are used to keep the state of the UI
    private List<Movie> mCurrentMovieList = new ArrayList<>(0);
    private int mCurrentListPosition = 0;
    private Filter mCurrentFilter = Filter.POPULAR;
    private MenuItem mFilterMenuItem;
    private Bundle mPreviousState = new Bundle();

    //the repository containing the movies to load
    private final MoviesRepository mRepository = new TheMovieDBRepository(new HTTPURLConnectionClient());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreviousState = savedInstanceState;

        mMoviesPostersRecyclerView = findViewById(R.id.rv_movies_list);
        mErrorMessage = findViewById(R.id.tv_error_message);
        mLoadingMessageTextView = findViewById(R.id.tv_loading_message);
        mLoadingProgressBarr = findViewById(R.id.pb_loading);
        findViewById(R.id.bt_try_again).setOnClickListener(view -> reload(mCurrentFilter));

        mMovieAdapter = new MoviePosterAdapter(this);

        //we use an integer resource for this so it can be
        //changed dynamically depending on screen orientation
        mGridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.AMOUNT_OF_COLUMNS_IN_POSTER_GRID));

        mMoviesPostersRecyclerView.setAdapter(mMovieAdapter);
        mMoviesPostersRecyclerView.setLayoutManager(mGridLayoutManager);

        //restores the grid layout manager to its previous position
        if (savedInstanceState != null && savedInstanceState.get(KEY_CURRENT_LIST_POSITION) != null) {
            mCurrentListPosition = savedInstanceState.getInt(KEY_CURRENT_LIST_POSITION);
        }

        //restores the previous filter selected
        if (savedInstanceState != null && savedInstanceState.get(KEY_CURRENT_FILTER) != null) {
            mCurrentFilter = (Filter) savedInstanceState.getSerializable(KEY_CURRENT_FILTER);
        }

    }

    @Override
    public void showLoading() {
        mCurrentListPosition = 0;
        mFilterMenuItem.setVisible(false);
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

        mFilterMenuItem.setVisible(true);

        mMovieAdapter.setData(movies);
        mGridLayoutManager.scrollToPosition(mCurrentListPosition);

        mMoviesPostersRecyclerView.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);

        if (mCurrentFilter == Filter.TOP_RATED) {
            setTitle(R.string.top_rated_movies);
        } else {
            setTitle(R.string.popular_movies);
        }
    }

    @Override
    public void showError() {
        mFilterMenuItem.setVisible(false);
        mMoviesPostersRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
        mLoadingProgressBarr.setVisibility(View.INVISIBLE);
        mLoadingMessageTextView.setVisibility(View.INVISIBLE);
    }

    private void reload(Filter filter) {
        new LoadMoviesTask(mRepository, this).execute(filter);
    }

    @Override
    public void onPosterClicked(Movie movie) {
        DetailsActivity.start(this, movie);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            //this can possibly trows a ClassCastException since it can be any other kind of list
            outState.putParcelableArrayList(KEY_MOVIES_LIST, (ArrayList<? extends Parcelable>) mCurrentMovieList);
            //this line throws nothing. it is inside the try to look more nice than being outside the try block
            outState.putInt(KEY_CURRENT_LIST_POSITION, mGridLayoutManager.findFirstVisibleItemPosition());
            outState.putSerializable(KEY_CURRENT_FILTER, mCurrentFilter);
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        mFilterMenuItem = menu.findItem(R.id.action_filter);

        //we pass the previous state to the task so it can
        //decides if it needs to load something or not
        //we start the load task here so the menu item that controls the filter
        //can be instantiated and we don`t get any race condition between the task and the view
        new LoadMoviesTask(mRepository, this).restoreStateAndExecute(mPreviousState);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                mCurrentFilter = Filter.POPULAR;
                break;
            case R.id.action_top_rated:
                mCurrentFilter = Filter.TOP_RATED;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        reload(mCurrentFilter);
        return true;
    }

}
