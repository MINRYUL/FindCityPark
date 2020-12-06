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
        String password = "sanghee0610";

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
            st.executeUpdate("create table Park(parkId int not null, parkName text not null, parkDescription text not null, parkAddress text, region varchar(100) not null, parkTelephone varchar(50), parkUrl text, dpName varchar(100) not null, PRIMARY KEY(parkId));");
            st.executeUpdate("create table Department(dpName varchar(100) not null,dpAddress text, dpTelephone varchar(50), PRIMARY KEY(dpName));");
            st.executeUpdate("create table Review(reviewId int not null, writer varchar(20), star int not null, reviewContent varchar(50), parkId int not null, PRIMARY KEY(reviewId));");
            st.executeUpdate("create table AirCondition(region varchar(100) not null, airStatus varchar(10), ozone double precision, carbon double precision, fineDust int, ultraFineDust int, PRIMARY KEY(region));");

            // 디비에 삽입
            ArrayList<Park> parkList = GetSeoulParkData.getParkArrayList();
            System.out.println(parkList.get(12).getParkId());
            System.out.println(parkList.get(12).getParkName());
            System.out.println(parkList.get(12).getParkDescription());
            System.out.println(parkList.get(12).getParkAddress());
            System.out.println(parkList.get(12).getRegion());
            System.out.println(parkList.get(12).getParkTelephon());
            System.out.println(parkList.get(12).getParkUrl());
            System.out.println(parkList.get(12).getDpName());

            ArrayList<AirCondition> airConditionArrayList = GetAirCondition.getAirConditionArrayList();
            for(int i = 0; i < 25; i++){
                if (airConditionArrayList.get(i).getRegion() == "111123") continue;
                st.executeUpdate("insert into AirCondition values (" + "'" + airConditionArrayList.get(i).getRegion() + "', " +
                        "'" + airConditionArrayList.get(i).getAirStatus() + "', " + "'" + airConditionArrayList.get(i).getOZone() + "', " +
                        "'" + airConditionArrayList.get(i).getCarbon() +  "', " + "'" + airConditionArrayList.get(i).getFineDust() + "', " +
                        "'" + airConditionArrayList.get(i).getUltraFineDust() + "');");
            }

//            ArrayList<Park> parkList = GetSeoulParkData.getParkArrayList();
            for(int i = 0; i < parkList.size(); i++){
                String text = parkList.get(i).getParkDescription().replace("'", "*");
                st.executeUpdate("insert into Park values (" + "'" + parkList.get(i).getParkId() + "', " + "'" + parkList.get(i).getParkName() + "', " +
                        "'" + text + "', " + "'" + parkList.get(i).getParkAddress() + "', " +
                        "'" + parkList.get(i).getRegion() + "', " + "'" + parkList.get(i).getParkTelephon() + "', " +
                        "'" + parkList.get(i).getParkUrl() + "', " + "'" + parkList.get(i).getDpName() + "');");
            }

            //Review 삽입
            st.executeUpdate("insert into Review values('1', '김말국', 5, '공원에 공기가 좋네요', 5);");
            st.executeUpdate("insert into Review values('2', '미나', 4, '비둘기 똥이 많음', 1);");
            st.executeUpdate("insert into Review values('3', '요미', 3, '흡연구역이 없음', 3);");
            st.executeUpdate("insert into Review values('4', '제시', 2, '편의점이 있어서 좋음', 5);");
            st.executeUpdate("insert into Review values('5', '사라키', 1, '조용해서 좋아여', 4);");

            //department 삽입
            st.executeUpdate("insert into Department values('도봉구청 공원녹지과', '서울 도봉구 마들로 656', '02-2091-2120');");
            st.executeUpdate("insert into Department values('중랑구청 공원녹지과', '서울 중랑구 봉화산로 179 ', '02-2094-0114');");
            st.executeUpdate("insert into Department values('동작구청 공원녹지과', '서울 동작구 장승배기로 161', '02-820-1114');");
            st.executeUpdate("insert into Department values('금천구 공원녹지과', '서울 금천구 시흥대로73길 70', '02-2627-2114');");
            st.executeUpdate("insert into Department values('관악구청 공원녹지과', '서울 관악구 관악로 145', '02-879-5000');");
            st.executeUpdate("insert into Department values('서대문구청 푸른도시과', '서울 서대문구 연희로 248', '02-330-1301');");
            st.executeUpdate("insert into Department values('성동구청 공원녹지과', '서울 성동구 고산자로 270', '02-2286-5114');");

            String userRegion; // 거주지역(구) 입력받기
            String parkId;     // parkId 입력받기
            String dpName;     // 리뷰볼 때 join하기 위해 사용됨

            System.out.println("지역(구)을 입력하세요 : ");
            userRegion = scan.nextLine();

            rs = st.executeQuery("select parkId, dpName\n" +
                    "from Park \n" +
                    "where region = " + userRegion + ";");

            parkId = rs.getString("parkId");
            dpName = rs.getString("dpName");
            // 위 쿼리를 통해서 parkName 들을 보여주기

            // parkName 하나 입력받기

            /* 아래부터는 입력받은 것을 기반으로 본격 서비스 시작 */

            // 1. 공원 소개
            rs = st.executeQuery("select parkDescription \n" +
                    "from Park \n" +
                    "where parkId = " + parkId + " and region = " + userRegion + ";");

            // parkDescription(공원소개) 출력하기

            // 2. 위치보기
            rs = st.executeQuery("select parkAddress \n" +
                    "from Park \n" +
                    "where parkId = " + parkId + " and region = " + userRegion + ";");

            // parkAddress출력

            //3. 시설 소개
            rs = st.executeQuery("select parkEquip\n" +
                    "from Park \n" +
                    "where parkId = " + parkId + " and region = " + userRegion + ";");

            // parkEquip 출력

            // 4. 현재 대기 환경
            rs = st.executeQuery("select airStatus, ozone, carbon, fineDust, ultraFineDust \n" +
                    "from Park, AirCondition \n" +
                    "where Park.region = AirCondition.region and " + " region = " + userRegion + ";");

            // 대기환경 다 출력

            // 5. 리뷰 보기
            rs = st.executeQuery("select parkName, writer, star, reviewContent \n" +
                    "from Park, Review\n" +
                    "where Park.parkId = Review.parkId and Park.parkId = " + parkId + " and region = " + userRegion +";");

            // 리뷰 다 출력하기

            // 6. 관리부서 보기
            rs = st.executeQuery("select dpName, dpAddress, dpTelephone\n" +
                    "from Park, Department\n" +
                    "where Park.dpName and Department.dpName and Park.parkId = " + parkId + "Park.dpName = " + dpName + ";");

            // 관리부서 출력
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
