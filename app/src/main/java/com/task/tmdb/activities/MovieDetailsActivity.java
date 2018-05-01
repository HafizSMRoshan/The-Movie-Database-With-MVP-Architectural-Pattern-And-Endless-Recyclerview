package com.task.tmdb.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.task.tmdb.R;
import com.task.tmdb.beans.Movie;
import com.task.tmdb.services.MovieService;

import static com.task.tmdb.utilities.Util.MOVIE_DETAIL;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView iv_movie_poster;
    private TextView tv_movie_title, tv_movie_date, tv_movie_vote_avg, tv_movie_popularity, tv_movie_overview;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra(MOVIE_DETAIL)) {
            movie = (Movie) getIntent().getExtras().get(MOVIE_DETAIL);
            initViews();
        } else {
            Toast.makeText(this, "Movie details not found.", Toast.LENGTH_LONG).show();
            MovieDetailsActivity.this.finish();
        }
    }

    private void initViews() {

        iv_movie_poster = findViewById(R.id.iv_movie_poster);
        Picasso.with(MovieDetailsActivity.this).load(MovieService.BASE_IMAGE_URL_ORIGINAL + this.movie.getPosterPath()).into(iv_movie_poster);


        tv_movie_title = findViewById(R.id.tv_movie_title);
        tv_movie_title.setText(movie.getTitle());

        tv_movie_date = findViewById(R.id.tv_movie_date);
        tv_movie_date.setText(movie.getReleaseDate());

        tv_movie_vote_avg = findViewById(R.id.tv_movie_vote_avg);
        tv_movie_vote_avg.setText(String.valueOf(movie.getVoteAverage()));

        tv_movie_popularity = findViewById(R.id.tv_movie_popularity);
        tv_movie_popularity.setText(String.valueOf(movie.getPopularity()));

        tv_movie_overview = findViewById(R.id.tv_movie_overview);
        tv_movie_overview.setText(movie.getOverview());

    }

}
