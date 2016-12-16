package com.rbauction.wit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class LeadsActivity extends AppCompatActivity{

    List<AuctionInfo> auctions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        setContentView(R.layout.activity_leads);


        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        auctions.add(new AuctionInfo("Orlando (2017 Feb)", "2017/1/1", "101", 28.538340, -81.379240, 4167147));
        auctions.add(new AuctionInfo("Kansas City (2017 Mar)", "2017/1/2", "102", 39.099730, -94.578570, 4393217));
        auctions.add(new AuctionInfo("Salt Lake City (2017 Apr)", "2017/1/3", "104", 40.760780,	-111.891050, 5780993));

        ContactAdapter ca = new ContactAdapter(auctions);
        recList.setAdapter(ca);
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

}
