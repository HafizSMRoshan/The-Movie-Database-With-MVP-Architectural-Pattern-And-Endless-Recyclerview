package com.task.tmdb.interfaces;

import com.task.tmdb.beans.UpcomingMoviesResponse;

import java.util.List;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public interface CallbackHandler<T> {

    interface OnFinishedListener {
        void onResponse(UpcomingMoviesResponse response);
        void onError(String msg);
    }

    void onNewPage(List<T> page);
}
