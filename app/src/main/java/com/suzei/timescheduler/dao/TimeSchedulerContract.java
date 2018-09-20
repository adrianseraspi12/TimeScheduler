package com.suzei.timescheduler.dao;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class TimeSchedulerContract {

    public static final String CONTENT_AUTHORITY = "com.suzei.timescheduler";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SCHEDULES = "schedules";

    public static abstract class TimeScheduleEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                BASE_CONTENT_URI,
                PATH_SCHEDULES);

        // Mime type of schedule list
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_SCHEDULES;

        //  Mime type of a single schedule
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_SCHEDULES;

        //  Table name
        public static final String TABLE_SCHEDULES = "schedules";
        public static final String TABLE_DAY ="day";

        //  Common column name
        public static final String KEY_ID = BaseColumns._ID;

        //  schedule column names
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_ACTIVE = "active";
        public static final String COLUMN_DAY_ID = "day_id";

        //  day column names
        public static final String COLUMN_SUNDAY = "sunday";
        public static final String COLUMN_MONDAY = "monday";
        public static final String COLUMN_TUESDAY = "tuesday";
        public static final String COLUMN_WEDNESDAY = "wednesday";
        public static final String COLUMN_THURSDAY = "thursday";
        public static final String COLUMN_FRIDAY = "friday";
        public static final String COLUMN_SATURDAY = "saturday";

        //  isActive
        public static final int FALSE = 0;
        public static final int TRUE = 1;
    }

}
