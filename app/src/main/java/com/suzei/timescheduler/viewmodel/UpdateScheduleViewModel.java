package com.suzei.timescheduler.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.suzei.timescheduler.database.Schedule;
import com.suzei.timescheduler.database.ScheduleRepository;

public class UpdateScheduleViewModel extends ViewModel {

    private ScheduleRepository repository;

    UpdateScheduleViewModel(ScheduleRepository repository) {
        this.repository = repository;
    }

    public void updateSchedule(Schedule schedule) {
        new UpdateScheduleTask(repository).execute(schedule);
    }

    private static class UpdateScheduleTask extends AsyncTask<Schedule, Void, Void> {

        private ScheduleRepository repository;

        UpdateScheduleTask(ScheduleRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(Schedule... schedules) {
            repository.updateSchedule(schedules[0]);
            return null;
        }

    }

}
