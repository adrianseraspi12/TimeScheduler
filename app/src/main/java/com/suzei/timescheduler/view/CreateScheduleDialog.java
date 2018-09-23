package com.suzei.timescheduler.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.suzei.timescheduler.database.Schedule;
import com.suzei.timescheduler.util.FullscreenDialogEditor;

class CreateScheduleDialog extends FullscreenDialogEditor {

    private Activity activity;

    CreateScheduleDialog(@NonNull Activity a) {
        super(a);
        this.activity = a;
        setTitle("Create Schedule");
    }

    @Override
    protected void onSaveClick(Schedule schedule) {

    }

}