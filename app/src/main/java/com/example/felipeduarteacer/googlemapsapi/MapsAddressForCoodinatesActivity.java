package com.example.felipeduarteacer.googlemapsapi;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsAddressForCoodinatesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText etTextoLugar;
    private Button btSearch;
    private Marker mMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        etTextoLugar = findViewById(R.id.etTextoLugarID_MapLayout);
        btSearch = findViewById(R.id.btSearchID_MapLayout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMapsID_Maps);
        mapFragment.getMapAsync(this);

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindOnMap(v);
            }
        });
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

        if (mMap != null)
        {
            // Movimentar o marcador
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {
                    Toast.makeText(getApplicationContext(), "Marker Drag START", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    Toast.makeText(getApplicationContext(), "Marker Drag END", Toast.LENGTH_SHORT).show();
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    List<Address> list = null;
                    try
                    {
                        LatLng ll = marker.getPosition();
                        list = geocoder.getFromLocation(ll.latitude, ll.longitude, 1);
                        Address address = list.get(0);
                        marker.setTitle(address.getLocality());
                        marker.showInfoWindow();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            // Painel de informadoçõs do lugar marcado
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    View view = getLayoutInflater().inflate(R.layout.item_custom_address, null);
                    TextView tvLocality = view.findViewById(R.id.tvLocalityID_ca);
                    TextView tvLat = view.findViewById(R.id.tvLatitudeID_ca);
                    TextView tvLon = view.findViewById(R.id.tvLongitudeID_ca);
                    TextView tvSnippet = view.findViewById(R.id.tvSnippetID_ca);

                    LatLng latLng = marker.getPosition();
                    tvLocality.setText(marker.getTitle());
                    tvLat.setText(String.valueOf(latLng.latitude));
                    tvLon.setText(String.valueOf(latLng.longitude));
                    tvSnippet.setText(marker.getSnippet());
                    return view;
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.itemNoneID:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.itemNormalID:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.itemSatelliteID:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.itemHybridID:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.itemTerrainID:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void GoToLocation(double latitude, double longitude, int zoom)
    {
        if (mMarker != null)
        {
            mMarker.remove();
        }

        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions marker = new MarkerOptions();
        marker.title("SUCESSO + CAFE");
        marker.position(latLng);
        //marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)); //trocar cor do icone
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marcador)); //alterar a imagem do icone
        marker.draggable(true); //arratavel
        marker.snippet("texto snippet aqui!!!");
        mMarker = mMap.addMarker(marker);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(cameraUpdate);
    }

    public void FindOnMap(View v)
    {
        Geocoder geocoder = new Geocoder(this);
        try
        {
            List<Address> myListAddress = geocoder.getFromLocationName(etTextoLugar.getText().toString(), 1);
            if (myListAddress.size() > 0)
            {
                Address address = myListAddress.get(0);
                String locality = address.getLocality();
                String country = address.getCountryName();
                String subLocality = address.getSubLocality();
                String addressLine = address.getAddressLine(0);
                String premises = address.getPremises();
                String featureName = address.getFeatureName();
                double lat = address.getLatitude();
                double lon = address.getLongitude();
                Log.d("CAFE","******************************************************");
                Log.d("CAFE","----> Locality: " + locality + " - Country: " + country + " - supLocality: " + subLocality);
                Log.d("CAFE","----> premises: " + premises + " - featureName: " + featureName);
                Log.d("CAFE","----> addressLine: " + addressLine);
                Log.d("CAFE","----> lat: " + lat + " - lon: " + lon);
                GoToLocation(lat,lon,15);
            }
            else
            {
                Log.d("CAFE","myListAnddress zero");
                Toast.makeText(getApplicationContext(), "Lugar não encontrado", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
