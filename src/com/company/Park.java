package com.company;

public class Park {
    private int parkId;
    private String parkName;
    private String parkDescription;
    private String parkAddress;
    private String region;
    private String parkTelephon;
    private String parkUrl;
    private String text;
    private String parkEquip;
    private String dpName;
    private int primaryKey;

    public Park(){
    }

    public Park(int parkId, String parkName, String parkDescription, String parkAddress, String region, String parkTelephon, String parkUrl, String text, String parkEquip, String dpName){
        this.parkId = parkId;
        this.parkName = parkName;
        this.parkDescription = parkDescription;
        this.parkAddress = parkAddress;
        this.region = region;
        this.parkTelephon = parkTelephon;
        this.parkUrl = parkUrl;
        this.text = text;
        this.parkEquip = parkEquip;
        this.dpName = dpName;
    }

    public int primaryKey(){
        return this.primaryKey;
    }
    public void primaryKey(int primaryKey){
        this.primaryKey = primaryKey;
    }
}
