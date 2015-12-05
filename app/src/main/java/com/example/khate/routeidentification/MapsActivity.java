package com.example.khate.routeidentification;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    public void onClick(View view)throws IOException {
        //Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show();
        EditText location_tf = (EditText)findViewById(R.id.tv_destination);
        EditText location_tf1 = (EditText)findViewById(R.id.tv_source);

        String location = location_tf.getText().toString();
        String location1 = location_tf1.getText().toString();
        //Toast.makeText(this, location, Toast.LENGTH_SHORT).show();

        Geocoder gc = new Geocoder(this);

        List<Address> list = gc.getFromLocationName(location, 1);
        List<Address> list1 = gc.getFromLocationName(location1, 1);
        //Toast.makeText(this, list.get(0).toString(), Toast.LENGTH_SHORT).show();
        Address add = list.get(0);
        Address add1 = list1.get(0);

        String locality = add.getLocality();
        String locality1 = add1.getLocality();

        //Toast.makeText(this, locality, Toast.LENGTH_SHORT).show();

        double lat = add.getLatitude();
        double lng = add.getLongitude();
        double lat1 = add1.getLatitude();
        double lng1 = add1.getLongitude();

        gotoLocation(lat, lng, 6);
        gotoLocation(lat1, lng1, 6);

        setMarker(add.getLocality(),lat,lng);
        setMarker(add1.getLocality(),lat1,lng1);
    }

    private void setMarker(String locality, double lat, double lng) {
        /*if (marker != null) {
            marker.remove();
        }*/
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng (lat, lng))
                .title(locality);
        //.flat(true);
        marker = mMap.addMarker(options);
    }

    private void gotoLocation(double lat, double lng, float zoom){
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }
}
