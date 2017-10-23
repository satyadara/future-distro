package com.blibli.distro_pos.DAO.Item;

import com.blibli.distro_pos.Model.Item.ItemType;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemTypeDAO {
    private Connection con;

    public ItemTypeDAO() {
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

    public List<ItemType> getAll() {
        String sql = "SELECT * FROM item_type ORDER BY name_item_type;";
        List<ItemType> itemTypeList = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs != null) {
                System.out.println("getAll Types : ");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_item_type"));
                    ItemType itemType = new ItemType(rs.getString("id_item_type"),
                            rs.getString("name_item_type"));
                    itemTypeList.add(itemType);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return itemTypeList;
    }
}
