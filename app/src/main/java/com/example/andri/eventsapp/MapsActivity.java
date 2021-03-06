package com.example.andri.eventsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.andri.eventsapp.model.Event;
import com.example.andri.eventsapp.model.GpsTracker;
import com.example.andri.eventsapp.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapClickListener {

    private User user;
    private Event event;
    private List<Event> events;

    private Marker myMarker;
    private GoogleMap mMap;
    private GpsTracker gps;
    private LatLng myPosition;
    private LatLng myMarkerPosition;
    float zoomLevel;



    private AlertDialog.Builder dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dlg = new AlertDialog.Builder(this);

        Intent intent = getIntent();
        user = (User) intent.getExtras().get("user");
        event = (Event) intent.getExtras().get("event");
        events = (List<Event>) intent.getExtras().getSerializable("events");

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
            openDlg(getString(R.string.exLocation));
        }

        myPosition  = new LatLng(gps.getLatitude(), gps.getLongitude());

        zoomLevel = 16.0f;

        // Adds a marker if exists a location on event
        if(event.getLatitude() != null && event.getLongitude() != null) {
            myMarkerPosition = new LatLng(event.getLatitude(), event.getLongitude());
            mMap.addMarker(new MarkerOptions().position(myMarkerPosition).title(getString(R.string.sEventLocation)));
            zoomLevel = 10.0f;
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,zoomLevel));

        mMap.setOnMapClickListener(this);

    }


    @Override
    public void onMapClick(final LatLng latLng) {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoomLevel));
        myMarker= mMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.sEventLocation)));
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
                    event.setLatitude(latLng.latitude);
                    event.setLongitude(latLng.longitude);

                    Intent intent = new Intent(getBaseContext(),EventActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("event", event);
                    intent.putExtra("events", (Serializable) events);
                    finish();
                    startActivity(intent);

                }catch (Exception e){
                    openDlg(getString(R.string.exLocation));
                }

            }
        });
        alert = builder.create();
        alert.show();

        myMarker.remove();
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
