package com.blibli.distro_pos.DAO.cashier;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.cashier.ShoppingCart;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.nimbus.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShoppingCartDAO extends MyConnection {


    public List<ShoppingCart> getAll() {
        String sql = "SELECT * FROM CART;";
        List<ShoppingCart> list = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    ShoppingCart shoppingCart = new ShoppingCart(
                            resultSet.getString("id_item"),
                            resultSet.getString("username"),
                            resultSet.getInt("quantity"),
                            resultSet.getString("item_name"));
                    list.add(shoppingCart);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }

        return list;
    }


    public void save(ShoppingCart shoppingCart) {
        String sql = "INSERT INTO cart(id_item, username, quantity, item_name) " +
                "VALUES (?,?,?,?);";
        try {
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, shoppingCart.getId_item());
            preparedStatement.setString(2, shoppingCart.getUsername());
            preparedStatement.setInt(3, shoppingCart.getQuantity());
            preparedStatement.setString(4, shoppingCart.getItem_name());
            preparedStatement.execute();
        } catch (Exception e) {
            System.out.println("#INSERT# something error : " + e.toString());
        }
    }

    public void update(ShoppingCart shoppingCart) {
        String sql = "UPDATE cart SET quantity = ? WHERE id_item = ?;";
        try {
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setInt(1, shoppingCart.getQuantity());
            preparedStatement.setString(2, shoppingCart.getId_item());
            preparedStatement.execute();
        } catch (Exception e) {
            System.out.println("#UPDATE# something error : " + e.toString());
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM cart WHERE id_item = ?;";
        try {
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
        } catch (Exception e) {
            System.out.println("#DELETE# something error : " + e.toString());
        }
    }

    public void clear() {
        String sql = "DELETE FROM cart;";
        try {
            Statement statement = this.con.createStatement();
            statement.execute(sql);
        } catch (Exception e) {
            System.out.println("#CLEAR# something error : " + e.toString());
        }
    }
}
