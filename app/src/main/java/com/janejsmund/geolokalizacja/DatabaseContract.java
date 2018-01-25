package com.janejsmund.geolokalizacja;

import android.provider.BaseColumns;

final class DatabaseContract {

    private DatabaseContract() {}

    static class DatabaseEntry implements BaseColumns {
        static final String TABLE_NAME = "locations";
        static final String COLUMN_NAME = "name";
        static final String COLUMN_DESCRIPTION = "description";
        static final String COLUMN_RADIUS = "radius";
        static final String COLUMN_LATITUDE = "latitude";
        static final String COLUMN_LONGITUDE = "longitude";
    }
}
