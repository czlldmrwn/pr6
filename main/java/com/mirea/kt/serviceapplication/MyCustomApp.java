package com.mirea.kt.serviceapplication;

import android.app.Application;
import android.util.Log;

public class MyCustomApp extends Application {

    public static final String LOG_TAG = "my_app_tag";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "Application created!");
    }
}
