package com.task.tmdb.main;

import android.app.Application;
import com.androidnetworking.AndroidNetworking;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public class TMDBApplication extends Application {

    private static TMDBApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        AndroidNetworking.initialize(getApplicationContext());
    }

    public static synchronized TMDBApplication getInstance() {
        return mInstance;
    }

}
