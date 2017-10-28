package com.blibli.distro_pos.DAO.item;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.item.Item;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDAO extends MyConnection {

    public List<Item> getAllOriginal() {
//        String sql = "SELECT id_item, id_emp, name_item, price_item, "
//                + "(SELECT name_item_merk FROM item_merk WHERE item_merk.id_item_merk = item.merk_item) AS merk_item, stock_item, "
//                + "(SELECT name_item_color FROM item_color WHERE item_color.id_item_color = item.color_item) AS color_item,size_item, "
//                + "(SELECT name_item_type FROM item_type WHERE item_type.id_item_type = item.type_item) AS type_item, status_item FROM item ORDER BY id_item;";
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
                            rs.getString("merk_item"),
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

    public List<Item> getAllModify() {
        String sql = "SELECT id_item, id_emp, name_item, price_item, "
                + "(SELECT name_item_merk FROM item_merk WHERE item_merk.id_item_merk = item.merk_item) AS merk_item, stock_item, "
                + "(SELECT name_item_color FROM item_color WHERE item_color.id_item_color = item.color_item) AS color_item,size_item, "
                + "(SELECT name_item_type FROM item_type WHERE item_type.id_item_type = item.type_item) AS type_item, status_item FROM item ORDER BY id_item;";
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
                            rs.getString("merk_item"),
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
//        String sql = "SELECT id_item, id_emp, name_item, price_item, "
//                + "(SELECT name_item_merk FROM item_merk WHERE item_merk.id_item_merk = item.merk_item) AS merk_item, stock_item, "
//                + "(SELECT name_item_color FROM item_color WHERE item_color.id_item_color = item.color_item) AS color_item,size_item, "
//                + "(SELECT name_item_type FROM item_type WHERE item_type.id_item_type = item.type_item) AS type_item, status_item FROM item WHERE id_item = '" + id + "';";
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
                    item.setMerk(rs.getString("merk_item"));
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
//        String sql = "INSERT INTO item(id_item, id_emp, name_item, price_item, merk_item, color_item, size_item, type_item, status_item, stock_item) "
//                + "VALUES (nextval('sec_item') || ?,?,?,?,"
//                + "(SELECT id_item_merk FROM item_merk WHERE name_item_merk = ?),"
//                + "(SELECT id_item_color FROM item_color WHERE name_item_color = ?),?,"
//                + "(SELECT id_item_type FROM item_type WHERE name_item_type= ?),?,?);";
        String sql = "INSERT INTO item(id_item, id_emp, name_item, price_item, merk_item, color_item, size_item, type_item, status_item, stock_item) " +
                "VALUES (nextval('sec_item') || ?,?,?,?,?,?,?,?,?,?)";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, "-" + item.getId_item());
            preparedStatement.setString(2, "EMP-1001");
            preparedStatement.setString(3, item.getName_item());
            preparedStatement.setFloat(4, item.getPrice());
            preparedStatement.setString(5, item.getMerk());
            preparedStatement.setString(6, item.getColor());
            preparedStatement.setString(7, item.getSize());
            preparedStatement.setString(8, item.getType());
            preparedStatement.setString(9, "aktif");
            preparedStatement.setInt(10, item.getStock());
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
//        String sql = "DELETE FROM item WHERE id_item = '" + id + "';";
        String sql = "UPDATE item SET status_item = 'Tidak Aktif' WHERE id_item = '" + id + "'";
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

            System.out.println("item counted : " + result);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#COUNT# something error : " + e.toString());
        }

        return result;
    }

    public List<Item> paginate(int page) {
        String sql = "SELECT * FROM item ORDER BY id_item LIMIT 10 OFFSET ?;";
        List<Item> itemList = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            int offset = (page - 1) * 10;
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                System.out.println("getAll\t: ");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_item"));
                    Item item = new Item(
                            rs.getString("id_item"),
                            rs.getString("id_emp"),
                            rs.getString("name_item"),
                            rs.getFloat("price_item"),
                            rs.getString("merk_item"),
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
}
