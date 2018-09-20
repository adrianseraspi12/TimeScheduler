package com.suzei.timescheduler.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.suzei.timescheduler.dao.TimeSchedulerContract.TimeScheduleEntry;
import com.suzei.timescheduler.model.Day;

import java.util.List;

public class DayDAO extends CrudImplementation<Day> {

    private Long mId;
    private Long insertId;

    private String[] allColumns = {
            TimeScheduleEntry.KEY_ID,
            TimeScheduleEntry.COLUMN_SUNDAY,
            TimeScheduleEntry.COLUMN_MONDAY,
            TimeScheduleEntry.COLUMN_TUESDAY,
            TimeScheduleEntry.COLUMN_WEDNESDAY,
            TimeScheduleEntry.COLUMN_THURSDAY,
            TimeScheduleEntry.COLUMN_FRIDAY,
            TimeScheduleEntry.COLUMN_SATURDAY};

    public DayDAO(Context context) {
        super(context);
    }

    public DayDAO(Context context, Long id) {
        super(context);
        this.mId = id;
    }

    @Override
    public void create(Day model) {
        ContentValues values = new ContentValues();
        values.put(TimeScheduleEntry.COLUMN_SUNDAY, model.getSunday());
        values.put(TimeScheduleEntry.COLUMN_MONDAY, model.getMonday());
        values.put(TimeScheduleEntry.COLUMN_TUESDAY, model.getTuesday());
        values.put(TimeScheduleEntry.COLUMN_WEDNESDAY, model.getWednesday());
        values.put(TimeScheduleEntry.COLUMN_THURSDAY, model.getThursday());
        values.put(TimeScheduleEntry.COLUMN_FRIDAY, model.getFriday());
        values.put(TimeScheduleEntry.COLUMN_SATURDAY, model.getSaturday());

        insertId = mDatabase.insert(TimeScheduleEntry.TABLE_DAY, null, values);
    }

    @Override
    public Day readSingleRow() {
        Cursor cursor = mDatabase.query(
                TimeScheduleEntry.TABLE_DAY, allColumns,
                TimeScheduleEntry.COLUMN_DAY_ID + "=?",
                new String[]{String.valueOf(mId)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursorToDay(cursor);
    }

    @Override
    public List<Day> readAllRows() {
        return null;
    }

    @Override
    public void update(Day model) {

    }

    @Override
    public void delete(Day model) {

    }

    public Long getInsertId() {
        return insertId;
    }

    private Day cursorToDay(Cursor cursor) {
        long _id = cursor.getLong(0);
        int sunday = cursor.getInt(1);
        int monday = cursor.getInt(2);
        int tuesday = cursor.getInt(3);
        int wednesday = cursor.getInt(4);
        int thursday = cursor.getInt(5);
        int friday = cursor.getInt(6);
        int saturday = cursor.getInt(7);

        return new Day(_id, sunday, monday, tuesday, wednesday,thursday, friday, saturday);
    }
}
