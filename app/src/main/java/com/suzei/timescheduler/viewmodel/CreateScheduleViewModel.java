package com.suzei.timescheduler.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.suzei.timescheduler.database.Schedule;
import com.suzei.timescheduler.database.ScheduleRepository;

public class CreateScheduleViewModel extends ViewModel {

    private ScheduleRepository repository;

    CreateScheduleViewModel(ScheduleRepository repository) {
        this.repository = repository;
    }

    public void createSchedule(Schedule schedule) {
        new CreateScheduleTask(repository).execute(schedule);
    }

    private static class CreateScheduleTask extends AsyncTask<Schedule, Void, Void> {

        private ScheduleRepository repository;

        CreateScheduleTask(ScheduleRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(Schedule... schedules) {
            repository.createSchedule(schedules[0]);
            return null;
        }

    }

}
