package com.example.android3_dz7_map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.android3_dz7_map.databinding.ActivityMainBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap gMap;
    private ActivityMainBinding binding;
    private List<LatLng> latLngList = new ArrayList<>();
    private PolylineOptions polylineOptions;
    private Prefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = new Prefs(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupListener();
    }

    private void setupListener() {
        binding.btnDrawActivityMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawLocations();
            }
        });

        binding.btnClearActivityMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLocation();
            }
        });
    }

    private void clearLocation() {
        latLngList.clear();
        gMap.clear();
        prefs.clearPrefs();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setOnMapClickListener(this);
        gMap.setOnMarkerClickListener(this);
        latLngList = prefs.getLocation();
        drawLocations();
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        gMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Marker"));
        latLngList.add(latLng);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        marker.remove();
        return false;
    }

    private void drawLocations() {
        if (latLngList != null) {
            polylineOptions = new PolylineOptions();
            polylineOptions.color(Color.DKGRAY);
            polylineOptions.width(10);
            for (LatLng location : latLngList) {
                polylineOptions.add(location);
            }
            gMap.addPolyline(polylineOptions);
            prefs.saveLocation(latLngList);
        }
    }
}