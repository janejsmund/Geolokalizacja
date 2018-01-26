package com.janejsmund.geolokalizacja;

import android.provider.BaseColumns;

final class DatabaseContract {

    private DatabaseContract() {}

    static class DatabaseEntry implements BaseColumns {
        static final String TABLE_NAME = "locations";
        static final String _ID = "id";
        static final String COLUMN_NAME = "name";
        static final String COLUMN_DESCRIPTION = "description";
        static final String COLUMN_RADIUS = "radius";
        static final String COLUMN_LATITUDE = "latitude";
        static final String COLUMN_LONGITUDE = "longitude";

        static final String[] COLUMNS = new String[] {_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_RADIUS, COLUMN_LATITUDE, COLUMN_LONGITUDE};
    }
}
