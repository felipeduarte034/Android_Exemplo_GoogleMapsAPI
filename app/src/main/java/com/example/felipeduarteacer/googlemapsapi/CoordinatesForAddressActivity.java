package com.example.felipeduarteacer.googlemapsapi;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class CoordinatesForAddressActivity extends AppCompatActivity {

    private TextView tvLatitude;
    private TextView tvLongitude;

    private TextView tvCEP;
    private TextView tvRua;
    private TextView tvBairro;
    private TextView tvCidade;
    private TextView tvEstado;
    private TextView tvPais;
    private TextView tvLine;

    private LocationManager mManager;
    private Location mLocation;
    private Address mAddress;

    private double mLatitude = 38.8975708;
    private double mLongitude = -77.0374023;

    //38.8975708,-77.0374023

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordenate_for_andreess);

        tvLatitude = findViewById(R.id.tvLatitudeID_CoordAddr);
        tvLongitude = findViewById(R.id.tvLongitudeID_CoordAddr);
        tvCEP = findViewById(R.id.tvCepID_CoordAddr);
        tvRua = findViewById(R.id.tvRuaID_CoordAddr);
        tvBairro = findViewById(R.id.tvBairroID_CoordAddr);
        tvCidade = findViewById(R.id.tvCidadeID_CoordAddr);
        tvEstado = findViewById(R.id.tvEstadoID_CoordAddr);
        tvPais = findViewById(R.id.tvPaisID_CoordAddr);
        tvLine = findViewById(R.id.tvLineID_CoordAddr);

        tvLatitude.setText("Latitude: " + mLatitude);
        tvLongitude.setText("Longitude: " + mLongitude);

        try
        {
            mAddress = BuscaEnderecoComCoordenadas(mLatitude, mLongitude, getApplicationContext());

            tvLine.setText("Line: " + mAddress.getAddressLine(0));
            tvCEP.setText("CEP: " + mAddress.getPostalCode());
            tvRua.setText("Rua: " + mAddress.getThoroughfare());
            tvBairro.setText("Bairro: " + mAddress.getSubLocality());
            tvCidade.setText("Cidade: " + mAddress.getLocality());
            tvEstado.setText("Estado: " + mAddress.getAdminArea());
            tvPais.setText("Pais: " + mAddress.getCountryName());

            Log.i("cafe", "getSubThoroughfare: " + mAddress.getSubThoroughfare());
            Log.i("cafe", "getFeatureName: " + mAddress.getFeatureName());
            Log.i("cafe", "getCountryCode: " + mAddress.getCountryCode());
            Log.i("cafe", "getPhone: " + mAddress.getPhone());
            Log.i("cafe", "getPremises: " + mAddress.getPremises());
            Log.i("cafe", "getUrl: " + mAddress.getUrl());
            Log.i("cafe", "getExtras: " + mAddress.getExtras());
            Log.i("cafe", "getLocale: " + mAddress.getLocale());

        }
        catch (IOException e)
        {
            Log.i("cafe", e.getMessage());
        }
    }

    private Address BuscaEnderecoComCoordenadas(Double latitude, Double longitude, Context context) throws IOException {
        Geocoder geocoder;
        Address address = null;
        List<Address> addressList;

        geocoder = new Geocoder(context);

        addressList = geocoder.getFromLocation(latitude, longitude, 1);

        if (addressList.size() > 0)
        {
            address = addressList.get(0);
        }
        return address;
    }
}
