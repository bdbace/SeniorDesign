package com.example.seniordesign.ui.maps;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.seniordesign.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment fragment;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_maps, container, false);
        //return inflater.inflate(R.layout.activity_maps, container, false);
        final SupportMapFragment myMapF = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        myMapF.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            LatLng marker = new LatLng(36.02329252, -115.29980468);
            LatLng stop = new LatLng(36.023035, -115.303505);
            Polyline polyline1 = mMap.addPolyline(new PolylineOptions().clickable(true).add(
                    new LatLng(36.023292, -115.299804),
                    new LatLng(36.023292, -115.299804),
                    new LatLng(36.023292, -115.299804),
                    new LatLng(36.023292, -115.299804),
                    new LatLng(36.023292, -115.299804),
                    new LatLng(36.023293, -115.299804),
                    new LatLng(36.023293, -115.299804),
                    new LatLng(36.023292, -115.299804),
                    new LatLng(36.023292, -115.299804),
                    new LatLng(36.023291, -115.299803),
                    new LatLng(36.023290, -115.299803),
                    new LatLng(36.023289, -115.299802),
                    new LatLng(36.023288, -115.299801),
                    new LatLng(36.023287, -115.299801),
                    new LatLng(36.023286, -115.299800),
                    new LatLng(36.023286, -115.299799),
                    new LatLng(36.023287, -115.299795),
                    new LatLng(36.023288, -115.299792),
                    new LatLng(36.023289, -115.299790),
                    new LatLng(36.023291, -115.299787),
                    new LatLng(36.023293, -115.299784),
                    new LatLng(36.023296, -115.299781),
                    new LatLng(36.023299, -115.299779),
                    new LatLng(36.023303, -115.299777),
                    new LatLng(36.023307, -115.299775),
                    new LatLng(36.023213, -115.301366),
                    new LatLng(36.022262, -115.301351),
                    new LatLng(36.022213, -115.303505),
                    new LatLng(36.023035, -115.303505))
            .color(Color.RED).width(25));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 16));
            mMap.addMarker(new MarkerOptions().title("Start").position(marker));
            mMap.addMarker(new MarkerOptions().title("End").position(stop));
        }
    }
}

