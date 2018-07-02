package com.example.felipeduarteacer.googlemapsapi;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {

    private final int PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission( getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ShowDialogExplicacao(this);
                Log.i("cafe", "--> explicaçao");
                //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
            }
            else
            {
                Log.i("cafe", "--> request");
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
            }
        }
    }

    private void ShowDialogExplicacao(final Activity activity)
    {
        new AlertDialog.Builder(this)
                .setTitle("Localização do Dispositivo")
                .setMessage("Para continuar, forneça a permissão, clicando em ALLOW.")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
                            }
                        })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("cafe", "--> Permitido");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Log.i("cafe", "--> Negado");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(),"Permita o acesso a localização do dispositivo clicando em ALLOW!",Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(getApplicationContext(), LocationActivity.class));
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
