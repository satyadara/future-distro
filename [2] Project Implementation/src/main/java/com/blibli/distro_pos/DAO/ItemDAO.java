package com.blibli.distro_pos.DAO;

import com.blibli.distro_pos.Model.Item;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDAO {
    private Connection con;

    public ItemDAO() {
    }

    public void connect() {
        try {
            String db_password = "postgres";
            String db_username = "postgres";
            String uri = "jdbc:postgresql://localhost:5432/satyadara";
            this.con = DriverManager.getConnection(uri, db_username, db_password);
            System.out.println("open connection");

        } catch (Exception e) {
            System.out.println("error " + e.toString());
        }
    }

    public void disconnect() {
        try {
            this.con.close();
            System.out.println("close connection");

        } catch (Exception e) {
            System.out.println("error " + e.toString());
        }
    }

    public List<Item> getAll() {
        String sql = "SELECT * FROM item;";
        List<Item> itemList = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    System.out.println("getAll : " + rs.getString("id_item"));
                    Item item = new Item(
                            rs.getString("id_item"),
                            rs.getString("id_emp"),
                            rs.getString("name_item"),
                            rs.getFloat("price_item"),
                            rs.getInt("stock_item"),
                            rs.getString("color_item"),
                            rs.getString("size_item"),
                            rs.getString("type_item"),
                            rs.getString("status_item"));
                    itemList.add(item);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("something error :" + e.toString());
        }

        return itemList;
    }

    public Item getOne(String id) {
        String sql = "SELECT * FROM item WHERE id_item = " + id + ";";
        Item item = new Item();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    System.out.println("getOne : " + rs.getString("id_item"));
                    item.setId_item(rs.getString("id_item"));
                    item.setId_emp(rs.getString("id_emp"));
                    item.setName_item(rs.getString("name_item"));
                    item.setPrice(rs.getFloat("price_item"));
                    item.setStock(rs.getInt("stock_item"));
                    item.setColor(rs.getString("color_item"));
                    item.setSize(rs.getString("size_item"));
                    item.setType(rs.getString("type_item"));
                    item.setStatus(rs.getString("status_item"));
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("something error :" + e.toString());
        }

        return item;
    }

    public void save(Item item) {
        String sql = "INSERT INTO item(id_item, id_emp, name_item, price_item, stock_item, color_item, size_item, type_item, status_item)"
                + " VALUES ('" + item.getId_item() + "-' || nextval('sec_item'), 'EMP-1002', '"
                /* + item.getId_emp() + "," *******/
                + item.getName_item() + "',"
                + item.getPrice() + ","
                + item.getStock() + ",'"
                + item.getColor() + "','"
                + item.getSize() + "','"
                + item.getType() + "',"
                + " 'Tersedia' );";
        System.out.println(sql);
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            statement.executeQuery(sql);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#INSERT# something error :" + e.toString());
        }
    }

    public void update(Item item) {
        String sql = "UPDATE item SET id_item = " + item.getId_item() +
                ", id_emp = " + item.getId_emp() +
                ",name_item = " + item.getName_item() +
                ", price_item =" + item.getPrice() +
                ", stock_item =" + item.getStock() +
                ", color_item = " + item.getColor() +
                ", size_item =" + item.getSize() +
                ", type_item = " + item.getType() +
                ", status_item = " + item.getStatus() + "WHERE id_item = " + item.getId_item() + ";";
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            statement.executeQuery(sql);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("something error :" + e.toString());
        }
    }

    public int count() {
        String sql = "SELECT COUNT(id_item) FROM item;";
        int result = 0;
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                result = rs.getInt("count");
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#COUNT# something error : " + e.toString());
        }

        return result;
    }
}
