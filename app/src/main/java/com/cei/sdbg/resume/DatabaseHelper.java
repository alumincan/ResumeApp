package com.cei.sdbg.resume;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2016/10/1.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "resume.db";

    public static class JobFunction implements BaseColumns {
        public static final String TABLE_NAME = "job_functions";
        public static final String COLUMN_NAME = "name";
    }

    public static class Event implements BaseColumns {
        public static final String TABLE_NAME = "events";
        public static final String COLUMN_NAME = "name";
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_JOBFUNCTIONS_TABLE = "CREATE TABLE " + JobFunction.TABLE_NAME + "(" +
                JobFunction._ID + " INTEGER PRIMARY KEY," +
                JobFunction.COLUMN_NAME + " TEXT)";
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + Event.TABLE_NAME + "(" +
                Event._ID + " INTEGER PRIMARY KEY," +
                Event.COLUMN_NAME + " TEXT)";

        db.execSQL(CREATE_JOBFUNCTIONS_TABLE);
        db.execSQL(CREATE_EVENTS_TABLE);

        populateDefaultJobFunctions(db);
        populateDefaultEvetns(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    void populateDefaultJobFunctions (SQLiteDatabase db) {
        String[] defaultJobFunctions = mContext.getResources().getStringArray(R.array.array_job_function);

        for (String job : defaultJobFunctions) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(JobFunction.COLUMN_NAME, job);
            db.insert(JobFunction.TABLE_NAME, null, contentValues);
        }
    }

    void populateDefaultEvetns (SQLiteDatabase db) {
        String[] defaultEvents = mContext.getResources().getStringArray(R.array.array_events);

        for (String event : defaultEvents) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(Event.COLUMN_NAME, event);
            db.insert(Event.TABLE_NAME, null, contentValues);
        }
    }

    List<String> getAllJobFunctions() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> allJobFunctions = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + JobFunction.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    allJobFunctions.add(cursor.getString(1));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();

        return allJobFunctions;
    }

    void removeJobFunction(String jobFunction) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = JobFunction.COLUMN_NAME + "=?";
        String[] selectionArgs = {jobFunction};

        db.delete(JobFunction.TABLE_NAME, selection, selectionArgs);

        db.close();
    }
    void removeMultiJobFunction(List<String> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = JobFunction.COLUMN_NAME + "=?";
        for (String jobs : list) {
            String[] selectionArgs = {jobs};

            db.delete(JobFunction.TABLE_NAME, selection, selectionArgs);
        }

        db.close();
    }

    void addJobFunction(String jobFunction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(JobFunction.COLUMN_NAME, jobFunction);

        db.insert(JobFunction.TABLE_NAME, null, values);
        db.close();
    }

    boolean isJobFunctionExisted(String jobFunction) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =
                "SELECT  * FROM " + JobFunction.TABLE_NAME + " WHERE " + JobFunction.COLUMN_NAME +"=?";

        String[] selectionArgs = {jobFunction};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return true;
            }
            cursor.close();
        }
        db.close();

        return false;
    }

    boolean isEventExisted(String event) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =
                "SELECT  * FROM " + Event.TABLE_NAME + " WHERE " + Event.COLUMN_NAME +"=?";
        String[] selectionArgs = {event};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return true;
            }
            cursor.close();
        }

        db.close();

        return false;
    }

    void addEvent(String event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Event.COLUMN_NAME, event);

        db.insert(Event.TABLE_NAME, null, values);
        db.close();
    }

    void removeEvent(String event) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = Event.COLUMN_NAME + "=?";
        String[] selectionArgs = {event};

        db.delete(Event.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    List<String> getAllEvents() {
        List<String> allEvents = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + Event.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    allEvents.add(cursor.getString(1));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();

        return allEvents;
    }
}