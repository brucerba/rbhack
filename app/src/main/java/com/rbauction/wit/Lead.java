package com.rbauction.wit;
public class Lead {

    public int id;
    public int postingId;
    public String url;
    public String price;
    public String equipment;
    public String phoneNum;
    public String saleDate;
    public String postingLocationName;
    public String location;
    public double geographyLatitude;
    public double geographyLongitude;
    public int geonameId;
    public String geographyName;
    public String geographyDivision;
    public String geographyCountryName;


    public Lead(){

    }

    public Lead(
            int id,
            String phoneNum,
            double geographyLatitude,
            double geographyLongitude,
            String equipment,
            String price
    ){
        this.id = id;
        this.geographyLatitude = geographyLatitude;
        this.geographyLongitude = geographyLongitude;
        this.phoneNum = phoneNum;
        this.equipment = equipment;
        this.price = price;
    }



}