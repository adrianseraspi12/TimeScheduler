package com.suzei.timescheduler.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public abstract class CrudImplementation<T> {

    private Context context;
    protected SQLiteDatabase mDatabase;

    CrudImplementation(Context context) {
        this.context = context;
        DatabaseManager.initializeInstance(context);
        open();
    }

    public void open() {
        mDatabase = DatabaseManager.getInstance().openDatabase();
    }

    public void close() {
        DatabaseManager.getInstance().closeDatabase();
    }

    protected Context getContext() {
        return context;
    }

    public abstract void create(T model);

    public abstract T readSingleRow();

    public abstract List<T> readAllRows();

    public abstract void update(T model);

    public abstract void delete(T model);


}
