package com.rbauction.wit;
public class AuctionInfo {
    protected String Auction_Name;
    protected String Auction_Date;
    protected String Auction_Number;
    protected double lat;
    protected double lon;
    protected int geoname_id;

    public AuctionInfo(
            String Auction_Name,
            String Auction_Date,
            String Auction_Number,
            double lat,
            double lon,
            int geoname_id
            ){

        this.Auction_Name = Auction_Name;
        this.Auction_Date = Auction_Date;
        this.Auction_Number = Auction_Number;
        this.lat = lat;
        this.lon = lon;
        this.geoname_id = geoname_id;
    }

}