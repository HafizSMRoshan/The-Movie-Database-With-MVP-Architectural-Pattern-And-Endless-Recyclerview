
package com.task.tmdb.implementers;

import com.task.tmdb.beans.Movie;
import com.task.tmdb.interactors.GetMoviesInteractor;
import com.task.tmdb.presenters.MoviesPresenter;
import com.task.tmdb.views.MoviesView;

import java.util.List;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public class MoviesPresenterImpl implements MoviesPresenter, GetMoviesInteractor.OnFinishedListener {

    private MoviesView moviesView;
    private GetMoviesInteractor getMoviesInteractor;

    public MoviesPresenterImpl(MoviesView moviesView, GetMoviesInteractor getMoviesInteractor) {
        this.moviesView = moviesView;
        this.getMoviesInteractor = getMoviesInteractor;
    }

    @Override
    public void onResume() {
        if (moviesView != null) {
            moviesView.showProgress();
        }

        getMoviesInteractor.getMovies(this);
    }

    @Override
    public boolean lastPageReached() {
        return getMoviesInteractor.lastPageReached();
    }

    @Override
    public void onItemClicked(int position) {
        if (moviesView != null) {
            moviesView.showMessage(String.format("Position %d clicked", position + 1));
        }
    }

    @Override
    public void onDestroy() {
        moviesView = null;
    }

    public MoviesView getMoviesView() {
        return moviesView;
    }

    @Override
    public void onResponse(List<Movie> movies) {
        if (moviesView != null) {
            moviesView.setMovies(movies);
            moviesView.hideProgress();
        }
    }

    @Override
    public void onError(String msg) {
        if (moviesView != null) {
            moviesView.showMessage(msg);
            moviesView.hideProgress();
        }
    }

}
