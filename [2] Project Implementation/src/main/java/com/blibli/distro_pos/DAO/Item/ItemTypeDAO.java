package com.blibli.distro_pos.DAO.item;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.item.ItemType;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemTypeDAO extends MyConnection {

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

    public ItemType getOne(String id) {
        String sql = "SELECT * FROM item_type WHERE id_item_type = ?";
        ItemType type = new ItemType();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    type = new ItemType(
                            rs.getString("id_item_type"),
                            rs.getString("name_item_type"));
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#GET ONE# somthing error : " + e.toString());
        }

        return type;
    }

    public void save(ItemType type) {
        String sql = "INSERT INTO ITEM_TYPE (ID_ITEM_TYPE, NAME_ITEM_TYPE) VALUES (?,?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, type.getIdItem_Type());
            preparedStatement.setString(2, type.getNameItem_Type());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#INSERT# something error : " + e.toString());
        }
    }

    public void update(ItemType type) {
        String sql = "UPDATE item_type SET name_item_type = ? WHERE id_item_type = ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, type.getNameItem_Type());
            preparedStatement.setString(2, type.getIdItem_Type());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#UPDATE# something error : " + e.toString());
        }
    }
}
