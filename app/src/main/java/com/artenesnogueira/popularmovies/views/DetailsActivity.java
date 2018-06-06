package com.artenesnogueira.popularmovies.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.artenesnogueira.popularmovies.R;
import com.artenesnogueira.popularmovies.models.Movie;
import com.artenesnogueira.popularmovies.models.MovieDetailViewState;
import com.artenesnogueira.popularmovies.models.State;
import com.artenesnogueira.popularmovies.models.View;
import com.artenesnogueira.popularmovies.viewmodel.DetailsViewModel;
import com.squareup.picasso.Picasso;

/**
 * Activity that displays the details of a movie
 */
public class DetailsActivity extends AppCompatActivity implements View {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final String KEY_MOVIE_ID = "movie";

    private CoordinatorLayout mContainerLayout;
    private ImageView mPosterImageView;
    private ImageView mBackdropImageView;
    private RatingBar mUserVoteRatingBar;
    private TextView mReleaseDateTextView;
    private TextView mPlotTextView;
    private LinearLayout mErrorMessage;
    private TextView mLoadingMessage;
    private ProgressBar mLoadingProgressBar;
    private CollapsingToolbarLayout mTollBar;

    //a static method is a good way to explicitly tell
    //what we want to start this activity
    public static void start(Context context, String id) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(KEY_MOVIE_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //if there is no movie, there is nothing to do
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(KEY_MOVIE_ID)) {
            Log.e(TAG, "No movie id passed in intent");
            finish();
            return;
        }

        String id = intent.getStringExtra(KEY_MOVIE_ID);

        if (id == null || id.isEmpty()) {
            Log.e(TAG, "No movie id passed in intent");
            finish();
            return;
        }

        DetailsViewModel viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

        mTollBar = findViewById(R.id.toolbar_layout);

        setSupportActionBar(findViewById(R.id.toolbar));
        mContainerLayout = findViewById(R.id.cl_container);
        mPosterImageView = findViewById(R.id.iv_movie_poster);
        mBackdropImageView = findViewById(R.id.iv_movie_backdrop);
        mUserVoteRatingBar = findViewById(R.id.rb_user_votes);
        mReleaseDateTextView = findViewById(R.id.tv_release_date);
        mPlotTextView = findViewById(R.id.tv_plot);
        mErrorMessage = findViewById(R.id.tv_error_message);
        mLoadingMessage = findViewById(R.id.tv_loading_message);
        mLoadingProgressBar = findViewById(R.id.pb_loading);
        findViewById(R.id.bt_try_again).setOnClickListener(view -> viewModel.reload());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewModel.watchState(id).observe(this, this::render);

    }

    private void bindMovieToUI(Movie movie) {

        //load the title
        mTollBar.setTitle(movie.getTitle());

        //load the backdrop image
        Picasso.get().load(movie.getBackdrop()).into(mBackdropImageView);

        //load the release date
        mReleaseDateTextView.setText(movie.getReleaseDate());

        //load the plot/overview
        mPlotTextView.setText(movie.getOverview());

        //calculates the rating. The rating goes from 0 to 10, but
        //we want to show in a 5 start rating bar, so we have to
        //calculate how many stars is the rating worth
        mUserVoteRatingBar.setRating((movie.getVoteAverage() * 5) / 10);

        //load the poster image
        Picasso.get()
                .load(movie.getPoster())
                .placeholder(R.drawable.loading_poster)
                .error(R.drawable.broken_poster)
                .into(mPosterImageView);

    }

    private void showLoading() {
        mContainerLayout.setVisibility(android.view.View.INVISIBLE);
        mErrorMessage.setVisibility(android.view.View.INVISIBLE);
        mLoadingProgressBar.setVisibility(android.view.View.VISIBLE);
        mLoadingMessage.setVisibility(android.view.View.VISIBLE);
    }

    private void showError() {
        mContainerLayout.setVisibility(android.view.View.INVISIBLE);
        mErrorMessage.setVisibility(android.view.View.VISIBLE);
        mLoadingProgressBar.setVisibility(android.view.View.INVISIBLE);
        mLoadingMessage.setVisibility(android.view.View.INVISIBLE);
    }

    private void showMovie(Movie movie) {
        mLoadingMessage.setVisibility(android.view.View.INVISIBLE);
        mLoadingProgressBar.setVisibility(android.view.View.INVISIBLE);
        mErrorMessage.setVisibility(android.view.View.INVISIBLE);

        bindMovieToUI(movie);

        mContainerLayout.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void render(State state) {

        MovieDetailViewState currentState = (MovieDetailViewState) state;

        if (currentState.isLoading()) {
            showLoading();
            return;
        }

        if (currentState.hasError()) {
            showError();
            return;
        }

        if (currentState.getMovie() != null) {
            showMovie(currentState.getMovie());
            return;
        }

        //by default show the error state
        showError();

    }

}
