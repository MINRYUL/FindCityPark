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
        String password = "sanghee0610";

        GetSeoulParkData.getParkData();
        GetAirCondition.getAirCondition();

        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("도심 속 공원 찾기 - 황디비");

//            System.out.println("Connecting PostgreSQL database");
            // JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();

            st.executeUpdate("drop Table Park cascade");
            st.executeUpdate("drop Table Review cascade");
            st.executeUpdate("drop Table AirCondition cascade");

            // 3개 테이블 생성: Create table문 이용
            st.executeUpdate("create table Park(parkId int not null, parkName text not null, parkDescription text not null, parkAddress text, region varchar(100) not null, parkTelephone varchar(50), parkUrl text, dpName varchar(100) not null, PRIMARY KEY(parkId));");
            st.executeUpdate("create table Review(reviewId int not null, writer varchar(20), star int not null, reviewContent varchar(50), parkId int not null, PRIMARY KEY(reviewId));");
            st.executeUpdate("create table AirCondition(region varchar(100) not null, airStatus varchar(10), ozone double precision, carbon double precision, fineDust int, ultraFineDust int, PRIMARY KEY(region));");

            // 디비에 삽입
            ArrayList<Park> parkList = GetSeoulParkData.getParkArrayList();
            ArrayList<AirCondition> airConditionArrayList = GetAirCondition.getAirConditionArrayList();

            for(int i = 0; i < 25; i++){
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

//            for (int i = 0; i < parkList.size(); i++) {
//                st.executeUpdate("insert into Review values('"+ i +"', '김말국', 5, '공원에 공기가 좋네요', 1);");
//                i++;
//                st.executeUpdate("insert into Review values('"+ i +"', '미나', 4, '비둘기 똥이 많음', 3);");
//                i++;
//                st.executeUpdate("insert into Review values('"+ i +"', '요미', 3, '흡연구역이 없음', 4);");
//                i++;
//                st.executeUpdate("insert into Review values('"+ i +"', '제시', 2, '편의점이 있어서 좋음', 5);");
//                i++;
//                st.executeUpdate("insert into Review values('"+ i +"', '사라키', 1, '조용해서 좋아여', 6);");
//                i++;
//                st.executeUpdate("insert into Review values('"+ i +"', '뀨뀨', 5, '공원에 공기가 좋네요', 12);");
//                i++;
//                st.executeUpdate("insert into Review values('"+ i +"', '꾸잉', 4, '시설이 되게 잘 되어있어요', 13);");
//                i++;
//                st.executeUpdate("insert into Review values('"+ i +"', '케케', 3, '걍 쏘쏘', 16);");
//                i++;
//                st.executeUpdate("insert into Review values('"+ i +"', '뀨잉', 2, '너무 아무것도 업슴', 17);");
//                i++;
//                st.executeUpdate("insert into Review values('"+ i +"', '기요미', 5, '편안하고 좋네용!', 18);");
//                i++
//            }
//
            // 그냥 직접 넣었슴다
            st.executeUpdate("insert into Review values('1', '김말국', 5, '공원에 공기가 좋네요', 1);");
            st.executeUpdate("insert into Review values('2', '미나', 4, '비둘기 똥이 많음', 3);");
            st.executeUpdate("insert into Review values('3', '요미', 3, '흡연구역이 없음', 4);");
            st.executeUpdate("insert into Review values('4', '제시', 2, '편의점이 있어서 좋음', 5);");
            st.executeUpdate("insert into Review values('5', '사라키', 1, '조용해서 좋아여', 6);");
            st.executeUpdate("insert into Review values('6', '뀨뀨', 5, '공원에 공기가 좋네요', 12);");
            st.executeUpdate("insert into Review values('7', '꾸잉', 4, '시설이 되게 잘 되어있어요', 13);");
            st.executeUpdate("insert into Review values('8', '케케', 3, '걍 쏘쏘', 16);");
            st.executeUpdate("insert into Review values('9', '뀨잉', 2, '너무 아무것도 업슴', 17);");
            st.executeUpdate("insert into Review values('10', '기요미', 5, '편안하고 좋네용!', 18);");

            st.executeUpdate("insert into Review values('11', '김말국', 5, '공원에 공기가 좋네요', 19);");
            st.executeUpdate("insert into Review values('12', '미나', 4, '비둘기 똥이 많음', 20);");
            st.executeUpdate("insert into Review values('13', '요미', 3, '흡연구역이 없음', 21);");
            st.executeUpdate("insert into Review values('14', '제시', 2, '편의점이 있어서 좋음', 22);");
            st.executeUpdate("insert into Review values('15', '사라키', 1, '조용해서 좋아여', 23);");
            st.executeUpdate("insert into Review values('16', '뀨뀨', 5, '공원에 공기가 좋네요', 24);");
            st.executeUpdate("insert into Review values('17', '꾸잉', 4, '시설이 되게 잘 되어있어요', 25);");
            st.executeUpdate("insert into Review values('18', '케케', 3, '걍 쏘쏘', 26);");
            st.executeUpdate("insert into Review values('19', '뀨잉', 2, '너무 아무것도 업슴', 27);");
            st.executeUpdate("insert into Review values('20', '기요미', 5, '편안하고 좋네용!', 29);");

            st.executeUpdate("insert into Review values('21', '김말국', 5, '공원에 공기가 좋네요', 30);");
            st.executeUpdate("insert into Review values('22', '미나', 4, '비둘기 똥이 많음', 31);");
            st.executeUpdate("insert into Review values('23', '요미', 3, '흡연구역이 없음', 32);");
            st.executeUpdate("insert into Review values('24', '제시', 2, '편의점이 있어서 좋음', 36);");
            st.executeUpdate("insert into Review values('25', '사라키', 1, '조용해서 좋아여', 37);");
            st.executeUpdate("insert into Review values('26', '뀨뀨', 5, '공원에 공기가 좋네요', 39);");
            st.executeUpdate("insert into Review values('27', '꾸잉', 4, '시설이 되게 잘 되어있어요', 40);");
            st.executeUpdate("insert into Review values('28', '케케', 3, '걍 쏘쏘', 41);");
            st.executeUpdate("insert into Review values('29', '뀨잉', 2, '너무 아무것도 업슴', 45);");
            st.executeUpdate("insert into Review values('30', '기요미', 5, '편안하고 좋네용!', 46);");

            st.executeUpdate("insert into Review values('31', '김말국', 5, '공원에 공기가 좋네요', 47);");
            st.executeUpdate("insert into Review values('32', '미나', 4, '비둘기 똥이 많음', 48);");
            st.executeUpdate("insert into Review values('33', '요미', 3, '흡연구역이 없음', 51);");
            st.executeUpdate("insert into Review values('34', '제시', 2, '편의점이 있어서 좋음', 52);");
            st.executeUpdate("insert into Review values('35', '사라키', 1, '조용해서 좋아여', 53);");
            st.executeUpdate("insert into Review values('36', '뀨뀨', 5, '공원에 공기가 좋네요', 54);");
            st.executeUpdate("insert into Review values('37', '꾸잉', 4, '시설이 되게 잘 되어있어요', 55);");
            st.executeUpdate("insert into Review values('38', '케케', 3, '걍 쏘쏘', 56);");
            st.executeUpdate("insert into Review values('39', '뀨잉', 2, '너무 아무것도 업슴', 57);");
            st.executeUpdate("insert into Review values('40', '기요미', 5, '편안하고 좋네용!', 58);");

            st.executeUpdate("insert into Review values('41', '말국', 5, '공원 공기가 좋네요', 59);");
            st.executeUpdate("insert into Review values('42', '미나', 4, '비둘기 똥이 많음', 60);");
            st.executeUpdate("insert into Review values('43', '요미', 3, '흡연구역이 없어서 불편', 61);");
            st.executeUpdate("insert into Review values('44', '제시', 2, '편의점이 있어서 좋음', 65);");
            st.executeUpdate("insert into Review values('45', '니니', 1, '조용해서 좋아여', 66);");
            st.executeUpdate("insert into Review values('46', '뀨뀨', 5, '공원에 공기가 좋네요', 68);");
            st.executeUpdate("insert into Review values('47', '꾸잉', 4, '시설이 되게 잘 되어있어요', 69);");
            st.executeUpdate("insert into Review values('48', '쿠케', 3, '걍 쏘쏘', 70);");
            st.executeUpdate("insert into Review values('49', '뀨잉', 2, '너무 아무것도 업슴', 71);");
            st.executeUpdate("insert into Review values('50', '기요미', 5, '편안하고 좋네용!', 72);");

            st.executeUpdate("insert into Review values('51', '김말국', 5, '공원에 공기가 좋네요', 73);");
            st.executeUpdate("insert into Review values('52', '미나', 4, '비둘기 똥이 많음', 75);");
            st.executeUpdate("insert into Review values('53', '요미', 3, '흡연구역이 없음', 76);");
            st.executeUpdate("insert into Review values('54', '제시', 2, '편의점이 있어서 좋음', 80);");
            st.executeUpdate("insert into Review values('55', '사라키', 1, '조용해서 좋아여', 81);");
            st.executeUpdate("insert into Review values('56', '뀨뀨', 5, '공원에 공기가 좋네요', 82);");
            st.executeUpdate("insert into Review values('57', '꾸잉', 4, '시설이 되게 잘 되어있어요', 83);");
            st.executeUpdate("insert into Review values('58', '케케', 3, '걍 쏘쏘', 85);");
            st.executeUpdate("insert into Review values('59', '뀨잉', 2, '너무 아무것도 업슴', 86);");
            st.executeUpdate("insert into Review values('60', '기요미', 5, '편안하고 좋네용!', 87);");

            st.executeUpdate("insert into Review values('61', '김말국', 5, '공원에 공기가 좋네요', 89);");
            st.executeUpdate("insert into Review values('62', '미나', 4, '비둘기 똥이 많음', 90);");
            st.executeUpdate("insert into Review values('63', '요미', 3, '흡연구역이 없음', 91);");
            st.executeUpdate("insert into Review values('64', '제시', 2, '편의점이 있어서 좋음', 92);");
            st.executeUpdate("insert into Review values('65', '사라키', 1, '조용해서 좋아여', 93);");
            st.executeUpdate("insert into Review values('66', '뀨뀨', 5, '공원에 공기가 좋네요', 94);");
            st.executeUpdate("insert into Review values('67', '꾸잉', 4, '시설이 되게 잘 되어있어요', 95);");
            st.executeUpdate("insert into Review values('68', '케케', 3, '걍 쏘쏘', 96);");
            st.executeUpdate("insert into Review values('69', '뀨잉', 2, '너무 아무것도 업슴', 98);");
            st.executeUpdate("insert into Review values('70', '기요미', 5, '편안하고 좋네용!', 99);");

            st.executeUpdate("insert into Review values('71', '김말국', 5, '공원에 공기가 좋네요', 100);");
            st.executeUpdate("insert into Review values('72', '미나', 4, '비둘기 똥이 많음', 101);");
            st.executeUpdate("insert into Review values('73', '요미', 3, '흡연구역이 없음', 102);");
            st.executeUpdate("insert into Review values('74', '제시', 2, '편의점이 있어서 좋음', 103);");
            st.executeUpdate("insert into Review values('75', '사라키', 1, '조용해서 좋아여', 104);");
            st.executeUpdate("insert into Review values('76', '뀨뀨', 5, '공원에 공기가 좋네요', 106);");
            st.executeUpdate("insert into Review values('77', '꾸잉', 4, '시설이 되게 잘 되어있어요', 107);");
            st.executeUpdate("insert into Review values('78', '케케', 3, '걍 쏘쏘', 108);");
            st.executeUpdate("insert into Review values('79', '뀨잉', 2, '너무 아무것도 업슴', 109);");
            st.executeUpdate("insert into Review values('80', '기요미', 5, '편안하고 좋네용!', 110);");

            st.executeUpdate("insert into Review values('81', '김말국', 5, '공원에 공기가 좋네요', 111);");
            st.executeUpdate("insert into Review values('82', '미나', 4, '비둘기 똥이 많음', 112);");
            st.executeUpdate("insert into Review values('83', '요미', 3, '흡연구역이 없음', 114);");
            st.executeUpdate("insert into Review values('84', '제시', 2, '편의점이 있어서 좋음', 117);");
            st.executeUpdate("insert into Review values('85', '사라키', 1, '조용해서 좋아여', 118);");
            st.executeUpdate("insert into Review values('86', '뀨뀨', 5, '공원에 공기가 좋네요', 119);");
            st.executeUpdate("insert into Review values('87', '꾸잉', 4, '시설이 되게 잘 되어있어요', 120);");
            st.executeUpdate("insert into Review values('88', '케케', 3, '걍 쏘쏘', 122);");
            st.executeUpdate("insert into Review values('89', '뀨잉', 2, '너무 아무것도 업슴', 123);");
            st.executeUpdate("insert into Review values('90', '기요미', 5, '편안하고 좋네용!', 124);");

            st.executeUpdate("insert into Review values('91', '김말국', 5, '공원에 공기가 좋네요', 125);");
            st.executeUpdate("insert into Review values('92', '안뇽', 4, '비둘기 똥이 많음', 127);");
            st.executeUpdate("insert into Review values('93', '하늘', 4, '깨끗하고 평화로워용', 128);");
            st.executeUpdate("insert into Review values('94', '뀨엥', 3, '걍 그럼ㅋ', 128);");
            st.executeUpdate("insert into Review values('95', '안녕', 3, '그냥저냥이에요', 128);");
            st.executeUpdate("insert into Review values('96', '인생뭐있나', 4, '인생이 힘들때 휴식 겸 오기 좋네여', 128);");

            String userRegion = null; // 거주지역(구) 입력받기
            String parkId = null;     // parkId 입력받기
            String dpName = null;     // 리뷰볼 때 join 하기 위해 사용됨
            String parkName = null;

            System.out.println("1) 거주 지역 입력하기   2) 종료");
            int choice = scan.nextInt();

            if (choice == 2) System.exit(0);

            rs = st.executeQuery("select region from Park");

            int idx = 1;
            while (rs.next()) {
                if (idx == 15 || idx == 30 || idx == 45 || idx == 60 || idx == 75 || idx == 90) System.out.println(" ");
                System.out.print(idx + ") " + rs.getString("region") + " ");
                idx++;
            }
            System.out.println(" ");
            System.out.println(" ");


            Scanner scanner = new Scanner(System.in);
            System.out.print("지역구를 입력하세요 : ");
            userRegion = scanner.nextLine();

            rs = st.executeQuery("select parkId, parkName, dpName " +
                    "from Park " +
                    "where region = '" + userRegion + "';");

            idx = 1;
            while(rs.next()) {
                System.out.print(idx + ") " + rs.getString("parkName") + " ");
                idx++;
            }
            System.out.println(" ");
            System.out.println(" ");

            // parkName 입력
            System.out.print("공원명을 입력하세요 : ");
            parkName = scanner.nextLine();

            while (choice != 5) {
                System.out.print("메뉴를 선택해주세요.   ");
                System.out.println("1) 공원 소개    2) 위치 확인    3) 현재 대기 환경    4) 리뷰 보기    5) 종료하기");
                choice = scan.nextInt();

                if (choice == 1) { // 공원 소개
                    rs = st.executeQuery("select parkDescription \n" +
                            "from Park \n" +
                            "where parkName = '" + parkName + "' and region = '" + userRegion + "';");

                    while(rs.next()) {
                        System.out.println(rs.getString("parkDescription"));
                    }
                    System.out.println("");
                }
                else if (choice == 2) { // 위치 확인
                    rs = st.executeQuery("select parkAddress \n" +
                            "from Park \n" +
                            "where parkName = '" + parkName + "' and region = '" + userRegion + "';");

                    while(rs.next()) {
                        System.out.println(rs.getString("parkAddress"));
                    }
                    System.out.println("");
                }
                else if (choice == 3) { // 현재 대기 환경
                    rs = st.executeQuery("select airStatus, ozone, carbon, fineDust, ultraFineDust \n" +
                            "from Park, AirCondition \n" +
                            "where Park.region = AirCondition.region and " + " Park.parkName = '" + parkName + "';");
                    while(rs.next()) {
                        System.out.println("통합 대기 환경 지수 : " + rs.getString("airStatus"));
                        System.out.println("오존 농도 : " + rs.getString("ozone"));
                        System.out.println("일산화탄소 농도 : " + rs.getString("carbon"));
                        System.out.println("미세먼지 농도 : " + rs.getString("fineDust"));
                        System.out.println("초미세먼지 농도 : " + rs.getString("ultraFineDust"));
                    }
                    System.out.println("");
                }
                else if (choice == 4) { // 리뷰 보기
                    rs = st.executeQuery("select parkName, writer, star, reviewContent \n" +
                            "from Park, Review\n" +
                            "where Park.parkId = Review.parkId and Park.parkName = '" + parkName + "' and region = '" + userRegion +"';");
                    // 리뷰 출력
                    System.out.printf("%-25s %-15s %5s %20s\n", "* 공원 이름 *", "* 글쓴이 *", "* 별점 *", "* 한줄평 *");
                    System.out.println("---------------------------------------------------------------------------------");
                    while(rs.next()) {
                        String parkName1 = rs.getString("parkName");
                        String writer = rs.getString("writer");
                        int star = rs.getInt("star");
                        String reviewContent = rs.getString("reviewContent");

                        System.out.printf("%-25s %s %15s %23s\n", parkName1, writer, star, reviewContent);
                    }
                    System.out.println("");
                }
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
