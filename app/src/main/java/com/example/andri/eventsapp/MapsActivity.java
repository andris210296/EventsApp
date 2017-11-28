package com.example.andri.eventsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private GpsTracker gps;
    private LatLng myPosition;
    float zoomLevel;

    private AlertDialog.Builder dlg;

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

        myPosition  = new LatLng(gps.getLatitude(), gps.getLongitude());

        zoomLevel = 16.0f;
        //mMap.addMarker(new MarkerOptions().position(myPosition).title(getString(R.string.sMyLocation)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,zoomLevel));

        mMap.setOnMapClickListener(this);

    }


    @Override
    public void onMapClick(final LatLng latLng) {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoomLevel));
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.sMyLocation)));
        AlertDialog alert;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.sSetLocation));
        builder.setMessage(R.string.sQMyLocation);
        builder.setNegativeButton(R.string.sBtnCancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                return;
            }
        });
        builder.setPositiveButton(R.string.sBtnLocation, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                try{

                    finish();

                }catch (Exception e){
                    openDlg(getString(R.string.exLocation));
                }

            }
        });
        alert = builder.create();
        alert.show();

        marker.remove();
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,zoomLevel));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void openDlg(String message) {
        dlg.setMessage(message.toString());
        dlg.setNeutralButton("OK", null);
        dlg.show();
    }

}
