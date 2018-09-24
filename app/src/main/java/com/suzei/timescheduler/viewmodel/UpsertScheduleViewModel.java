package com.suzei.timescheduler.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.suzei.timescheduler.database.ScheduleEntity;
import com.suzei.timescheduler.database.ScheduleRepository;

public class UpsertScheduleViewModel extends ViewModel {

    private ScheduleRepository repository;

    UpsertScheduleViewModel(ScheduleRepository repository) {
        this.repository = repository;
    }

    public void upsertSchedule(ScheduleEntity scheduleEntity) {
        new CreateScheduleTask(repository).execute(scheduleEntity);
    }

    private static class CreateScheduleTask extends AsyncTask<ScheduleEntity, Void, Void> {

        private ScheduleRepository repository;

        CreateScheduleTask(ScheduleRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(ScheduleEntity... scheduleEntities) {
            repository.createSchedule(scheduleEntities[0]);
            return null;
        }

    }

}
