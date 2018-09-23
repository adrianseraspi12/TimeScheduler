package com.suzei.timescheduler.di;

import android.app.Application;

import com.suzei.timescheduler.TimeSchedule;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final TimeSchedule app;

    public ApplicationModule(TimeSchedule app) {
        this.app = app;
    }

    @Provides
    TimeSchedule provideTimeScheduleApp() {
        return app;
    }

    @Provides
    Application provideApplication() {
        return app;
    }

}
