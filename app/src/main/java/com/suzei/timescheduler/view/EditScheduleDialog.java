package com.suzei.timescheduler.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.suzei.timescheduler.database.Schedule;
import com.suzei.timescheduler.util.FullscreenDialogEditor;

public class EditScheduleDialog extends FullscreenDialogEditor {

    public EditScheduleDialog(@NonNull Activity a) {
        super(a);
        setTitle("Edit Schedule");
    }

    @Override
    protected void onSaveClick(Schedule schedule) {
    }

}
