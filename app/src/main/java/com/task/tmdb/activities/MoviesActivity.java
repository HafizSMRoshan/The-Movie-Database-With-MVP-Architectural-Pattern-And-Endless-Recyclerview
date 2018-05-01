package com.task.tmdb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rdo.endlessrecyclerviewadapter.EndlessRecyclerViewScrollListener;
import com.task.tmdb.R;
import com.task.tmdb.adapters.MovieAdapter;
import com.task.tmdb.beans.Movie;
import com.task.tmdb.implementers.GetMoviesInteractorImpl;
import com.task.tmdb.implementers.MoviesPresenterImpl;
import com.task.tmdb.interactors.GetMoviesInteractor;
import com.task.tmdb.interfaces.ItemClickListener;
import com.task.tmdb.presenters.MoviesPresenter;
import com.task.tmdb.views.MoviesView;

import java.util.ArrayList;
import java.util.List;

import static com.task.tmdb.utilities.Util.MOVIE_DETAIL;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public class MoviesActivity extends AppCompatActivity implements MoviesView, ItemClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView rv_movie;
    private ProgressBar progressBar;
    private MoviesPresenter presenter;


    private MovieAdapter movieAdapter;
    private List<Movie> movies = new ArrayList<Movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmdb);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initObjects();

        initViews();

    }

    private void initObjects() {

        this.movieAdapter = new MovieAdapter(this.movies, this, MoviesActivity.this);
        this.presenter = new MoviesPresenterImpl(this, new GetMoviesInteractorImpl());

    }

    private void destroyObjects() {

        this.movies.clear();
        this.movieAdapter.notifyDataSetChanged();
        this.movieAdapter = null;
        this.presenter = null;


    }

    private void initViews() {

        this.rv_movie = (RecyclerView) findViewById(R.id.rv_movie);
        this.rv_movie.setHasFixedSize(true);
        this.rv_movie.addOnScrollListener(new EndlessRecyclerViewScrollListener() {
            @Override
            public void onScrolledToBottom() {
                MoviesActivity.this.movieAdapter.showLoadingIndicator();
                MoviesActivity.this.presenter.onResume();
            }

            @Override
            public boolean endlessScrollEnabled() {
                return !MoviesActivity.this.movieAdapter.isRefreshing() && !MoviesActivity.this.presenter.lastPageReached(); //Your flag used to check if there are more pages available
            }
        });
        this.rv_movie.setLayoutManager(new LinearLayoutManager(this));
        this.rv_movie.setItemAnimator(new DefaultItemAnimator());
        this.rv_movie.setAdapter(movieAdapter);

        progressBar = (ProgressBar) findViewById(R.id.progress);


        this.mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        this.mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        this.mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                destroyObjects();
                initObjects();
                MoviesActivity.this.rv_movie.setAdapter(movieAdapter);
                MoviesActivity.this.presenter.onResume();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tmdb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        rv_movie.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        rv_movie.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMovies(List<Movie> movies) {
        MoviesActivity.this.movieAdapter.addNewPage(movies);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickItem(Movie movie) {
        startActivity(new Intent(MoviesActivity.this, MovieDetailsActivity.class)
                .putExtra(MOVIE_DETAIL, movie));
    }
}
