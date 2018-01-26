package com.janejsmund.geolokalizacja;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NewLocationActivity extends Activity {

    EditText hNazwa, hOpis, hPromien;

    LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);

        hNazwa = findViewById(R.id.edtNazwa);
        hOpis = findViewById(R.id.edtOpis);
        hPromien = findViewById(R.id.edtPromien);

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

            nazwa = hNazwa.getText().toString();
            opis = hOpis.getText().toString();
            promien = hPromien.getText().toString();

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
            Log.i("wpis", "Dodano wpis do bazy danych: Nazwa: " + nazwa + ", Opis: " + opis + ", Promień: " + promien + ", latitude: " + latitude + ", longitude: " + longitude);
        }
        else {
            locationTracker.askToEnableLocationPermission();
        }


    }
}
