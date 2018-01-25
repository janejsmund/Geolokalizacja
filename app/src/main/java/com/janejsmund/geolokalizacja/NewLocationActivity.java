package com.janejsmund.geolokalizacja;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewLocationActivity extends AppCompatActivity {

    EditText edtNazwa, edtOpis, edtPromien;

    LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);

        edtNazwa = findViewById(R.id.edtNazwa);
        edtOpis = findViewById(R.id.edtNazwa);
        edtPromien = findViewById(R.id.edtPromien);

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationTracker.stopUsingLocation();
    }

    public void addLocation(View view) {

        locationTracker = new LocationTracker(this);
        String latitude, longitude;
        String nazwa, opis, promien;
        DatabaseHelper helper;
        SQLiteDatabase db;

        if (locationTracker.isLocationPermissionEnabled()) {

            nazwa = edtNazwa.getText().toString();
            opis = edtOpis.getText().toString();
            promien = edtPromien.getText().toString();

            latitude = String.valueOf(locationTracker.getLatitude());
            longitude = String.valueOf(locationTracker.getLongitude());

            helper = new DatabaseHelper(this);
            db = helper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put(DatabaseContract.DatabaseEntry.COLUMN_NAME, nazwa);
            contentValues.put(DatabaseContract.DatabaseEntry.COLUMN_DESCRIPTION, opis);
            contentValues.put(DatabaseContract.DatabaseEntry.COLUMN_RADIUS, promien);
            contentValues.put(DatabaseContract.DatabaseEntry.COLUMN_LATITUDE, latitude);
            contentValues.put(DatabaseContract.DatabaseEntry.COLUMN_LONGITUDE, longitude);

            db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, contentValues);
        }
        else {
            locationTracker.askToEnableLocationPermission();
        }


    }
}
