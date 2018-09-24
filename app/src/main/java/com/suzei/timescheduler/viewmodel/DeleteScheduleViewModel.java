package com.suzei.timescheduler.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.suzei.timescheduler.database.Schedule;
import com.suzei.timescheduler.database.ScheduleRepository;

public class DeleteScheduleViewModel extends ViewModel {

    private ScheduleRepository repository;

    DeleteScheduleViewModel(ScheduleRepository repository) {
        this.repository = repository;
    }

    public void deleteSchedule(Schedule schedule) {
        new DeleteScheduleItemTask(repository).execute(schedule);
    }

    private static class DeleteScheduleItemTask extends AsyncTask<Schedule, Void, Void> {

        private ScheduleRepository repository;

        DeleteScheduleItemTask(ScheduleRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(Schedule... scheduleEntities) {
            repository.deleteSchedule(scheduleEntities[0]);
            return null;
        }

    }

}
