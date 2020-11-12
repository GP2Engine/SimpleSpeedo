package com.example.android_ergasia2;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/*
Kollias Anastasios
 */

public class UserExceedMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    dbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exceed_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        db = new dbHelper(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*// Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
         */

        Cursor c = db.getAllExcesses();
        final LatLngBounds.Builder mapBuilder = new LatLngBounds.Builder();
        boolean addedMarker = false;

        if(c.getCount() == 0){
            Toast.makeText(this, "No speed limit exceedances found.",Toast.LENGTH_LONG).show();
            Intent i = new Intent(UserExceedMaps.this,Menu.class); //going to the menu activity
            startActivity(i);
        }
        else
        {
            while(c.moveToNext())
            {
                double longitude = c.getDouble(c.getColumnIndex("longitude"));
                double latitude = c.getDouble(c.getColumnIndex("latitude"));

                MarkerOptions options = new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .anchor(0.5F, 1.0F)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                Marker marker = googleMap.addMarker(options);
                mapBuilder.include(marker.getPosition());
                addedMarker = true;
            }
            if (!c.isClosed()) {
                c.close();
                c = null;
            }
        }
        if (addedMarker) {
            final LatLngBounds mapBounds = mapBuilder.build();
            final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(mapBounds, 0);
            googleMap.moveCamera(cameraUpdate);
        }
    }
}
