package com.rbauction.wit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyOnClickListener implements View.OnClickListener {

   protected AuctionInfo auctinfo;
   public MyOnClickListener(AuctionInfo auct_info){
      this.auctinfo = auct_info;
   }

   @Override
   public void onClick(final View view) {

      Intent intent = new Intent(view.getContext(), MapsActivity.class);
      intent.putExtra("geoname_id", auctinfo.geoname_id);
      intent.putExtra("Auction_Number", auctinfo.Auction_Number);
      intent.putExtra("Auction_Name", auctinfo.Auction_Name);
      intent.putExtra("Auction_Date", auctinfo.Auction_Date);
      intent.putExtra("Lat", auctinfo.lat);
      intent.putExtra("Lon", auctinfo.lon);

      view.getContext().startActivity(intent);

   }


}
