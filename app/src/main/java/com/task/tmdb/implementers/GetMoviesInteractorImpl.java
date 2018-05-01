
package com.task.tmdb.implementers;

import com.task.tmdb.beans.UpcomingMoviesResponse;
import com.task.tmdb.interactors.GetMoviesInteractor;
import com.task.tmdb.interfaces.CallbackHandler;
import com.task.tmdb.services.MovieService;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public class GetMoviesInteractorImpl implements GetMoviesInteractor {

    private int pageNumber = 1;
    private int pageSize = 0;
    private int pageCount = 0;


    @Override
    public void getMovies(final OnFinishedListener listener) {
        MovieService.getInstance().getMovies(pageNumber, new CallbackHandler.OnFinishedListener() {
            @Override
            public void onResponse(UpcomingMoviesResponse response) {
                listener.onResponse(response.getMovies());
                GetMoviesInteractorImpl.this.pageCount = response.getTotalPages();
                GetMoviesInteractorImpl.this.pageNumber++;
            }

            @Override
            public void onError(String msg) {
                listener.onError(msg);
            }
        });
    }

    @Override
    public boolean lastPageReached() {
        return GetMoviesInteractorImpl.this.pageNumber == this.pageCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

}
