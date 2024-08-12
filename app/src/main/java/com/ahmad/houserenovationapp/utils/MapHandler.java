package com.ahmad.houserenovationapp.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

/** @noinspection ALL*/
public class MapHandler {
    private final MapView mapView;
    private GoogleMap mMap;
    private final Context context;
    private Marker selectedLocationMarker;
    private final FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_REQUEST_CODE = 1000;

    public MapHandler(Context context, MapView mapView, OnMapReadyCallback callback) {
        this.context = context;
        this.mapView = mapView;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        initMap(context, callback);
    }

    private void initMap(Context context, OnMapReadyCallback callback) {
        // Initialize the map view
        mapView.onCreate(null);
        mapView.onResume(); // Needed to get the map to display immediately

        // Initialize Google Play Services
        try {
            MapsInitializer.initialize(context.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the callback for when the map is ready
        mapView.getMapAsync(googleMap -> {
            this.mMap = googleMap;
            setupMap(googleMap);
            callback.onMapReady(googleMap);
        });
    }

    private void setupMap(GoogleMap googleMap) {
        mMap = googleMap;
        // Check for location permissions
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(
                    (FragmentActivity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
            return;
        }

        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        // Get the last known location
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((FragmentActivity) context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
                        }
                    }
                });

        // Set a click listener for the map
        mMap.setOnMapClickListener(latLng -> {
            if (selectedLocationMarker != null) {
                selectedLocationMarker.remove();
            }

            selectedLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
            selectedLocationMarker.showInfoWindow();
        });
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mMap != null) {
                    setupMap(mMap);  // Retry enabling location
                }
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Marker getSelectedLocationMarker(){
        return selectedLocationMarker;
    }
    public GoogleMap getmMap() {
        return mMap;
    }

    public void onResume() {
        mapView.onResume();
    }

    public void onPause() {
        mapView.onPause();
    }

    public void onDestroy() {
        mapView.onDestroy();
    }

    public void onLowMemory() {
        mapView.onLowMemory();
    }

    public void onSaveInstanceState(Bundle outState) {
        mapView.onSaveInstanceState(outState);
    }
}
