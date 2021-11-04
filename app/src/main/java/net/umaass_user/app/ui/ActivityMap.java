package net.umaass_user.app.ui;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.ui.base.BaseActivity;
import net.umaass_user.app.utils.MapDirction;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.permission.helper.PermissionHelper;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class ActivityMap extends BaseActivity {

    private GoogleMap googleMap;
    FloatingActionButton fab;
    TextView txtAddress;


    double lat = 0;
    double log = 0;
    String name ;
    String address ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        readView();
        functionView();
    }

    @Override
    public void readView() {
        super.readView();
        fab = findViewById(R.id.fab);
        txtAddress = findViewById(R.id.txtAddress);
    }


    @Override
    public void functionView() {
        super.functionView();
        lat = getIntent().getDoubleExtra("lat", 0);
        log = getIntent().getDoubleExtra("log", 0);
        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("address");

        txtAddress.setText(address);
        fab.setImageResource(R.drawable.ic_help_outline_white_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }


    private void initilizeMap() {
        if (googleMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        ActivityMap.this.googleMap = googleMap;
                        LatLng hyderadbad = new LatLng(lat, log);
                        googleMap.addMarker(new MarkerOptions().position(hyderadbad).title(name));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hyderadbad, 15));
                        googleMap.getUiSettings().setMapToolbarEnabled(true);
                        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                        googleMap.getUiSettings().setZoomControlsEnabled(true);
                        googleMap.getUiSettings().setZoomGesturesEnabled(true);
                        direction();
                    }
                });
            }

        }
    }

    Location myLocation;

    private void direction() {
        if (googleMap != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    fab.setImageResource(R.drawable.ic_help_outline_white_24dp);
                    return;
                }
            }
            if (SmartLocation.with(ActivityMap.this).location().state().isGpsAvailable()) {
                fab.setImageResource(R.drawable.ic_directions_white_24dp);
            }
            googleMap.setMyLocationEnabled(true);
        }
    }

    private void init() {
        PermissionHelper.requestLocation(new PermissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                if (SmartLocation.with(ActivityMap.this).location().state().locationServicesEnabled()) {
                    SmartLocation.with(ActivityMap.this).location().state().isAnyProviderAvailable();
                }
                if (SmartLocation.with(ActivityMap.this).location().state().isGpsAvailable()) {
                    SmartLocation.with(ActivityMap.this)
                                 .location()
                                 .oneFix()
                                 .start(new OnLocationUpdatedListener() {
                                     @Override
                                     public void onLocationUpdated(Location location) {
                                         direction();
                                         myLocation = location;
                                         G.log("Location", "location :" + location.getLatitude() + " - " + location.getLongitude());
                                         List<LatLng> list = new ArrayList<>();
                                         list.add(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
                                         list.add(new LatLng(lat, log));
                                         MapDirction mapDirction = new MapDirction();
                                         mapDirction.getDirection(list, new MapDirction.MapDirctionCallBack() {
                                             @Override
                                             public void onResponce(PolylineOptions polylineOptions) {
                                                 googleMap.addPolyline(polylineOptions);
                                             }
                                         });
                                     }
                                 });
                } else {
                    buildAlertMessageNoGps();
                }
            }
        });
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Utils.getString(R.string.gps_off_message))
               .setCancelable(false)
               .setPositiveButton(Utils.getString(R.string.yes), new DialogInterface.OnClickListener() {
                   public void onClick(final DialogInterface dialog, final int id) {
                       //  startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                       enableLocationSettings();
                   }
               })
               .setNegativeButton(Utils.getString(R.string.no), new DialogInterface.OnClickListener() {
                   public void onClick(final DialogInterface dialog, final int id) {
                       dialog.cancel();
                   }
               });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    protected void enableLocationSettings() {
        LocationRequest locationRequest = LocationRequest.create()
                                                         .setInterval(1000)
                                                         .setFastestInterval(1000)
                                                         .setNumUpdates(1)
                                                         .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        LocationServices
                .getSettingsClient(this)
                .checkLocationSettings(builder.build())
                .addOnSuccessListener(this, (LocationSettingsResponse response) -> {
                    // startUpdatingLocation(...);
                })
                .addOnFailureListener(this, ex -> {
                    if (ex instanceof ResolvableApiException) {
                        // Location settings are NOT satisfied,  but this can be fixed  by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),  and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) ex;
                            resolvable.startResolutionForResult(ActivityMap.this, 100);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                init();
            } else {
                G.toast(Utils.getString(R.string.gps_is_off));
                //user clicked cancel: informUserImportanceOfLocationAndPresentRequestAgain();
            }
        }

    }


}
