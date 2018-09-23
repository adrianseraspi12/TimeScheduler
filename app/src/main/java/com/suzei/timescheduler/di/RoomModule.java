package com.suzei.timescheduler.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;

import com.suzei.timescheduler.database.ScheduleDao;
import com.suzei.timescheduler.database.ScheduleDatabase;
import com.suzei.timescheduler.database.ScheduleRepository;
import com.suzei.timescheduler.viewmodel.CustomViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private final ScheduleDatabase database;

    public RoomModule(Application app) {
        database = ScheduleDatabase.getInstance(app);
    }

    @Provides
    @Singleton
    ScheduleRepository provideScheduleRepository(ScheduleDao scheduleDao) {
        return new ScheduleRepository(scheduleDao);
    }

    @Provides
    @Singleton
    ScheduleDao provideScheduleDao(ScheduleDatabase database) {
        return database.getScheduleDao();
    }

    @Provides
    @Singleton
    ScheduleDatabase provideScheduleDatabase() {
        return database;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(ScheduleRepository repository) {
        return new CustomViewModelFactory(repository);
    }

}
