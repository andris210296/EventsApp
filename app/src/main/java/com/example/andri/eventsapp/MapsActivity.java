package com.example.andri.eventsapp;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.andri.eventsapp.model.GpsTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GpsTracker gps;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        gps = new GpsTracker(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       if(gps.canGetLocation()){
           double latitude = gps.getLatitude();
           double longitude = gps.getLongitude();

           Toast.makeText(getApplicationContext(),"Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
       }else
           gps.showSettingsAlert();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {

        }

        LatLng myPosition = new LatLng(gps.getLatitude(), gps.getLongitude());

        float zoomLevel = 16.0f;
        mMap.addMarker(new MarkerOptions().position(myPosition).title(getString(R.string.sMyLocation)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,zoomLevel));

    }


}
