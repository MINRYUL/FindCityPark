package com.company;

public class Park {
    private int parkId;
    private String parkName;
    private String parkDescription;
    private String parkAddress;
    private String region;
    private String parkTelephon;
    private String parkUrl;
    private int primaryKey;

    public Park(){
    }

    public Park(int parkId, String parkName, String parkDescription, String parkAddress, String region, String parkTelephon, String parkUrl){
        this.parkId = parkId;
        this.parkName = parkName;
        this.parkDescription = parkDescription;
        this.parkAddress = parkAddress;
        this.region = region;
        this.parkTelephon = parkTelephon;
        this.parkUrl = parkUrl;
    }
    public int primaryKey(){
        return this.primaryKey;
    }
    public void primaryKey(int primaryKey){
        this.primaryKey = primaryKey;
    }
    public int getParkId(){
        return this.parkId;
    }
    public String getParkName(){
        return this.parkName;
    }
    public String getParkDescription(){
        return this.parkDescription;
    }
    public String getParkAddress(){
        return this.parkAddress;
    }
    public String getRegion(){
        return this.region;
    }
    public String getParkTelephon(){
        return this.parkTelephon;
    }
    public String getParkUrl(){
        return this.parkUrl;
    }

}
