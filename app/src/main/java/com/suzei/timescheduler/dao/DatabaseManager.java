package com.suzei.timescheduler.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 *  Concurrent Database Access
 *  Prevent SQLiteErrors
 *
 *  SQLiteDatabaseLockedException: database is locked (code 5)
 *  IllegalStateException: SQLiteDatabase created and never closed
 *  IllegalStateException: attempt to re-open an already-closed object: SQLiteDatabase
 */

public class DatabaseManager {

    private int mOpenCounter;

    private static DatabaseManager instance;
    private static DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDBHelper = new DatabaseHelper(context);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if (mOpenCounter == 1) {
            //  Opening a database
            mDatabase = mDBHelper.getWritableDatabase();
        }

        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter--;
        if (mOpenCounter == 0) {
            //  closing database
            mDatabase.close();
        }
    }

}
