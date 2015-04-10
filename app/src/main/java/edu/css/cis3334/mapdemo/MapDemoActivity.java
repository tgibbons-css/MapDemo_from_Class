package edu.css.cis3334.mapdemo;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapDemoActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_demo);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
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

    /**
     * convert a street address to a LatLng location for mapping
     * This method can be used to convert a address string into a LatLng location
     * @param address
     */
    private LatLng mapAddress(String address) {
        double latitude;
        double longitude;
        List<Address> geocodeMatches = null;        // list of possible addresses returned

        try {
            // Get list of top 1 locations that match this address string
            geocodeMatches = new Geocoder(this).getFromLocationName(address, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // make sure an address was returned
        if (!geocodeMatches.isEmpty())
        {
            // get lat long of first result returned (0)
            latitude = geocodeMatches.get(0).getLatitude();
            longitude = geocodeMatches.get(0).getLongitude();
            return(new LatLng(latitude, longitude));
        } else {
            return null;
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(46.8156057,-92.1064674)).title("Our Classroom"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(46.9071467,-92.2019079)).title("Tom's House"));
        mMap.addMarker(new MarkerOptions().position(mapAddress("1200 Kenwood Ave., Duluth, MN")).title("CSS"));

        //final LatLng CLASSROOM_LATLNG = new LatLng(46.815661,-92.106594);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(46.815661,-92.106594), 15));
    }
}
