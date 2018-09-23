package com.suzei.timescheduler.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.suzei.timescheduler.database.ScheduleRepository;

public class DeleteAllScheduleViewModel extends ViewModel {

    private ScheduleRepository repository;

    DeleteAllScheduleViewModel(ScheduleRepository repository) {
        this.repository = repository;
    }

    public void deleteAllSchedule() {
        new DeleteAllScheduleTask(repository).execute();
    }

    private static class DeleteAllScheduleTask extends AsyncTask<Void, Void, Void> {

        private ScheduleRepository repository;

        DeleteAllScheduleTask(ScheduleRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            repository.deleteAllSchedule();
            return null;
        }

    }

}
