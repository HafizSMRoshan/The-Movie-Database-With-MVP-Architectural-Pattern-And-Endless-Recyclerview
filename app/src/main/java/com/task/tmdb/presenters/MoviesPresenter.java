
package com.task.tmdb.presenters;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public interface MoviesPresenter {

    void onResume();

    boolean lastPageReached();

    void onItemClicked(int position);

    void onDestroy();
}
