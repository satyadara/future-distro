package com.blibli.distro_pos.DAO.item;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.DAO.item.Interface.ItemColorInterface;
import com.blibli.distro_pos.DAO.item.Interface.SubItemInterface;
import com.blibli.distro_pos.Model.item.SubItem;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemColorImpl extends MyConnection implements ItemColorInterface {

    @Override
    public List<SubItem> getAll() {
        String sql = "SELECT * FROM item_color ORDER BY name_item_color;";
        List<SubItem> subItemList = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs != null) {
//                System.out.println("getAll Colors : ");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_item_color"));
                    SubItem subItem = new SubItem(rs.getString("id_item_color"),
                            rs.getString("name_item_color"));
                    subItemList.add(subItem);
                }
            }
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#FETCH# something error : " + e.toString());
        }
        return subItemList;
    }

    @Override
    public SubItem getOne(String id) {
        String sql = "SELECT * FROM item_color WHERE id_item_color = ?";
        SubItem color = new SubItem();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    color = new SubItem(
                            rs.getString("id_item_color"),
                            rs.getString("name_item_color"));
                }
            }
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#GET ONE# somthing error : " + e.toString());
        }

        return color;
    }

    @Override
    public void save(SubItem color) {
        String sql = "INSERT INTO item_color (id_item_color, name_item_color) VALUES (?,?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, color.getId());
            preparedStatement.setString(2, color.getName());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#INSERT# something error : " + e.toString());
        }
    }

    @Override
    public void update(SubItem color) {
        String sql = "UPDATE item_color SET name_item_color= ? WHERE id_item_color= ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, color.getName());
            preparedStatement.setString(2, color.getId());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#UPDATE# something error : " + e.toString());
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
        String sql = "SELECT COUNT(id_item_color) FROM item_color;";
        int result = 0;
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                result = rs.getInt("count");
            }
            System.out.println("item color counted : " + result);
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#COUNT# something error : " + e.toString());
        }

        return result;
    }

    @Override
    public List<SubItem> paginate(int page) {
        String sql = "SELECT * FROM item_color ORDER BY name_item_color LIMIT 10 OFFSET ?;";
        List<SubItem> subItemList = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            int offset = (page - 1) * 10;
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
//                System.out.println("getAll Colors : ");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_item_color"));
                    SubItem subItem = new SubItem(rs.getString("id_item_color"),
                            rs.getString("name_item_color"));
                    subItemList.add(subItem);
                }
            }
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#FETCH# something error : " + e.toString());
        }
        return subItemList;
    }
}
