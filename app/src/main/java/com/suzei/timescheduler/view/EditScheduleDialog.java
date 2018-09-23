package com.suzei.timescheduler.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.suzei.timescheduler.database.Schedule;
import com.suzei.timescheduler.util.FullscreenDialogEditor;

public class EditScheduleDialog extends FullscreenDialogEditor implements
        CrudImplementation.Listener {

    public EditScheduleDialog(@NonNull Activity a) {
        super(a);
        setTitle("Edit Schedule");
    }

    @Override
    protected void onSaveClick(Schedule schedule) {
        ScheduleDAO scheduleDAO = new ScheduleDAO(getContext(), this);
        scheduleDAO.update(schedule);
    }

    @Override
    public void onSuccess() {
        dismiss();
    }

    @Override
    public void onFailed() {
        Toast.makeText(getContext(), "Unable to Save, Please try again",
                Toast.LENGTH_SHORT).show();
    }
}
