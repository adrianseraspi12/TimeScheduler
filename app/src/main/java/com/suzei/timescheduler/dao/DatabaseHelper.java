package com.suzei.timescheduler.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.suzei.timescheduler.dao.TimeSchedulerContract.TimeScheduleEntry;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "time_scheduler_manager.db";

    private static final String CREATE_TABLE_SCHEDULES =
            "CREATE TABLE " + TimeScheduleEntry.TABLE_SCHEDULES + " ("
            + TimeScheduleEntry.KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + TimeScheduleEntry.COLUMN_NAME + " TEXT, "
            + TimeScheduleEntry.COLUMN_TIME + " TEXT, "
            + TimeScheduleEntry.COLUMN_ACTIVE + " INTEGER NOT NULL,"
            + TimeScheduleEntry.COLUMN_DAY_ID + " INTEGER NOT NULL);";

    private static final String CREATE_TABLE_DAY =
            "CREATE TABLE " + TimeScheduleEntry.TABLE_DAY + " ("
            + TimeScheduleEntry.KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + TimeScheduleEntry.COLUMN_SUNDAY + " INTEGER NOT NULL, "
            + TimeScheduleEntry.COLUMN_MONDAY + " INTEGER NOT NULL, "
            + TimeScheduleEntry.COLUMN_TUESDAY + " INTEGER NOT NULL, "
            + TimeScheduleEntry.COLUMN_WEDNESDAY + " INTEGER NOT NULL, "
            + TimeScheduleEntry.COLUMN_THURSDAY + " INTEGER NOT NULL, "
            + TimeScheduleEntry.COLUMN_FRIDAY + " INTEGER NOT NULL, "
            + TimeScheduleEntry.COLUMN_SATURDAY + " INTEGER NOT NULL);";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DAY);
        db.execSQL(CREATE_TABLE_SCHEDULES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +
                TimeSchedulerContract.TimeScheduleEntry.TABLE_SCHEDULES);

        db.execSQL("DROP TABLE IF EXISTS " +
                TimeSchedulerContract.TimeScheduleEntry.TABLE_DAY);

        onCreate(db);
    }
}
