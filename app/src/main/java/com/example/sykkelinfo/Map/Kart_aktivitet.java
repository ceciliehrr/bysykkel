package com.example.sykkelinfo.Map;


import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sykkelinfo.Model.Sykkelstasjon;
import com.example.sykkelinfo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

// Setter opp Google kart og plasserer markers basert på sykkelstasjonslista
// Engelske kommentarer kommer fra Google
//
/*
        Copyright 2017 Google Inc.
        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.

        https://developers.google.com/maps/documentation/android-sdk/marker

 */
public class Kart_aktivitet extends AppCompatActivity implements
        OnMapReadyCallback {

    ArrayList<Sykkelstasjon> stasjonsliste = new ArrayList<Sykkelstasjon>();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private final LatLng mDefaultLocation = new LatLng(59.913475, 10.755400);
    private static final int DEFAULT_ZOOM = 15;
    private static final String TAG = "Kart_aktivitet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.
                kart_aktivitet_view);


        final Button button = findViewById(R.id.home);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        stasjonsliste = this.getIntent().getParcelableArrayListExtra("lista");
        Log.d("Tostring:", "KARTLISTE" + stasjonsliste.toString());

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        // Add a marker in Oslo, lillegata 1,
        // and move the map's camera to the same location.
        LatLng oslo = new LatLng(59.913475, 10.755400);
        googleMap.addMarker(new MarkerOptions().position(oslo)
                .title("Lilletorget 1").icon(getMarkerIcon("#B88FBB")));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(oslo));
        googleMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));

        for (int i = 0; i < stasjonsliste.size(); i++) {
            String name = stasjonsliste.get(i).getName();
            Double lat = stasjonsliste.get(i).getLat();
            Double lon = stasjonsliste.get(i).getLon();
            int numSykler = stasjonsliste.get(i).getNum_bikes_available();
            int numLocks = stasjonsliste.get(i).getNum_docks_available();

            Log.d("LATLONG:", "LAT: " + lat + "Long: " + lon);
            LatLng LatLon = new LatLng(lat, lon);
            String snippet = "Antall sykler ledig: " + numSykler + " " + "Antall låser ledig: " + numLocks;
            Marker sykkelstasjoner = googleMap.addMarker(new MarkerOptions()
                    .position(LatLon)
                    .snippet(snippet)
                    .title(name)
                    .icon(getMarkerIcon("#2F2A35")));
            sykkelstasjoner.setTag(0);
        }
    }

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }
}
