package com.rbauction.wit;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    AuctionInfo auctinfo;
    ArrayList<Lead> leads = new ArrayList<>();

    String readFile(String name) {
        final StringBuilder sb = new StringBuilder();
        String strLine = "";
        try {

            final BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(name), "UTF-8"));
            while ((strLine = reader.readLine()) != null) {
                sb.append(strLine);
            }
        } catch (Exception ex) {
            Log.d("Error reading file", ex.getMessage());
        }
        return sb.toString();
    }


    public double randomize2(double lat) {
        Random r = new Random();
        double rand = r.nextDouble();
        rand = rand * 0.0001;
        return lat += rand;
    }

    static int sign = 1;

    public double randomize(double lat) {
        sign = sign * -1;
        return lat += sign * new Random().nextInt(10) * 0.1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent thisIntent = getIntent();

        auctinfo = new AuctionInfo(
                thisIntent.getStringExtra("Auction_Name"),
                thisIntent.getStringExtra("Auction_Date"),
                thisIntent.getStringExtra("Auction_Number"),
                thisIntent.getDoubleExtra("Lat", 0),
                thisIntent.getDoubleExtra("Lon", 0),
                thisIntent.getIntExtra("geoname_id", 0)
        );


    }

    boolean hasLocationPermission() {
        return PackageManager.PERMISSION_GRANTED == checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE);
    }


    void call(Lead tag) {
        String uri = "tel:" + ((Lead) tag).phoneNum.trim();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        if (hasLocationPermission())
            startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(this));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Object tag = marker.getTag();
                if (tag instanceof Lead) {

                    if (hasLocationPermission()) {
                        call((Lead) tag);
                    } else {
                        currentLead = (Lead) tag;
                        requestLocationPermission();
                    }
                }
            }
        });

        // Zoom in to Auction
        LatLng auctionLatLon = new LatLng(auctinfo.lat, auctinfo.lon);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(auctionLatLon).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Marker auctionMarker = mMap.addMarker(new MarkerOptions().position(auctionLatLon).title(auctinfo.Auction_Name));
        auctionMarker.setTag(auctinfo);
        loadLeads();
        //Log.d("LatLon: " , String.valueOf(auctinfo.lat) + " " +  String.valueOf(auctinfo.lon));
        // Post leads.
        for (Lead l : leads) {
            LatLng leadLatLon = new LatLng(randomize(l.geographyLatitude), randomize(l.geographyLongitude));
            MarkerOptions moptions = new MarkerOptions();
            moptions.position(leadLatLon);
            moptions.title(l.phoneNum);
            Marker marker = mMap.addMarker(moptions);
            marker.setTag(l);
            // Log.d("LatLon: " , String.valueOf(randomize(l.geographyLatitude)) + " " + String.valueOf(randomize(l.geographyLongitude)));

        }

    }

    void loadLeads() {
        try {
            String jsonText = null;
            jsonText = readFile("leads.Json");

            if (jsonText != null) {
                JSONArray jarray = new JSONArray(jsonText);

                JSONObject jary = null;
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject l = jarray.getJSONObject(i);
                    if (String.valueOf(auctinfo.geoname_id).equals(l.getString("geographyId"))) {
                        jary = jarray.getJSONObject(i);
                        break;
                    }
                }


                JSONArray jleads = jary.getJSONArray("customerLeads");
                for (int i = 0; i < jleads.length(); i++) {
                    JSONObject jlead = jleads.getJSONObject(i);
                    leads.add(
                            new Lead(
                                    jlead.getInt("id"),
                                    jlead.getString("phoneNum"),
                                    Double.parseDouble(jlead.getString("geographyLatitude")),
                                    Double.parseDouble(jlead.getString("geographyLongitude")),
                                    jlead.getString("equipment"),
                                    jlead.getString("price")
                            )
                    );
                }
                Log.d(TAG, leads.size() + " leads loaded");

            }
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }

    public static final int RC_HANDLE_LOCATION_PERM = 10000;

    @TargetApi(23)

    private void requestLocationPermission() {
        final String[] permissions = new String[]{Manifest.permission.CALL_PHONE};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
            this.requestPermissions(permissions, RC_HANDLE_LOCATION_PERM);
            return;
        }


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions(permissions,
                        RC_HANDLE_LOCATION_PERM);
            }
        };

        Snackbar.make(findViewById(R.id.jsonView), "okay we need mic",
                Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok, listener)
                .show();
    }

    Lead currentLead;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_LOCATION_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Location permission granted - now do location");
            if (currentLead != null)
                call(currentLead);
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission error")
                .setMessage("User didn't grant recording permit")
                .setPositiveButton(android.R.string.ok, listener)
                .show();
    }

    public static final int RC_BARCODE_CAPTURE = 10000;


}
