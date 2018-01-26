package com.janejsmund.geolokalizacja;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.janejsmund.geolokalizacja.DatabaseContract.*;
import static com.janejsmund.geolokalizacja.DatabaseContract.DatabaseEntry.COLUMN_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "locations.db";

    private static final String SQL_CREATE_LOCATIONS =
            "CREATE TABLE " + DatabaseEntry.TABLE_NAME + " (" +
                    DatabaseEntry._ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    DatabaseEntry.COLUMN_DESCRIPTION + " TEXT," +
                    DatabaseEntry.COLUMN_RADIUS + " TEXT," +
                    DatabaseEntry.COLUMN_LATITUDE + " TEXT," +
                    DatabaseEntry.COLUMN_LONGITUDE + " TEXT)";

    private static final String SQL_DELETE_LOCATIONS =
            "DROP TABLE IF EXISTS " + DatabaseEntry.TABLE_NAME;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_LOCATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_LOCATIONS);
        onCreate(sqLiteDatabase);
    }
}
