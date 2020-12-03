package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

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
            //rs = st.executeQuery("SELECT VERSION()");

            st.executeUpdate("drop Table apply cascade");
            st.executeUpdate("drop Table college");
            st.executeUpdate("drop Table student");

            System.out.println("Creating College, Student, Apply relations");
            // 3개 테이블 생성: Create table문 이용
            st.executeUpdate("create table College(cName varchar(20), state varchar(20), enrollment int);");
            st.executeUpdate("create table Student(sID int, sName varchar(20), GPA numeric(2,1), sizeHS int);");
            st.executeUpdate("create table Apply(sID int, cName varchar(20), major varchar(20), decision char);");

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

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
