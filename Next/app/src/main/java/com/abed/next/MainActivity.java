package com.abed.next;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    EditText editTextSpd;
    EditText editTextTime;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnUpdate = findViewById(R.id.btnUpdate);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentFocus();
        }
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);


    }

    private void getCurrentLocation() {

        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {


            double lat1;//start latitude point
            double lon1;//start longitude point

            double bearing;//distance calculation

            @Override
            public void onSuccess(Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onMapReady(GoogleMap googleMap) {


                        googleMap.setMyLocationEnabled(true);

                        googleMap.getUiSettings().setMyLocationButtonEnabled(true);


                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        lat1 = location.getLatitude();
                        lon1 = location.getLongitude();
                        bearing = location.getBearing();

                        MarkerOptions options = new MarkerOptions().position(latLng).title("Here, I'm..");

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 1000000000));
                        googleMap.addMarker(options);

                        Intent intentGetLon = getIntent();
                        String[] stringLon = intentGetLon.getStringArrayExtra("stringLon");
                        String[] stringLat = intentGetLon.getStringArrayExtra("stringLat");


                        for (int i = 0; i < stringLat.length; i++) {
                            double lat = Double.parseDouble(stringLat[i]);
                            double lon = Double.parseDouble(stringLon[i]);
                            LatLng latLng2 = new LatLng(lat, lon);
                            MarkerOptions options2 = new MarkerOptions().position(latLng2);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 1000000000));
                            googleMap.addMarker(options2.icon(BitmapDescriptorFactory.defaultMarker(150)));
                            googleMap.addPolyline(new PolylineOptions()
                                    .add(latLng, latLng2)
                                    .width(5)
                                    .color(Color.RED));

                            btnUpdate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intentInputLonLat = new Intent(getApplicationContext(), HomePage.class);
                                    startActivity(intentInputLonLat);
                                }
                            });
                        }




//                        btnNEXT.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                                double speed = Double.parseDouble(editTextSpd.getText().toString());
//                                double time = Double.parseDouble(editTextTime.getText().toString());
//
//                                double distance = speed * time;//distance calculation
//
//
//                                final double r = 6371 * 1000; // Earth Radius in m
//
//                                double lat2 = Math.asin(Math.sin(Math.toRadians(lat1)) * Math.cos(distance / r)
//                                        + Math.cos(Math.toRadians(lat1)) * Math.sin(distance / r) * Math.cos(Math.toRadians(bearing)));
//                                double lon2 = Math.toRadians(lon1)
//                                        + Math.atan2(Math.sin(Math.toRadians(bearing)) * Math.sin(distance / r) * Math.cos(Math.toRadians(lat1)), Math.cos(distance / r)
//                                        - Math.sin(Math.toRadians(lat1)) * Math.sin(lat2));
//                                lat2 = Math.toDegrees(lat2);
//                                lon2 = Math.toDegrees(lon2);
//
//                                LatLng latLng2 = new LatLng(lat2, lon2);
//
//                                MarkerOptions options2 = new MarkerOptions().position(latLng2).title("Here, I'l be there..");
//
//                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 1000000000));
//                                googleMap.addMarker(options2);
//
//
//                            }
//                        });


                    }
                });

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }
}