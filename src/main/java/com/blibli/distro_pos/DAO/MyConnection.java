package com.blibli.distro_pos.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {
    protected Connection con;

    public MyConnection() {
    }

    public MyConnection(Connection con) {
        this.con = con;
    }

    public void connect() {
        try {
            String db_username = "postgres";
            String db_password = "postgres";
            String uri = "jdbc:postgresql://35.200.231.200:5432/distro_pos";
            this.con = DriverManager.getConnection(uri, db_username, db_password);
            System.out.println("*****open connection*****");

        } catch (Exception e) {
            System.out.println("error " + e.toString());
        }
    }

    public void disconnect() {
        try {
            this.con.close();
            System.out.println("*****close connection*****");

        } catch (Exception e) {
            System.out.println("error " + e.toString());
        }
    }
}
