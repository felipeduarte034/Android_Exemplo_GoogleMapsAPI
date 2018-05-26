package com.example.felipeduarteacer.googlemapsapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btEndereco = findViewById(R.id.btEnderecoID_MainAct);
        Button btLocAtual = findViewById(R.id.btLocAtualID_MainAct);
        Button btStreetView = findViewById(R.id.btStreetViewID_MainAct);

        btEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });

        btLocAtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Maps2Activity.class));
            }
        });

        btStreetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), Maps3Activity.class));
            }
        });
    }
}
