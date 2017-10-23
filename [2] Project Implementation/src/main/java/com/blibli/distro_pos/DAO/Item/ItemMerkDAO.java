package com.blibli.distro_pos.DAO.item;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.item.ItemMerk;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemMerkDAO extends MyConnection {
    public List<ItemMerk> getAll() {
        String sql = "SELECT * FROM item_merk ORDER BY name_item_merk;";
        List<ItemMerk> itemMerkList = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs != null) {
                System.out.println("getAll Merks : ");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_item_merk"));
                    ItemMerk itemMerk = new ItemMerk(rs.getString("id_item_merk"),
                            rs.getString("name_item_merk"));
                    itemMerkList.add(itemMerk);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return itemMerkList;
    }
}
