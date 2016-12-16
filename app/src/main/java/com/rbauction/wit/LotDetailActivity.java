package com.rbauction.wit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brucexia on 2016-12-15.
 */

public class LotDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_lot_detail, null);
        setContentView(contentView);
    }

    class ViewHolder {
        @BindView(R.id.lotImage)
        ImageView lotImageView;
        @BindView(R.id.lotNumber)
        TextView lotNumberView;
        @BindView(R.id.lotDescription)
        TextView lotDescView;
        @BindView(R.id.ringNumber)
        TextView ringView;
        @BindView(R.id.startTime)
        TextView startTimeView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void update(LotDetail lotDetail) {

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
