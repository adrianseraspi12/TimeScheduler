package com.suzei.timescheduler.di;

import android.app.Application;

import com.suzei.timescheduler.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    Application app();

}
