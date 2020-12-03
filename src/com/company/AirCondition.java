package com.company;

public class AirCondition {
    private String region;
    private String airStatus;
    private String oZone;
    private double carbon;
    private int fineDust;
    private int ultraFineDust;

    public AirCondition(){};
    public AirCondition(String region, String airStatus, String oZone, double carbon, int fineDust, int ultraFineDust){
        this.region = region;
        this.airStatus = airStatus;
        this.oZone = oZone;
        this.carbon = carbon;
        this.fineDust = fineDust;
        this.ultraFineDust = ultraFineDust;
    }

    public String getRegion(){
        return this.region;
    }
    public String getAirStatus(){
        return this.airStatus;
    }
    public String getOZone(){
        return this.oZone;
    }
    public double getCarbon(){
        return this.carbon;
    }
    public int getFineDust(){
        return this.fineDust;
    }
    public int getUltraFineDust(){
        return this.ultraFineDust;
    }
}
