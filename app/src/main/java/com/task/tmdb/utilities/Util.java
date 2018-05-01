package com.task.tmdb.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.task.tmdb.main.TMDBApplication;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public class Util {

    public static final String MOVIE_DETAIL = "MOVIE_DETAIL";

    public static boolean isInternetOn() {
        ConnectivityManager
                cm = (ConnectivityManager) TMDBApplication.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

}


