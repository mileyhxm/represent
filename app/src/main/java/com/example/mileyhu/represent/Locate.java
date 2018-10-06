package com.example.mileyhu.represent;

import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.Locale;

public class Locate extends AppCompatActivity{
    private FusedLocationProviderClient mFusedLocationClient;

    protected Location mLastLocation;
    private TextView mLatitudeText;
    private TextView mLongitudeText;
    private EditText zipCode;
    public static String input;
    private String lat;
    private String lon;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLatitudeText = findViewById(R.id.latitude);
        mLongitudeText = findViewById(R.id.longitude);
        zipCode = findViewById(R.id.zipCode);
        final TextView warning1 = findViewById(R.id.warning_zip);
        warning1.setVisibility(View.INVISIBLE);

        Button locate = findViewById(R.id.locate);
        ImageButton search = findViewById(R.id.search1);

        zipCode = findViewById(R.id.zipCode);

        View.OnClickListener locateOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*setContentView(R.layout.local_result);*/
                    launchResultsCurr();
            }
        };
        locate.setOnClickListener(locateOnClickListener);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zipCode.getText().toString().length() == 5) {
                    launchResultsZip();
                } else {
                    warning1.setVisibility(View.VISIBLE);
                }
            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();

        if (checkPermissions()) {
            getLastLocation();
        } else {
            startLocationPermissionRequest();
        }
    }

    public boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(Locate.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }


    private void getLastLocation() {
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLastLocation = location;
                                lat = String.valueOf(mLastLocation.getLatitude());
                                lon = String.valueOf(mLastLocation.getLongitude());
                                mLatitudeText.setText(lat);
                                mLongitudeText.setText(lon);
                            }
                            else {
                                startLocationPermissionRequest();
                            }
                        }
                    });
        } catch (SecurityException e) {
            mLatitudeText.setText("nopermission");
        }
    }

    private void launchResultsZip() {
        Intent intent = new Intent(this, Results.class);
        input = "geocode?q=" + zipCode.getText().toString();
        intent.putExtra("input", input);
        Results.isRandom = false;
        startActivity(intent);
    }

    private void launchResultsCurr() {
        Intent intent = new Intent(this, Results.class);
        input = "reverse?q=" + mLatitudeText.getText().toString() + "," + mLongitudeText.getText().toString();
        intent.putExtra("input", input);
        Results.isRandom = false;
        startActivity(intent);
    }
}


