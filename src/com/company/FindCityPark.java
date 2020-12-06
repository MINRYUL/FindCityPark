package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FindCityPark {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:postgresql://localhost/";
        String user = "postgres";
        String password = "관리자비밀번호";

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

            System.out.println("Connecting succeed");

            st.executeUpdate("drop Table Park cascade");
            st.executeUpdate("drop Table Department cascade");
            st.executeUpdate("drop Table Review cascade");
            st.executeUpdate("drop Table AirCondition cascade");

            // 3개 테이블 생성: Create table문 이용
            st.executeUpdate("create table Park(parkId int not null, parkName text not null, parkDescription text not null, parkAddress text, region varchar(100) not null, parkTelephone varchar(50), parkUrl text, dpName varchar(100) not null, PRIMARY KEY(parkId));");
            st.executeUpdate("create table Department(dpName varchar(100) not null,dpAddress text, dpTelephone varchar(50), PRIMARY KEY(dpName));");
            st.executeUpdate("create table Review(reviewId int not null, writer varchar(20), star int not null, reviewContent varchar(50), parkId int not null, PRIMARY KEY(reviewId));");
            st.executeUpdate("create table AirCondition(region varchar(100) not null, airStatus varchar(10), ozone double precision, carbon double precision, fineDust int, ultraFineDust int, PRIMARY KEY(region));");

            // 디비에 삽입
            ArrayList<Park> parkList = GetSeoulParkData.getParkArrayList();
            ArrayList<AirCondition> airConditionArrayList = GetAirCondition.getAirConditionArrayList();

            for(int i = 0; i < 25; i++){
                if (airConditionArrayList.get(i).getRegion() == "111123") continue;
                st.executeUpdate("insert into AirCondition values (" + "'" + airConditionArrayList.get(i).getRegion() + "', " +
                        "'" + airConditionArrayList.get(i).getAirStatus() + "', " + "'" + airConditionArrayList.get(i).getOZone() + "', " +
                        "'" + airConditionArrayList.get(i).getCarbon() +  "', " + "'" + airConditionArrayList.get(i).getFineDust() + "', " +
                        "'" + airConditionArrayList.get(i).getUltraFineDust() + "');");
            }

            for(int i = 0; i < parkList.size(); i++){
                String descText = parkList.get(i).getParkDescription().replace("'", "*");
                st.executeUpdate("insert into Park values (" + "'" + parkList.get(i).getParkId() + "', " + "'" + parkList.get(i).getParkName() + "', " +
                        "'" + descText + "', " + "'" + parkList.get(i).getParkAddress() + "', " +
                        "'" + parkList.get(i).getRegion() + "', " + "'" + parkList.get(i).getParkTelephon() + "', " +
                        "'" + parkList.get(i).getParkUrl() + "', '" + parkList.get(i).getDpName() + "');");
            }

            //Review 삽입
            st.executeUpdate("insert into Review values('1', '김말국', 5, '공원에 공기가 좋네요', 5);");
            st.executeUpdate("insert into Review values('2', '미나', 4, '비둘기 똥이 많음', 1);");
            st.executeUpdate("insert into Review values('3', '요미', 3, '흡연구역이 없음', 3);");
            st.executeUpdate("insert into Review values('4', '제시', 2, '편의점이 있어서 좋음', 70);");
            st.executeUpdate("insert into Review values('5', '사라키', 1, '조용해서 좋아여', 70);");

            //department 삽입
            st.executeUpdate("insert into Department values('도봉구청 공원녹지과', '서울 도봉구 마들로 656', '02-2091-2120');");
            st.executeUpdate("insert into Department values('중랑구청 공원녹지과', '서울 중랑구 봉화산로 179 ', '02-2094-0114');");
            st.executeUpdate("insert into Department values('동작구청 공원녹지과', '서울 동작구 장승배기로 161', '02-820-1114');");
            st.executeUpdate("insert into Department values('금천구 공원녹지과', '서울 금천구 시흥대로73길 70', '02-2627-2114');");
            st.executeUpdate("insert into Department values('관악구청 공원녹지과', '서울 관악구 관악로 145', '02-879-5000');");
            st.executeUpdate("insert into Department values('서대문구청 푸른도시과', '서울 서대문구 연희로 248', '02-330-1301');");
            st.executeUpdate("insert into Department values('성동구청 공원녹지과', '서울 성동구 고산자로 270', '02-2286-5114');");

            String userRegion = null; // 거주지역(구) 입력받기
            String parkId = null;     // parkId 입력받기
            String dpName = null;     // 리뷰볼 때 join하기 위해 사용됨
            String parkName = null;

            System.out.print("지역구를 입력하세요 : ");
            userRegion = scan.nextLine();

            rs = st.executeQuery("select parkId, dpName " +
                    "from Park " +
                    "where region = '" + userRegion + "';");

            while(rs.next()) {
                parkId = rs.getString("parkId");
                dpName = rs.getString("dpName");
            }

            // 위 쿼리를 통해서 parkName 들을 보여주기

            // parkName 하나 입력받기
            System.out.print("공원명을 입력하세요 : ");
            parkName = scan.nextLine();

            /* 아래부터는 입력받은 것을 기반으로 본격 서비스 시작 */

            // 1. 공원 소개
            rs = st.executeQuery("select parkDescription \n" +
                    "from Park \n" +
                    "where parkId = " + parkId + " and region = '" + userRegion + "';");

            // parkDescription(공원소개) 출력하기

            // 2. 위치보기
            rs = st.executeQuery("select parkAddress \n" +
                    "from Park \n" +
                    "where parkId = " + parkId + " and region = '" + userRegion + "';");

            // parkAddress출력

            // 4. 현재 대기 환경
            rs = st.executeQuery("select airStatus, ozone, carbon, fineDust, ultraFineDust \n" +
                    "from Park, AirCondition \n" +
                    "where Park.region = AirCondition.region and " + " Park.region = '" + userRegion + "';");

            // 대기환경 다 출력

            // 5. 리뷰 보기
            rs = st.executeQuery("select parkName, writer, star, reviewContent \n" +
                    "from Park, Review\n" +
                    "where Park.parkId = Review.parkId and Park.parkName = '" + parkName + "' and region = '" + userRegion +"';");

            // 리뷰 다 출력하기
            System.out.printf("%-20s %-10s %-5s %-20s\n", "parkName", "writer", "star", "reviewContent");
            System.out.println("---------------------------------------------------------");
            while(rs.next()) {
                String parkName1 = rs.getString("parkName");
                String writer = rs.getString("writer");
                int star = rs.getInt("star");
                String reviewContent = rs.getString("reviewContent");

                System.out.printf("%-20s %-10s %-5d %-20s\n", parkName1, writer, star, reviewContent);
            }

            // 6. 관리부서 보기
            rs = st.executeQuery("select parkName, Park.dpName, dpAddress, dpTelephone\n" +
                    "from Park, Department\n" +
                    "where Park.dpName = Department.dpName and Park.parkId = " + parkId + " and Park.dpName = '" + dpName + "';");

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
