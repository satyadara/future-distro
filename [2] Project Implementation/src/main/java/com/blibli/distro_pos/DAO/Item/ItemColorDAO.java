package com.blibli.distro_pos.DAO.item;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.item.ItemColor;
import com.blibli.distro_pos.Model.item.ItemMerk;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemColorDAO extends MyConnection {

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

    public void save(ItemColor color) {
        String sql = "INSERT INTO item_color (id_item_color, name_item_color) VALUES (?,?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, color.getIdItem_Color());
            preparedStatement.setString(2, color.getNameItem_Color());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#INSERT# something error : " + e.toString());
        }
    }

    public void update(ItemColor color) {
        String sql = "UPDATE item_color SET name_item_color= ? WHERE id_item_color= ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, color.getNameItem_Color());
            preparedStatement.setString(2, color.getIdItem_Color());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#UPDATE# something error : " + e.toString());
        }
    }
}
