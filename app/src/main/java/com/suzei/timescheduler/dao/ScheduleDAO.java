package com.suzei.timescheduler.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.suzei.timescheduler.TimeSchedule;
import com.suzei.timescheduler.dao.TimeSchedulerContract.TimeScheduleEntry;
import com.suzei.timescheduler.model.Day;
import com.suzei.timescheduler.model.Schedule;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ScheduleDAO extends CrudImplementation<Schedule> {

    private Listener callbacks;

    private String[] allColumns = {
            TimeScheduleEntry.KEY_ID,
            TimeScheduleEntry.COLUMN_NAME,
            TimeScheduleEntry.COLUMN_TIME,
            TimeScheduleEntry.COLUMN_ACTIVE,
            TimeScheduleEntry.COLUMN_DAY_ID};

    public ScheduleDAO(Context context, Listener callbacks) {
        super(context);
        this.callbacks = callbacks;
    }

    @Override
    public void create(Schedule schedule) {
        DayDAO dayDAO = new DayDAO(getContext());
        dayDAO.create(schedule.getDay());
        long insertDayId = dayDAO.getInsertId();

        ContentValues values = new ContentValues();
        values.put(TimeScheduleEntry.COLUMN_NAME, schedule.getName());
        values.put(TimeScheduleEntry.COLUMN_TIME, schedule.getTime());
        values.put(TimeScheduleEntry.COLUMN_ACTIVE, schedule.getActive());
        values.put(TimeScheduleEntry.COLUMN_DAY_ID, insertDayId);

        long insertScheduleId = mDatabase.insert(
                TimeScheduleEntry.TABLE_SCHEDULES,
                null, values);

        Timber.i("Insert Schedule Id= %s", insertScheduleId);
        Timber.i("Insert Day Id = %s", insertDayId);

        if (insertScheduleId == -1) {
            callbacks.onFailed();
            return;
        }

        callbacks.onSuccess();
    }

    @Override
    public Schedule readSingleRow() {
        return null;
    }

    @Override
    public List<Schedule> readAllRows() {
        List<Schedule> scheduleList = new ArrayList<>();

        Cursor cursor = mDatabase.query(TimeScheduleEntry.TABLE_SCHEDULES, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Schedule schedule = cursorToSchedule(cursor);
            scheduleList.add(schedule);
            cursor.moveToNext();
        }

        cursor.close();

        return scheduleList;
    }


    @Override
    public void update(Schedule model) {

    }

    @Override
    public void delete(Schedule model) {

    }

    private Schedule cursorToSchedule(Cursor cursor) {
        long _id = cursor.getLong(cursor.getColumnIndex(TimeScheduleEntry.KEY_ID));
        String name = cursor.getString(cursor.getColumnIndex(TimeScheduleEntry.COLUMN_NAME));
        String time = cursor.getString(cursor.getColumnIndex(TimeScheduleEntry.COLUMN_TIME));
        int active = cursor.getInt(cursor.getColumnIndex(TimeScheduleEntry.COLUMN_ACTIVE));
        long dayId = cursor.getLong(cursor.getColumnIndex(TimeScheduleEntry.COLUMN_DAY_ID));

        DayDAO dayDAO = new DayDAO(getContext(), dayId);
        Day day = dayDAO.readSingleRow();

        return new Schedule(_id, name, time, active, day);
    }

}
