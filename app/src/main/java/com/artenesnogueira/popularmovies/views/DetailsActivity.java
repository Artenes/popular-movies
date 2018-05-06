package com.artenesnogueira.popularmovies.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.artenesnogueira.popularmovies.R;
import com.artenesnogueira.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * Activity that displays the details of a movie
 */
public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    private static final String KEY_MOVIE = "movie";
    private static final String KEY_Y_SCROLL = "yScroll";

    private Movie mMovie;

    private CoordinatorLayout mContainerLayout;
    private ImageView mPosterImageView;
    private ImageView mBackdropImageView;
    private RatingBar mUserVoteRatingBar;
    private TextView mReleaseDateTextView;
    private TextView mPlotTextView;

    //a static method is a good way to explicitly tell
    //what we want to start this activity
    public static void start(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(KEY_MOVIE, movie);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //if there is no movie, there is nothing to do
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(KEY_MOVIE) || intent.getParcelableExtra(KEY_MOVIE) == null) {
            Log.e(TAG, "No movie passed in intent");
            finish();
            return;
        }

        mMovie = intent.getParcelableExtra(KEY_MOVIE);

        setSupportActionBar(findViewById(R.id.toolbar));
        mContainerLayout = findViewById(R.id.cl_container);
        mPosterImageView = findViewById(R.id.iv_movie_poster);
        mBackdropImageView = findViewById(R.id.iv_movie_backdrop);
        mUserVoteRatingBar = findViewById(R.id.rb_user_votes);
        mReleaseDateTextView = findViewById(R.id.tv_release_date);
        mPlotTextView = findViewById(R.id.tv_plot);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        bindMovieToUI();

        //restores the scroll of the screen if one is available
        if (savedInstanceState != null && savedInstanceState.get(KEY_Y_SCROLL) != null) {
            mContainerLayout.scrollTo(0, savedInstanceState.getInt(KEY_Y_SCROLL));
        }

    }

    private void bindMovieToUI() {

        //load the title
        Objects.requireNonNull(getSupportActionBar()).setTitle(mMovie.getTitle());

        //load the backdrop image
        Picasso.get().load(mMovie.getBackdrop()).into(mBackdropImageView);

        //load the release date\
        mReleaseDateTextView.setText(mMovie.getReleaseDate());

        //load the plot/overview
        mPlotTextView.setText(mMovie.getOverview());

        //calculates the rating. The rating goes from 0 to 10, but
        //we want to show in a 5 start rating bar, so we have to
        //calculate how many start is the rating worth
        mUserVoteRatingBar.setRating((mMovie.getVoteAverage() * 5) / 10);

        //load the poster image
        Picasso.get()
                .load(mMovie.getPoster())
                .placeholder(R.drawable.loading_poster)
                .error(R.drawable.broken_poster)
                .into(mPosterImageView);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the scroll position of the screen
        outState.putInt(KEY_Y_SCROLL, mContainerLayout.getScrollY());
    }

}
