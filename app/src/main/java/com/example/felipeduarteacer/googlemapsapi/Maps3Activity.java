package com.example.felipeduarteacer.googlemapsapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

public class Maps3Activity extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback {

    private StreetViewPanorama panorama;
    private LatLng latLngCurrent, latLngEnd;
    private EditText etPlaceName;
    private String lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps3);
        etPlaceName = findViewById(R.id.etPlaceNameID_Maps3);
        //lat = getIntent().getExtras().getString("latitude");
        //lon = getIntent().getExtras().getString("longitude");

        StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager()
                .findFragmentById(R.id.fragmentMap3ID_Maps3);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        etPlaceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(Maps3Activity.this);
                    startActivityForResult(intent, 200);
                }
                catch (GooglePlayServicesNotAvailableException e)
                {
                    e.printStackTrace();
                }
                catch (GooglePlayServicesRepairableException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == RESULT_OK)
        {
            Place place = PlaceAutocomplete.getPlace(this, data);
            latLngEnd = place.getLatLng();
            if (latLngEnd != null)
            {
                panorama.setPosition(latLngEnd);
            }
        }
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        panorama = streetViewPanorama;
        //latLngCurrent = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        latLngCurrent = new LatLng(0.0000, 0.0000);
        streetViewPanorama.setPosition(latLngCurrent);
    }
}
