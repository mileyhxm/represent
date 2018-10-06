package com.example.mileyhu.represent;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import android.location.Location;
import android.view.View;
import android.widget.*;
import android.content.Intent;

import java.util.Random;


public class Landing extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;
    public static String input;
    protected Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Button local = findViewById(R.id.button);
        Button random = findViewById(R.id.button2);

        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLocateActivity();
            }

        });

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRandomActivity();
            }
        });

    }

    private void launchLocateActivity() {
        Intent intent = new Intent(this, Locate.class);
        startActivity(intent);
    }

    private void launchRandomActivity() {
        Intent intent = new Intent(this, Results.class);
        int index = new Random().nextInt(41465);
        Resources res = getResources();
        String[] zip_array = res.getStringArray(R.array.zip_codes);
        input = "geocode?q=" + zip_array[index];
        intent.putExtra("input", input);
        Results.isRandom = true;
        startActivity(intent);
    }
}

