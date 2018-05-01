
package com.task.tmdb.views;

import com.task.tmdb.beans.Movie;

import java.util.List;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public interface MoviesView {

    void showProgress();

    void hideProgress();

    void setMovies(List<Movie> movies);

    void showMessage(String message);
}
