package kh.edu.rupp.ckccapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kh.edu.rupp.ckccapp.R;

/**
 * CKCCApp
 * Created by leapkh on 10/24/17.
 */

public class ContactFragment extends Fragment implements OnMapReadyCallback {

    private final double ckccLat = 11.569307;
    private final double ckccLng = 104.888504;

    private MapView mapView;
    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("ckcc", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        mapView = (MapView)rootView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

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

    private void addCkccMarkerAndZoomCamera(){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("Cambodia-Korea Cooperation Center");
        markerOptions.snippet("CKCC");
        LatLng ckccLatLng = new LatLng(ckccLat, ckccLng);
        markerOptions.position(ckccLatLng);
        googleMap.addMarker(markerOptions);
        CameraUpdate ckccCameraPosition = CameraUpdateFactory.newLatLngZoom(ckccLatLng, 15);
        googleMap.animateCamera(ckccCameraPosition);
    }

}

















