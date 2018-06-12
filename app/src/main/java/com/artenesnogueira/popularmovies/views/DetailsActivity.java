package com.artenesnogueira.popularmovies.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.artenesnogueira.popularmovies.models.YoutubeVideo;
import com.artenesnogueira.popularmovies.viewmodel.DetailsViewModel;
import com.squareup.picasso.Picasso;

/**
 * Activity that displays the details of a movie
 */
public class DetailsActivity extends AppCompatActivity implements View, YoutubeVideosAdapter.OnVideoClicked {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final String KEY_MOVIE_ID = "movie";

    private DetailsViewModel mViewModel;

    private CoordinatorLayout mContainerLayout;
    private ImageView mPosterImageView;
    private ImageView mBackdropImageView;
    private RatingBar mUserVoteRatingBar;
    private TextView mReleaseDateTextView;
    private TextView mPlotTextView;
    private LinearLayout mErrorMessage;
    private LinearLayout mTrailersLayout;
    private LinearLayout mReviewsLayout;
    private TextView mLoadingMessage;
    private ProgressBar mLoadingProgressBar;
    private CollapsingToolbarLayout mTollBar;

    private YoutubeVideosAdapter mTrailersAdapter;
    private ReviewsAdapter mReviewsAdapter;

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

        mViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

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

        mTrailersLayout = findViewById(R.id.videos_view);
        mReviewsLayout = findViewById(R.id.reviews_view);

        RecyclerView trailersRecyclerView = findViewById(R.id.rv_trailers);
        RecyclerView reviewsRecyclerView = findViewById(R.id.rv_reviews);

        mTrailersAdapter = new YoutubeVideosAdapter(this, this);
        mReviewsAdapter = new ReviewsAdapter();

        trailersRecyclerView.setHasFixedSize(true);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        trailersRecyclerView.setAdapter(mTrailersAdapter);

        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewsRecyclerView.setAdapter(mReviewsAdapter);

        //enable drawing cache so we can get its bitmap later to cache if necessary
        mPosterImageView.setDrawingCacheEnabled(true);
        mBackdropImageView.setDrawingCacheEnabled(true);

        findViewById(R.id.bt_try_again).setOnClickListener(view -> mViewModel.reload());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mViewModel.watchState(id).observe(this, this::render);

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

        if (movie.getVideos().size() > 0) {
            mTrailersLayout.setVisibility(android.view.View.VISIBLE);
            mTrailersAdapter.setData(movie.getVideos());
        } else {
            mTrailersLayout.setVisibility(android.view.View.INVISIBLE);
        }

        if (movie.getReviews().size() > 0) {
            mReviewsLayout.setVisibility(android.view.View.VISIBLE);
            mReviewsAdapter.setData(movie.getReviews());
        } else {
            mReviewsLayout.setVisibility(android.view.View.INVISIBLE);
        }

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
        supportInvalidateOptionsMenu();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_activity, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int icon = mViewModel.isFavorite() ? R.drawable.ic_is_favorite_24dp : R.drawable.ic_not_favorite_24dp;
        menu.findItem(R.id.action_favorite).setIcon(icon);
        menu.findItem(R.id.action_share).setVisible(mViewModel.hasTrailers());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                mViewModel.swapFavorite();
                //cache the images already loaded in the image views
                //so we don`t need to download them again
                mViewModel.cacheImages(mPosterImageView.getDrawingCache(),
                        mBackdropImageView.getDrawingCache());
                return true;
            case R.id.action_share:
                mViewModel.shareFirstTrailer();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //destroy the cache for the image views
        mPosterImageView.destroyDrawingCache();
        mBackdropImageView.destroyDrawingCache();
    }

    @Override
    public void onVideoClicked(YoutubeVideo video) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.getId()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getUrl()));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException exception) {
            //in case the user does not have the youtube app installed
            //we launch the web browser or any other app that can handle the action view intent
            startActivity(webIntent);
        }
    }

}