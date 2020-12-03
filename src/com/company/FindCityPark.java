package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FindCityPark {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        //알아서 바꾸셈
        String url = "jdbc:postgresql://localhost/";
        String user = "postgres";
        String password = "1221zxc151";

        GetSeoulParkData.getParkData();
        GetAirCondition.getAirCondition();

        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("SQL Programming Test");

            System.out.println("Connecting PostgreSQL database");
            // JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();

            st.executeUpdate("drop Table Park");
            st.executeUpdate("drop Table Department");
            st.executeUpdate("drop Table Review");
            st.executeUpdate("drop Table AirCondition");

            // System.out.println("Creating Park, Department, Review relations");
            // 3개 테이블 생성: Create table문 이용
            st.executeUpdate("create table Park(parkId int not null,parkName text not null, parkDescription text not null, parkAddress text, region varchar(100) not null, parkTelephone varchar(50), parkUrl text, dpName varchar(100) not null, PRIMARY KEY(parkId));");
            st.executeUpdate("create table Department(dpName varchar(100) not null,dpAddress text, varchar(50), PRIMARY KEY(dpName));");
            st.executeUpdate("create table Review(reviewId int not null, writer varchar(20), star int not null, reviewContent varchar(50), parkId int not null, PRIMARY KEY(reviewId));");
            st.executeUpdate("create table Review(region varchar(100) not null, airStatus varchar(10), ozone double precision, carbon double precision, fineDust int, ultraFineDust int, PRIMARY KEY(region));");

            ArrayList<Park> parkList = GetSeoulParkData.getParkArrayList();
            for(int i = 0; i < parkList.size(); i++){
                st.executeUpdate("insert into Park values (" + parkList.get(i).getParkId() + ", " + parkList.get(i).getParkName() +
                        ", " + parkList.get(i).getParkDescription() + ", " + parkList.get(i).getParkAddress() + ", " + parkList.get(i).getRegion() +
                        ", " + parkList.get(i).getParkTelephon() + ", " + parkList.get(i).getParkUrl() + ", " + parkList.get(i).getDpName() + ");");
            }
            ArrayList<AirCondition> airConditionArrayList = GetAirCondition.getAirConditionArrayList();
            for(int i = 0; i < airConditionArrayList.size(); i++){
                st.executeUpdate("insert into AirCondition values (" + airConditionArrayList.get(i).getRegion() + ", " + airConditionArrayList.get(i).getAirStatus() +
                        ", " + airConditionArrayList.get(i).getOZone() + ", " + airConditionArrayList.get(i).getCarbon() + ", " + airConditionArrayList.get(i).getFineDust() +
                        ", " + airConditionArrayList.get(i).getUltraFineDust() + ");");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
            }

        }
    }
}
