package com.abed.next.Activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.abed.next.DbManager.DBManagerLatLon;
import com.abed.next.DbManager.DBManagerUpdate;
import com.abed.next.R;
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
    Button btnUpdate;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        finishAffinity();
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

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

            double bearing;//distance calculation
            LatLng[] latLng2 = new LatLng[10];

            @Override
            public void onSuccess(Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onMapReady(GoogleMap googleMap) {

                        googleMap.setMyLocationEnabled(true);

                        googleMap.getUiSettings().setMyLocationButtonEnabled(true);


                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        bearing = location.getBearing();

                        MarkerOptions options = new MarkerOptions().position(latLng).title("Here, I'm..");

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        googleMap.addMarker(options);

                        DBManagerLatLon dbManagerLatLon = new DBManagerLatLon(getApplicationContext());
                        dbManagerLatLon.open();
                        Cursor cursor = dbManagerLatLon.fetchData();
                        cursor.moveToFirst();


                        DBManagerUpdate dbManagerUpdate = new DBManagerUpdate(getApplicationContext());
                        dbManagerUpdate.open();
                        Cursor cursorUpdate = dbManagerUpdate.fetchData();
                        cursorUpdate.moveToFirst();

                        String[] Sname = new String[100];
                        String SnameU;
                        int count = 0;
                        while (cursor.moveToNext()) {
                            Sname[count] = cursor.getString(1);
                            double lat = Double.parseDouble(cursor.getString(2));
                            double lon = Double.parseDouble(cursor.getString(3));

                            latLng2[count] = new LatLng(lat, lon);

                            MarkerOptions options2 = new MarkerOptions().position(latLng2[count]).title(Sname[count]);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2[count], 10));
                            googleMap.addMarker(options2.icon(BitmapDescriptorFactory.defaultMarker(150)));
                            googleMap.addPolyline(new PolylineOptions()
                                    .add(latLng, latLng2[count])
                                    .width(5)
                                    .color(Color.RED));
                            count++;

                        }

                        while (cursorUpdate.moveToNext()) {

                            SnameU = cursorUpdate.getString(1);
                            double latU = Double.parseDouble(cursorUpdate.getString(2));
                            double lonU = Double.parseDouble(cursorUpdate.getString(3));

                            for (int i = 0; i < count; i++) {
                                if (Sname[i].equals(SnameU)) {
                                    LatLng latLng3 = new LatLng(latU, lonU);
                                    MarkerOptions options3 = new MarkerOptions().position(latLng3).title(SnameU);
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng3, 10));
                                    googleMap.addMarker(options3.icon(BitmapDescriptorFactory.defaultMarker(150)));
                                    googleMap.addPolyline(new PolylineOptions()
                                            .add(latLng2[i], latLng3)
                                            .width(5)
                                            .color(Color.GREEN));
                                    latLng2[i] = latLng3;
                                }
                            }
                        }
                        btnUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, UpdateTable.class);
                                startActivity(intent);
                            }
                        });
                        dbManagerLatLon.close();
                        dbManagerUpdate.close();
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