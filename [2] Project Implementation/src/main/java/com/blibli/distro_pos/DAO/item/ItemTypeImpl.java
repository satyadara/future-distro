package com.blibli.distro_pos.DAO.item;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.DAO.item.Interface.ItemTypeInterface;
import com.blibli.distro_pos.DAO.item.Interface.SubItemInterface;
import com.blibli.distro_pos.Model.item.SubItem;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemTypeImpl extends MyConnection implements ItemTypeInterface {
    @Override
    public List<SubItem> getAll() {
        String sql = "SELECT * FROM item_type ORDER BY name_item_type;";
        List<SubItem> SubItemList = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs != null) {
                System.out.println("getAll Types : ");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_item_type"));
                    SubItem SubItem = new SubItem(rs.getString("id_item_type"),
                            rs.getString("name_item_type"));
                    SubItemList.add(SubItem);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return SubItemList;
    }

    @Override
    public SubItem getOne(String id) {
        String sql = "SELECT * FROM item_type WHERE id_item_type = ?";
        SubItem type = new SubItem();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    type = new SubItem(
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

    @Override
    public void save(SubItem type) {
        String sql = "INSERT INTO ITEM_TYPE (ID_ITEM_TYPE, NAME_ITEM_TYPE) VALUES (?,?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, type.getId());
            preparedStatement.setString(2, type.getName());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#INSERT# something error : " + e.toString());
        }
    }

    @Override
    public void update(SubItem type) {
        String sql = "UPDATE item_type SET name_item_type = ? WHERE id_item_type = ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, type.getName());
            preparedStatement.setString(2, type.getId());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#UPDATE# something error : " + e.toString());
        }
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void softDelete(String s) {

    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(id_item_type) FROM item_type;";
        int result = 0;
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                result = rs.getInt("count");
            }
            System.out.println("item type counted : " + result);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#COUNT# something error : " + e.toString());
        }

        return result;
    }

    @Override
    public List<SubItem> paginate(int page) {
        String sql = "SELECT * FROM item_type ORDER BY name_item_type LIMIT 10 OFFSET ?;";
        List<SubItem> SubItemList = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            int offset = (page - 1) * 10;
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                System.out.println("getAll Types : ");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_item_type"));
                    SubItem SubItem = new SubItem(rs.getString("id_item_type"),
                            rs.getString("name_item_type"));
                    SubItemList.add(SubItem);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return SubItemList;
    }
}
