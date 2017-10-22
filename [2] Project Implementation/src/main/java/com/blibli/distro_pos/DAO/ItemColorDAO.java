package com.blibli.distro_pos.DAO;

import com.blibli.distro_pos.Model.ItemColor;
import com.blibli.distro_pos.Model.ItemType;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemColorDAO {
    private Connection con;

    public ItemColorDAO() {
    }

    public void connect() {
        try {
            String db_password = "postgres";
            String db_username = "postgres";
            String uri = "jdbc:postgresql://localhost:5432/satyadara";
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

    public List<ItemColor> getAll() {
        String sql = "SELECT * FROM item_color ORDER BY name_item_color;";
        List<ItemColor> itemColorList = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs != null) {
                System.out.println("getAll Colors : ");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_item_color"));
                    ItemColor itemColor = new ItemColor(rs.getString("id_item_color"),
                            rs.getString("name_item_color"));
                    itemColorList.add(itemColor);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return itemColorList;
    }
}
