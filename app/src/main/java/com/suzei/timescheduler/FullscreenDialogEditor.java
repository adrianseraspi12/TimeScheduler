package com.suzei.timescheduler;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.suzei.timescheduler.util.AppTheme;

import java.util.Calendar;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

class FullscreenDialogEditor extends Dialog {

    public static final int PICK_ALARM_TONE = 5;

    private Activity activity;
    private boolean isDarkTheme;
    private String alarmTone;

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

    FullscreenDialogEditor(@NonNull Activity a) {
        super(a, AppTheme.getFullscreenDialogStyle(a.getApplicationContext()));
        setContentView(R.layout.fullscreen_editor_dialog);
        this.activity = a;
        ButterKnife.bind(this, this);
        checkIfDarkTheme();
        setStatusBarColor();
        setUpUi();
    }

    private void checkIfDarkTheme() {
        String theme = PreferenceManager
                .getDefaultSharedPreferences(getContext())
                .getString("app_theme", "Default Theme");
        isDarkTheme = AppTheme.isDarkTheme(theme);
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

    private void setUpUi() {
        menuView.setCardBackgroundColor(AppTheme.getAttrColor(activity, R.attr.colorPrimary));
        cornerDrawable.setColorFilter(AppTheme.getAttrColor(activity, R.attr.colorAccent),
                PorterDuff.Mode.SRC_IN);
    }

    @OnClick(R.id.editor_close)
    public void onCloseClick() {
        dismiss();
    }

    @OnClick({R.id.editor_sunday, R.id.editor_monday, R.id.editor_tuesday, R.id.editor_wednesday,
            R.id.editor_thursday, R.id.editor_friday, R.id.editor_saturday})
    public void onSelectWeekClick(TextView textView) {
        setBackgound(textView);
    }

    @OnClick(R.id.editor_time)
    public void onTimeClick() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(getContext(),
                (view, hourOfDay, minute1) -> timeView.setText(hourOfDay + ":" + minute1),
                hour, minute, true);
        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    private boolean isSelected(TextView textView) {
        return textView.getBackground() != null;
    }

    private void setBackgound(TextView textView) {
        if (isSelected(textView)) {
            textView.setBackground(null);
        } else {
            textView.setBackground(cornerDrawable);
        }
    }
}