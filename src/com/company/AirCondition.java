package com.company;

public class AirCondition {
    private String region;
    private String airStatus;
    private String oZone;
    private String carbon;
    private String fineDust;
    private String ultraFineDust;

    public AirCondition(){};
    public AirCondition(String region, String airStatus, String oZone, String carbon, String fineDust, String ultraFineDust){
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
    public String getCarbon(){
        return this.carbon;
    }
    public String getFineDust(){
        return this.fineDust;
    }
    public String getUltraFineDust(){
        return this.ultraFineDust;
    }
}
