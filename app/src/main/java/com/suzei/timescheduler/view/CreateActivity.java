package com.suzei.timescheduler.view;

import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.suzei.timescheduler.R;
import com.suzei.timescheduler.TimeSchedule;
import com.suzei.timescheduler.database.Schedule;
import com.suzei.timescheduler.util.AppTheme;
import com.suzei.timescheduler.viewmodel.UpsertScheduleViewModel;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelProvider;

    private UpsertScheduleViewModel upsertScheduleViewModel;

    @BindString(R.string.select_time) String selectTimeString;
    @BindString(R.string.create_schedule) String createScheduleString;
    @BindString(R.string.empty_time) String emptyTimeString;
    @BindString(R.string.time_is_not_correct) String timeIsNotCorrectString;
    @BindString(R.string.start_time) String startTimeString;
    @BindString(R.string.end_time) String endTimeString;
    @BindView(R.id.editor_name) TextInputEditText nameView;
    @BindView(R.id.editor_start_time) TextView startTimeView;
    @BindView(R.id.editor_end_time) TextView endTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppTheme.set(CreateActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        initObjects();
        setUpDagger();
        setUpViewModel();
        setUpToolbar();
    }

    private void initObjects() {
        ButterKnife.bind(this);
    }

    private void setUpDagger() {
        ((TimeSchedule) getApplication())
                .getAppComponent()
                .inject(this);
    }

    private void setUpViewModel() {
        upsertScheduleViewModel = ViewModelProviders
                .of(this, viewModelProvider)
                .get(UpsertScheduleViewModel.class);
    }

    private void setUpToolbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(createScheduleString);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_save) {
            //  save all data
            if (isTimeCorrect()) {
                Schedule schedule = new Schedule(
                        getString(nameView),
                        getString(startTimeView),
                        getString(endTimeView),
                        TimeSchedule.NOT_ACTIVE
                );

                upsertScheduleViewModel.upsertSchedule(schedule);
                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.editor_start_time, R.id.editor_end_time})
    public void onTimeClick(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog pickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute1) -> {
                    String time = String.format("%02d:%02d", hourOfDay, minute1);
                    textView.setText(time);
                },
                hour, minute, true);

        pickerDialog.setTitle(selectTimeString);
        pickerDialog.show();
    }

    private String getString(TextView textView) {
        return textView.getText().toString().trim();
    }

    private boolean isTimeCorrect() {
        String startTime = startTimeView.getText().toString();
        String endTime = endTimeView.getText().toString();

        if (startTime.equals(startTimeString) || endTime.equals(endTimeString)) {
            Toast.makeText(CreateActivity.this,
                    emptyTimeString,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        String[] startTimeArr = startTime.split(":");
        String[] endTimeArr = endTime.split(":");

        int startHour = Integer.parseInt(startTimeArr[0]);
        int startMin = Integer.parseInt(startTimeArr[1]);

        int endHour = Integer.parseInt(endTimeArr[0]);
        int endMin = Integer.parseInt(endTimeArr[1]);

        if (startHour > endHour || (startHour == endHour && startMin >= endMin)) {
            Toast.makeText(
                    this,
                    timeIsNotCorrectString,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        return true;
    }

}
