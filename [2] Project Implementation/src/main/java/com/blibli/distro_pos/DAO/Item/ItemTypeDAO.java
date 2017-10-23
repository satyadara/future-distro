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
}
