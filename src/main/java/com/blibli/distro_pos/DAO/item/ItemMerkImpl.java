package com.blibli.distro_pos.DAO.item;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.DAO.item.Interface.ItemMerkInterface;
import com.blibli.distro_pos.DAO.item.Interface.SubItemInterface;
import com.blibli.distro_pos.Model.item.SubItem;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemMerkImpl extends MyConnection implements ItemMerkInterface {
    @Override
    public List<SubItem> getAll() {
        String sql = "SELECT * FROM item_merk ORDER BY name_item_merk;";
        List<SubItem> SubItemList = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs != null) {
//                System.out.println("getAll Merks : ");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_item_merk"));
                    SubItem SubItem = new SubItem(rs.getString("id_item_merk"),
                            rs.getString("name_item_merk"));
                    SubItemList.add(SubItem);
                }
            }
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#FETCH# something error : " + e.toString());
        }
        return SubItemList;
    }

    @Override
    public SubItem getOne(String id) {
        String sql = "SELECT * FROM item_merk WHERE id_item_merk = ?;";
        SubItem merk = new SubItem();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    merk = new SubItem(
                            rs.getString("id_item_merk"),
                            rs.getString("name_item_merk"));
                }
            }
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#GET ONE# somthing error : " + e.toString());
        }

        return merk;
    }

    @Override
    public void save(SubItem merk) {
        String sql = "INSERT INTO item_merk (id_item_merk, name_item_merk) VALUES (?,?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, merk.getId());
            preparedStatement.setString(2, merk.getName());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#INSERT# something error : " + e.toString());
        }
    }

    @Override
    public void update(SubItem merk) {
        String sql = "UPDATE item_merk SET name_item_merk = ? WHERE id_item_merk = ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, merk.getName());
            preparedStatement.setString(2, merk.getId());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#UPDATE# something error : " + e.toString());
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
//            System.out.println("#COUNT# something error : " + e.toString());
        }

        return result;
    }

    @Override
    public List<SubItem> paginate(int page) {
        String sql = "SELECT * FROM item_merk ORDER BY name_item_merk LIMIT 10 OFFSET ?;";
        List<SubItem> SubItemList = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            int offset = (page - 1) * 10;
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
//                System.out.println("getAll Merks : ");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_item_merk"));
                    SubItem SubItem = new SubItem(rs.getString("id_item_merk"),
                            rs.getString("name_item_merk"));
                    SubItemList.add(SubItem);
                }
            }
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#FETCH# something error : " + e.toString());
        }
        return SubItemList;
    }
}
