package com.blibli.distro_pos.DAO.item;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.item.ItemColor;
import com.blibli.distro_pos.Model.item.ItemMerk;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemColorDAO extends MyConnection implements BasicDAO<ItemColor, String> {

    @Override
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

    @Override
    public ItemColor getOne(String id) {
        String sql = "SELECT * FROM item_color WHERE id_item_color = ?";
        ItemColor color = new ItemColor();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    color = new ItemColor(
                            rs.getString("id_item_merk"),
                            rs.getString("name_item_merk"));
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#GET ONE# somthing error : " + e.toString());
        }

        return color;
    }

    @Override
    public void save(ItemColor color) {
        String sql = "INSERT INTO item_color (id_item_color, name_item_color) VALUES (?,?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, color.getId());
            preparedStatement.setString(2, color.getName());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#INSERT# something error : " + e.toString());
        }
    }

    @Override
    public void update(ItemColor color) {
        String sql = "UPDATE item_color SET name_item_color= ? WHERE id_item_color= ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, color.getName());
            preparedStatement.setString(2, color.getId());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#UPDATE# something error : " + e.toString());
        }
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void softDelete(String id) {

    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(id_item_merk) FROM item_merk;";
        int result = 0;
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                result = rs.getInt("count");
            }
            System.out.println("item merk counted : " + result);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#COUNT# something error : " + e.toString());
        }

        return result;
    }

    @Override
    public List<ItemColor> paginate(int page) {
        String sql = "SELECT * FROM item_color ORDER BY name_item_color LIMIT 10 OFFSET ?;";
        List<ItemColor> itemColorList = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            int offset = (page - 1) * 10;
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                System.out.println("getAll Colors : ");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_item_color"));
                    ItemColor itemColor = new ItemColor(rs.getString("id_item_volor"),
                            rs.getString("name_item_merk"));
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
