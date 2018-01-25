package com.janejsmund.geolokalizacja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements OnMapReadyCallback{

    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.map);
        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;

        setUpMap();
    }

    private void setUpMap() {

        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
}
