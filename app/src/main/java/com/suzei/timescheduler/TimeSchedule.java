package com.suzei.timescheduler;

import android.app.Application;

import com.suzei.timescheduler.di.AppComponent;
import com.suzei.timescheduler.di.ApplicationModule;
import com.suzei.timescheduler.di.DaggerAppComponent;
import com.suzei.timescheduler.di.RoomModule;

import timber.log.Timber;

public class TimeSchedule extends Application {

    public static final int ACTIVE = 0;
    public static final int NOT_ACTIVE = 1;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        appComponent = DaggerAppComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .roomModule(new RoomModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
