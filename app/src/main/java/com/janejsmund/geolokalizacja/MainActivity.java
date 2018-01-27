package com.janejsmund.geolokalizacja;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.LinkedList;
import java.util.List;

import static com.janejsmund.geolokalizacja.DatabaseContract.DatabaseEntry.COLUMN_DESCRIPTION;
import static com.janejsmund.geolokalizacja.DatabaseContract.DatabaseEntry.COLUMN_LATITUDE;
import static com.janejsmund.geolokalizacja.DatabaseContract.DatabaseEntry.COLUMN_LONGITUDE;
import static com.janejsmund.geolokalizacja.DatabaseContract.DatabaseEntry.COLUMN_NAME;
import static com.janejsmund.geolokalizacja.DatabaseContract.DatabaseEntry.COLUMN_RADIUS;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private LinkedList<Geofence> geofences = new LinkedList<>();
    PendingIntent mGeofencePendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        refreshMarkers();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;

        setUpMap();
    }

    private void setUpMap() {

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(52.0, 20.0)));

        refreshMarkers();
    }

    public void goToLocationActivity(View view) {
        try {
            Intent intent = new Intent(this, NewLocationActivity.class);
            startActivity(intent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private void refreshMarkers() {

        List<MyLocation> locations = new LinkedList<>();

        SQLiteDatabase db;

        DatabaseHelper helper = new DatabaseHelper(this);
        db = helper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseContract.DatabaseEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        MyLocation location = new MyLocation();

        if (cursor.moveToFirst()) {
            do {

                location.setNazwa(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                location.setOpis(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                location.setPromien(cursor.getString(cursor.getColumnIndex(COLUMN_RADIUS)));
                location.setLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)));
                location.setLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE)));

                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    Log.i("cur", cursor.getString(i));
                }

                locations.add(location);

            } while (cursor.moveToNext());
        }

        cursor.close();

        MarkerOptions markerOptions;
        CircleOptions circleOptions;
        LatLng latLng;
        GeofencingClient client = LocationServices.getGeofencingClient(this);


        for (MyLocation location1 : locations) {
            latLng = new LatLng(Double.valueOf(location1.getLatitude()), Double.valueOf(location1.getLongitude()));
            markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(location1.getNazwa());
            circleOptions = new CircleOptions()
                    .center(latLng)
                    .radius(Double.valueOf(location1.getPromien()))
                    .fillColor(0x44ff0000)
                    .strokeColor(0xffff0000)
                    .strokeWidth(8);
            googleMap.addMarker(markerOptions);
            googleMap.addCircle(circleOptions);

            geofences.add(new Geofence.Builder()
                .setRequestId(location1.getNazwa())
                .setCircularRegion(
                        Double.valueOf(location1.getLatitude()),
                        Double.valueOf(location1.getLongitude()),
                        Float.valueOf(location1.getPromien())
                )
            .setExpirationDuration(60_000)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
            .build());


        }

        client.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("geof", "added");
                    }
                });


    }

    private GeofencingRequest getGeofencingRequest() {

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        builder.addGeofences(geofences);

        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {

        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }

        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);

        mGeofencePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);

        return mGeofencePendingIntent;
    }
}
