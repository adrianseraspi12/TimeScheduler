package com.suzei.timescheduler.ui;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.suzei.timescheduler.dao.CrudImplementation;
import com.suzei.timescheduler.dao.ScheduleDAO;
import com.suzei.timescheduler.model.Schedule;

class CreateScheduleDialog extends FullscreenDialogEditor implements CrudImplementation.Listener {

    private Activity activity;

    CreateScheduleDialog(@NonNull Activity a) {
        super(a);
        this.activity = a;
        setTitle("Create Schedule");
    }

    @Override
    protected void onSaveClick(Schedule schedule) {
        ScheduleDAO scheduleDAO = new ScheduleDAO(activity, this);
        scheduleDAO.create(schedule);
    }

    @Override
    public void onSuccess() {
        dismiss();
    }

    @Override
    public void onFailed() {
        Toast.makeText(getContext(), "Failed to save the schedule, Please try again",
                Toast.LENGTH_SHORT).show();
    }

}