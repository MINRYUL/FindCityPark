package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        //알아서 바꾸셈
        String url = "jdbc:postgresql://localhost/";
        String user = "postgres";
        String password = "1221zxc151";

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

            System.out.println("Creating Park, Department, Review relations");
            // 3개 테이블 생성: Create table문 이용
            st.executeUpdate("create table Park(parkId int not null,parkName text not null, parkDescription text not null, parkAddress text, region varchar(100) not null, parkTelephone varchar(50), parkUrl text, parkEquip text, dpName varchar(100) not null, PRIMARY KEY(parkId));");
            st.executeUpdate("create table Department(dpName varchar(100) not null,dpAddress text, varchar(50), PRIMARY KEY(dpName));");
            st.executeUpdate("create table Review(reviewId int not null, writer varchar(20), star int not null, reviewContent varchar(50), parkId int not null, PRIMARY KEY(reviewId));");


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
