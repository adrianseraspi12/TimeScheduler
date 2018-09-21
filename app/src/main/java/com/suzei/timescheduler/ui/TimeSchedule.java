package com.suzei.timescheduler.ui;

import android.app.Application;

import timber.log.Timber;

public class TimeSchedule extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

    }
}
