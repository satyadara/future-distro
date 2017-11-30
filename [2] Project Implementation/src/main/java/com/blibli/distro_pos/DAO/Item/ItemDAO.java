package com.blibli.distro_pos.DAO.item;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.discount.Discount;
import com.blibli.distro_pos.Model.item.Item;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemDAO extends MyConnection implements BasicDAO<Item, String> {
    private static final String COL_ID_ITEM = "id_item";
    private static final String COL_ID_EMP = "name_item";
    private static final String COL_NAME = "name_item";
    private static final String COL_PRICE = "price_item";
    private static final String COL_MERK = "merk_item";
    private static final String COL_STOCK = "stock_item";
    private static final String COL_IMAGE = "image_item";
    private static final String COL_COLOR = "color_item";
    private static final String COL_SIZE = "size_item";
    private static final String COL_TYPE = "type_item";
    private static final String COL_STATUS = "status_item";
    public static final String LIST = "itemList";

    @Override
    public List<Item> getAll() {
        String sql = "SELECT id_item, id_emp, name_item, price_item, im.name_item_merk AS merk_item, stock_item,  " +
                "ic.name_item_color AS color_item,size_item, it.name_item_type AS type_item, status_item FROM item i " +
                "JOIN item_merk im ON (i.merk_item = im.id_item_merk) JOIN item_color ic ON (i.color_item = ic.id_item_color) " +
                "JOIN item_type it ON (i.type_item = it.id_item_type) ORDER BY id_item;";
        List<Item> itemList = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            itemList = getItemList(rs);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("something error :" + e.toString());
        }

        return itemList;
    }

    @Override
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
                    item.setImage(rs.getString("image_item"));
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

    @Override
    public void save(Item item) {
        String sql = "INSERT INTO item(id_item, id_emp, name_item, price_item, image_item, merk_item, color_item, size_item, type_item, status_item, stock_item) " +
                "VALUES (nextval('sec_item') || ?,?,?,?,?,?,?,?,?,?,?)";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, "-" + item.getId_item());
            preparedStatement.setString(2, item.getId_emp());
            preparedStatement.setString(3, item.getName_item());
            preparedStatement.setFloat(4, item.getPrice());
            preparedStatement.setString(5, item.getImage());
            preparedStatement.setString(6, item.getMerk());
            preparedStatement.setString(7, item.getColor());
            preparedStatement.setString(8, item.getSize());
            preparedStatement.setString(9, item.getType());
            preparedStatement.setString(10, "Aktif");
            preparedStatement.setInt(11, item.getStock());
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#INSERT# something error :" + e.toString());
        }
    }

    @Override
    public void update(Item item) {
        String sql = "UPDATE item SET name_item = ?, price_item = ?, stock_item = ?, image_item = ?, color_item = ?, size_item = ?,"
                + " type_item = ? WHERE id_item = ?";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, item.getName_item());
            preparedStatement.setFloat(2, item.getPrice());
            preparedStatement.setInt(3, item.getStock());
            preparedStatement.setString(4, item.getImage());
            preparedStatement.setString(5, item.getColor());
            preparedStatement.setString(6, item.getSize());
            preparedStatement.setString(7, item.getType());
            preparedStatement.setString(8, item.getId_item());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#UPDATE# something error :" + e.toString());
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM item WHERE id_item = '" + id + "';";
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            statement.execute(sql);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#DELETE# something error :" + e.toString());
        }
    }

    @Override
    public void softDelete(String id) {
//        String sql = "DELETE FROM item WHERE id_item = '" + id + "';";
        String sql = "UPDATE item SET status_item = 'Tidak Aktif' WHERE id_item = '" + id + "'";
        System.out.println(id);
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            statement.executeQuery(sql);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#SOFT.DELETE# something error :" + e.toString());
        }
    }

    @Override
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
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#COUNT# something error : " + e.toString());
        }

        return result;
    }

    @Override
    public List<Item> paginate(int page) {
        String sql = "SELECT id_item, id_emp, name_item, price_item, im.name_item_merk AS merk_item, stock_item, image_item," +
                "ic.name_item_color AS color_item,size_item, it.name_item_type AS type_item, status_item FROM item i " +
                "JOIN item_merk im ON (i.merk_item = im.id_item_merk) JOIN item_color ic ON (i.color_item = ic.id_item_color) " +
                "JOIN item_type it ON (i.type_item = it.id_item_type) ORDER BY id_item LIMIT 10 OFFSET ?;";
        List<Item> itemList = new ArrayList<>();
        this.disconnect();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            int offset = (page - 1) * 10;
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            itemList = getItemList(rs);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("something error :" + e.toString());
        }

        return itemList;
    }

    private List<Item> getItemList(ResultSet rs) {
        List<Item> itemList = new ArrayList<>();
        try {
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
                            rs.getString("image_item"),
                            rs.getString("color_item"),
                            rs.getString("size_item"),
                            rs.getString("type_item"),
                            rs.getString("status_item"));
                    itemList.add(item);
                }
            }
        } catch (Exception e) {
            System.out.println("Get List item ");
        }
        return itemList;
    }

    public void setActive(String id) {
//        String sql = "DELETE FROM item WHERE id_item = '" + id + "';";
        String sql = "UPDATE item SET status_item = 'Aktif' WHERE id_item = '" + id + "'";
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            statement.execute(sql);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#SET ACTIVE# something error :" + e.toString());
        }
    }

    public Map<String, Object> search(String key, int page) {
        String sql = "SELECT id_item, id_emp, name_item, price_item, im.name_item_merk AS merk_item, stock_item, image_item," +
                "ic.name_item_color AS color_item,size_item, it.name_item_type AS type_item, status_item FROM item i " +
                "JOIN item_merk im ON (i.merk_item = im.id_item_merk) JOIN item_color ic ON (i.color_item = ic.id_item_color) " +
                "JOIN item_type it ON (i.type_item = it.id_item_type) WHERE name_item LIKE '%'||?||'%' ORDER BY id_item LIMIT 10 OFFSET ?;";
        String sql_counter = "SELECT COUNT(id_item) FROM item WHERE item.name_item LIKE '%'||?||'%';";
        Map<String, Object> map = new HashMap<>();
        List<Item> itemList;
        int count = 0;
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            int offset = (page - 1) * 10;
            preparedStatement.setString(1, key);
            preparedStatement.setInt(2, offset);
            ResultSet rs = preparedStatement.executeQuery();
            itemList = getItemList(rs);

            preparedStatement = this.con.prepareStatement(sql_counter);
            preparedStatement.setString(1, key);
            rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    count = rs.getInt("count");
                }
            }
            map.put(LIST, itemList);
            map.put("count", count);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error :" + e.toString());
        }

        return map;
    }
}
