package com.suzei.timescheduler.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.suzei.timescheduler.database.Schedule;
import com.suzei.timescheduler.database.ScheduleRepository;

public class ScheduleItemViewModel extends ViewModel {

    private ScheduleRepository repository;

    ScheduleItemViewModel(ScheduleRepository repository) {
        this.repository = repository;
    }

    public LiveData<Schedule> getScheduleById(String itemId) {
        return repository.getScheduleById(itemId);
    }

}
