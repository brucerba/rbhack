package com.rbauction.wit;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by brucexia on 2016-12-15.
 */
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View myContentsView;
    Activity activity;
    @BindView(R.id.nameView)
    TextView nameView;
    @BindView(R.id.phoneView)
    TextView phoneView;
    @BindView(R.id.addressView)
    TextView addressView;

    public MyInfoWindowAdapter(Activity activity) {
        this.activity = activity;
        myContentsView = activity.getLayoutInflater().inflate(R.layout.layout_info, null);
        ButterKnife.bind(this, myContentsView);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        Object object = marker.getTag();
        if (object instanceof Lead) {
            Lead auctionInfo = (Lead) marker.getTag();

        nameView.setText(auctionInfo.equipment);
            phoneView.setText(auctionInfo.phoneNum);
            addressView.setText(auctionInfo.postingLocationName);
        } else if (object instanceof AuctionInfo) {
            AuctionInfo auctionInfo = ((AuctionInfo)object);
//            nameView.setText(((AuctionInfo) object).geoname_id);
            phoneView.setText(((AuctionInfo) object).Auction_Name);
        }
        return myContentsView;
    }

}
