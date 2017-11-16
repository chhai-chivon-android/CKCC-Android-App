package kh.edu.rupp.ckccapp.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import kh.edu.rupp.ckccapp.R;

/**
 * CKCCApp
 * Created by leapkh on 10/24/17.
 */

public class ContactFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private final double ckccLat = 11.569307;
    private final double ckccLng = 104.888504;

    private MapView mapView;
    private GoogleMap googleMap;

    private Marker currentUserMarker;

    private GoogleApiClient googleApiClient;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("ckcc", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        mapView = (MapView) rootView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(getActivity());
        builder.addConnectionCallbacks(this);
        builder.addApi(LocationServices.API);
        googleApiClient = builder.build();
        googleApiClient.connect();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("ckcc", "onMapReady");
        this.googleMap = googleMap;

        addCkccMarkerAndZoomCamera();
    }

    private void addCkccMarkerAndZoomCamera() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("Cambodia-Korea Cooperation Center");
        markerOptions.snippet("CKCC");
        LatLng ckccLatLng = new LatLng(ckccLat, ckccLng);
        markerOptions.position(ckccLatLng);
        googleMap.addMarker(markerOptions);
        CameraUpdate ckccCameraPosition = CameraUpdateFactory.newLatLngZoom(ckccLatLng, 15);
        googleMap.animateCamera(ckccCameraPosition);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("ckcc", "onConnected");

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
            return;
        }

        // Get last known location
        Location lastKnowLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastKnowLocation != null) {
            Log.d("ckcc", "Last known location found");
            // Add marker for last known location
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title("You are here.");
            markerOptions.position(new LatLng(lastKnowLocation.getLatitude(), lastKnowLocation.getLongitude()));
            currentUserMarker = googleMap.addMarker(markerOptions);
        } else {
            Log.d("ckcc", "Last known location not found");
        }

        // Request for location
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000 * 5);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("ckcc", "onLocationChanged");
        if (currentUserMarker != null) {
            Log.d("ckcc", "Move current marker");
            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            currentUserMarker.setPosition(currentLatLng);
            //Move camera to updated location
            CameraUpdate currentCameraPosition = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15);
            googleMap.animateCamera(currentCameraPosition);
        }
    }
}

















