package com.task.tmdb.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rdo.endlessrecyclerviewadapter.EndlessRecyclerViewScrollListener;
import com.task.tmdb.R;
import com.task.tmdb.adapters.MovieAdapter;
import com.task.tmdb.beans.Movie;
import com.task.tmdb.implementers.GetMoviesInteractorImpl;
import com.task.tmdb.implementers.MoviesPresenterImpl;
import com.task.tmdb.interfaces.ItemClickListener;
import com.task.tmdb.presenters.MoviesPresenter;
import com.task.tmdb.utilities.Util;
import com.task.tmdb.views.MoviesView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.task.tmdb.utilities.Util.BUNDLES;
import static com.task.tmdb.utilities.Util.MOVIES;
import static com.task.tmdb.utilities.Util.MOVIE_DETAIL;
import static com.task.tmdb.utilities.Util.compareDate;
import static com.task.tmdb.utilities.Util.getSelectedDate;

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

    //    initialize objects
    private void initObjects() {

        this.movieAdapter = new MovieAdapter(this.movies, this, MoviesActivity.this);
        this.presenter = new MoviesPresenterImpl(this, new GetMoviesInteractorImpl());

    }

    //    destroying object
    private void destroyObjects() {

        this.movies.clear();
        this.movieAdapter.notifyDataSetChanged();
        this.movieAdapter = null;
        this.presenter = null;


    }

    //    initializing views
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

    /* Presenter resume and destroy as activity lifecycle */
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

            showDateFilterDialog();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //    date filter dialog
    private void showDateFilterDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.date_filter_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText et_start_date = (EditText) dialogView.findViewById(R.id.et_start_date);
        et_start_date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                //do your stuff here..
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    getSelectedDate(MoviesActivity.this, new Util.SelectDateListener() {
                        @Override
                        public void onSelectDate(String date) {
                            et_start_date.setText(date);
                        }
                    });
                }
                return false;
            }
        });

        final EditText et_end_date = (EditText) dialogView.findViewById(R.id.et_end_date);
        et_end_date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                //do your stuff here..
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    getSelectedDate(MoviesActivity.this, new Util.SelectDateListener() {
                        @Override
                        public void onSelectDate(String date) {
                            et_end_date.setText(date);
                        }
                    });
                }
                return false;
            }
        });

        dialogBuilder.setTitle(getResources().getString(R.string.filter_by_date));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String startDate = et_start_date.getText().toString();
                String endDate = et_end_date.getText().toString();

                if (compareDate(startDate, endDate)) {

                    if (movieAdapter != null
                            && movieAdapter.getMovies(startDate, endDate) != null
                            && movieAdapter.getMovies(startDate, endDate).size() > 0) {

                        Bundle args = new Bundle();
                        args.putSerializable(MOVIES, (Serializable) movieAdapter.getMovies(startDate, endDate));

                        startActivity(new Intent(MoviesActivity.this, MovieFilterActivity.class)
                                .putExtra(BUNDLES, args));

                    } else {

                        Toast.makeText(MoviesActivity.this, MoviesActivity.this.getResources().getString(R.string.movie_error), Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(MoviesActivity.this, getResources().getString(R.string.date_error), Toast.LENGTH_LONG).show();
                }

            }
        });
        dialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    /* callback of MoviesView */

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

    //    movie list item click listener
    @Override
    public void onClickItem(Movie movie) {
        startActivity(new Intent(MoviesActivity.this, MovieDetailsActivity.class)
                .putExtra(MOVIE_DETAIL, movie));
    }
}
