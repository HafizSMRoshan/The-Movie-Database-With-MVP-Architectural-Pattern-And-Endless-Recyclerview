
package com.task.tmdb.interactors;

import com.task.tmdb.beans.Movie;

import java.util.List;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public interface GetMoviesInteractor {

    interface OnFinishedListener {
        void onResponse(List<Movie> movies);
        void onError(String msg);
    }

    void getMovies(OnFinishedListener listener);
    boolean lastPageReached();
}
