package com.task.tmdb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.rdo.endlessrecyclerviewadapter.EndlessRecyclerViewScrollListener;
import com.task.tmdb.R;
import com.task.tmdb.adapters.MovieAdapter;
import com.task.tmdb.beans.Movie;
import com.task.tmdb.interfaces.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.task.tmdb.utilities.Util.BUNDLES;
import static com.task.tmdb.utilities.Util.MOVIES;

public class MovieFilterActivity extends AppCompatActivity implements ItemClickListener {

    List<Movie> movies = new ArrayList<Movie>();

    private RecyclerView rv_movie;

    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra(BUNDLES)) {

            Intent intent = getIntent();
            Bundle args = intent.getBundleExtra(BUNDLES);
            movies = (ArrayList<Movie>) args.getSerializable(MOVIES);

            initViews();

        } else {

            Toast.makeText(this, getResources().getString(R.string.movie_details_error), Toast.LENGTH_LONG).show();
            MovieFilterActivity.this.finish();

        }

    }

    private void initViews() {

        this.movieAdapter = new MovieAdapter(movies, this, MovieFilterActivity.this);

        this.rv_movie = (RecyclerView) findViewById(R.id.rv_movie);
        this.rv_movie.setHasFixedSize(true);
        this.rv_movie.addOnScrollListener(new EndlessRecyclerViewScrollListener() {
            @Override
            public void onScrolledToBottom() {

            }

            @Override
            public boolean endlessScrollEnabled() {
                return false; //Your flag used to check if there are more pages available
            }
        });
        this.rv_movie.setLayoutManager(new LinearLayoutManager(this));
        this.rv_movie.setItemAnimator(new DefaultItemAnimator());
        this.rv_movie.setAdapter(movieAdapter);

    }

    @Override
    public void onClickItem(Movie movie) {

    }
}
