package com.task.tmdb.services;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.task.tmdb.R;
import com.task.tmdb.beans.UpcomingMoviesResponse;
import com.task.tmdb.interfaces.CallbackHandler;
import com.task.tmdb.main.TMDBApplication;
import com.task.tmdb.utilities.Util;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public class MovieService {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/upcoming";
    public static final String BASE_IMAGE_URL_THUMB = "http://image.tmdb.org/t/p/w185";
    public static final String BASE_IMAGE_URL_ORIGINAL = "https://image.tmdb.org/t/p/original";
    private static final String API_KEY = "48a39217b2f934a435d37d39d522baad";
    private static final String LANGUAGE = "en-US";

    private static final String QUERY_PARAM_1 = "api_key";
    private static final String QUERY_PARAM_2 = "language";
    private static final String QUERY_PARAM_3 = "page";

    private static final MovieService ourInstance = new MovieService();

    public static MovieService getInstance() {
        return ourInstance;
    }

    private MovieService() {
    }

    public void getMovies(int pageNumber, final CallbackHandler.OnFinishedListener listener) {

        if (Util.isInternetOn()) {
            AndroidNetworking.get(BASE_URL)
                    .addQueryParameter(QUERY_PARAM_1, API_KEY)
                    .addQueryParameter(QUERY_PARAM_2, LANGUAGE)
                    .addQueryParameter(QUERY_PARAM_3, String.valueOf(pageNumber))
                    .setTag(this)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsObject(UpcomingMoviesResponse.class, new ParsedRequestListener<UpcomingMoviesResponse>() {
                        @Override
                        public void onResponse(UpcomingMoviesResponse response) {

                            listener.onResponse(response);

                        }

                        @Override
                        public void onError(ANError anError) {

                            if (anError.getErrorCode() == 401) {
                                listener.onError(TMDBApplication.getInstance().getResources().getString(R.string.error_401));
                            } else if (anError.getErrorCode() == 402) {
                                listener.onError(TMDBApplication.getInstance().getResources().getString(R.string.error_402));
                            } else {
                                listener.onError(anError.getErrorDetail());
                            }


                        }
                    });
        } else {
            listener.onError(TMDBApplication.getInstance().getResources().getString(R.string.internet_fail));
        }

    }

}
