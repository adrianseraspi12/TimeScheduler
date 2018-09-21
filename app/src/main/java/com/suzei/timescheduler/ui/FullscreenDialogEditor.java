package com.suzei.timescheduler.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.suzei.timescheduler.R;
import com.suzei.timescheduler.dao.TimeSchedulerContract;
import com.suzei.timescheduler.model.Day;
import com.suzei.timescheduler.model.Schedule;
import com.suzei.timescheduler.util.AppTheme;

import java.util.Calendar;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class FullscreenDialogEditor extends Dialog {

    private Activity activity;
    private int hour;
    private int minute;

    @BindDrawable(R.drawable.rounded_corner_background) Drawable cornerDrawable;
    @BindView(R.id.editor_menu) CardView menuView;
    @BindView(R.id.editor_title) TextView titleView;
    @BindView(R.id.editor_name) TextInputEditText nameView;
    @BindView(R.id.editor_sunday) TextView sundayView;
    @BindView(R.id.editor_monday) TextView mondayView;
    @BindView(R.id.editor_tuesday) TextView tuesdayView;
    @BindView(R.id.editor_wednesday) TextView wednesdayView;
    @BindView(R.id.editor_thursday) TextView thursdayView;
    @BindView(R.id.editor_friday) TextView fridayView;
    @BindView(R.id.editor_saturday) TextView saturdayView;
    @BindView(R.id.editor_time) TextView timeView;

    public FullscreenDialogEditor(@NonNull Activity a) {
        super(a, AppTheme.getFullscreenDialogStyle(a));
        this.activity = a;
        this.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        this.minute = Calendar.getInstance().get(Calendar.MINUTE);
        setUpUi();
        setStatusBarColor();
    }

    private void setUpUi() {
        setContentView(R.layout.fullscreen_editor_dialog);
        ButterKnife.bind(this, this);
        menuView.setCardBackgroundColor(AppTheme.getAttrColor(activity, R.attr.colorPrimary));
        cornerDrawable.setColorFilter(AppTheme.getAttrColor(activity, R.attr.colorAccent),
                PorterDuff.Mode.SRC_IN);
    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = AppTheme.getAttrColor(activity, R.attr.colorPrimaryDark);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    private boolean isDaySelected(TextView textView) {
        return textView.getBackground() != null;
    }

    public void setName(String name) {
        nameView.setText(name);
    }

    public void setTime(String time) {
        timeView.setText(time);

        String[] timeArray = time.split(":");
        this.hour = Integer.parseInt(timeArray[0].trim());
        this.minute = Integer.parseInt(timeArray[1].trim());
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        titleView.setText(title);
    }

    @OnClick(R.id.editor_close)
    public void onCloseClick() {
        dismiss();
    }

    @OnClick(R.id.editor_save)
    void onGetData() {
        onSaveClick(getSchedule());
    }

    @OnClick({R.id.editor_sunday, R.id.editor_monday, R.id.editor_tuesday, R.id.editor_wednesday,
            R.id.editor_thursday, R.id.editor_friday, R.id.editor_saturday})
    public void onSelectWeekClick(TextView textView) {
        if (isDaySelected(textView)) {
            textView.setBackground(null);
        } else {
            textView.setBackground(cornerDrawable);
        }
    }

    @OnClick(R.id.editor_time)
    public void onTimeClick() {
        TimePickerDialog timePicker = new TimePickerDialog(
                getContext(),
                (view, hourOfDay, minute1) ->
                        timeView.setText(String.format("%02d:%02d", hourOfDay, minute1)),
                hour, minute, true);

        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    private Schedule getSchedule() {
        Day day = new Day(getSelectedDay(sundayView),
                getSelectedDay(mondayView), getSelectedDay(tuesdayView),
                getSelectedDay(wednesdayView), getSelectedDay(thursdayView),
                getSelectedDay(fridayView), getSelectedDay(saturdayView));

        String name = nameView.getText().toString().trim();
        String time = timeView.getText().toString().trim();
        int active = TimeSchedulerContract.TimeScheduleEntry.TRUE;

        return new Schedule(name, time, active, day);
    }

    private int getSelectedDay(TextView textView) {
        if (isDaySelected(textView)) {
            return TimeSchedulerContract.TimeScheduleEntry.TRUE;
        } else {
            return TimeSchedulerContract.TimeScheduleEntry.FALSE;
        }
    }

    protected abstract void onSaveClick(Schedule schedule);
}
