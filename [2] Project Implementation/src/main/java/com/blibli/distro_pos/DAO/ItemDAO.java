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

    public List<Item> getAll() {
        String sql = "SELECT * FROM item ORDER BY id_item;";
        List<Item> itemList = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                System.out.println("getAll\t:");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_item"));
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
        String sql = "SELECT * FROM item WHERE id_item = '" + id + "';";
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
        String sql = "INSERT INTO item(id_item, id_emp, name_item, price_item, color_item, size_item, type_item, status_item, stock_item) "
                + "VALUES (nextval('sec_item') || ?,?,?,?,?,?,?,?,?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, "-" + item.getId_item());
            preparedStatement.setString(2, "EMP-1002");
            preparedStatement.setString(3, item.getName_item());
            preparedStatement.setFloat(4, item.getPrice());
            preparedStatement.setString(5, item.getColor());
            preparedStatement.setString(6, item.getSize());
            preparedStatement.setString(7, item.getType());
            preparedStatement.setString(8, "aktif");
            preparedStatement.setInt(9, item.getStock());
            preparedStatement.executeQuery();
            System.out.println("Success insert item : " + item.getId_item());
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#INSERT# something error :" + e.toString());
        }
    }

    public void update(Item item) {
        String sql = "UPDATE item SET name_item = ?, price_item = ?, stock_item = ?, color_item = ?, size_item = ?,"
                + " type_item = ? WHERE id_item = ?";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, item.getName_item());
            preparedStatement.setFloat(2, item.getPrice());
            preparedStatement.setInt(3, item.getStock());
            preparedStatement.setString(4, item.getColor());
            preparedStatement.setString(5, item.getSize());
            preparedStatement.setString(6, item.getType());
            preparedStatement.setString(7, item.getId_item());
            preparedStatement.executeQuery();
            System.out.println("Success update item : " + item.getId_item());
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#UPDATE# something error :" + e.toString());
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM item WHERE id_item = '" + id + "';";
        System.out.println(id);
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            statement.executeQuery(sql);

            System.out.println("Success delete item : " + id);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#DELETE# something error :" + e.toString());
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

            System.out.println("Item counted : " + result);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#COUNT# something error : " + e.toString());
        }

        return result;
    }
}