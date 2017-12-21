package com.blibli.distro_pos.DAO.cashier;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.DAO.cashier.Interface.ShoppingCartInterface;
import com.blibli.distro_pos.Model.cashier.ShoppingCart;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShoppingCartDAO extends MyConnection implements ShoppingCartInterface {
    @Override
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
                            resultSet.getString("item_name"),
                            resultSet.getDouble("subtotal"));
                    list.add(shoppingCart);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }

        return list;
    }

    @Override
    public void save(ShoppingCart shoppingCart) {
        String sql = "INSERT INTO cart(id_item, username, quantity, item_name, subtotal) " +
                "VALUES (?,?,?,?,?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, shoppingCart.getId_item());
            preparedStatement.setString(2, shoppingCart.getUsername());
            preparedStatement.setInt(3, shoppingCart.getQuantity());
            preparedStatement.setString(4, shoppingCart.getItem_name());
            preparedStatement.setDouble(5, shoppingCart.getSubtotal());
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#INSERT# something error : " + e.toString());
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        String sql = "UPDATE cart SET quantity = ?, subtotal = ? WHERE id_item = ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setInt(1, shoppingCart.getQuantity());
            preparedStatement.setDouble(2, shoppingCart.getSubtotal());
            preparedStatement.setString(3, shoppingCart.getId_item());
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#UPDATE# something error : " + e.toString());
        }
    }

    @Override
    public void cancel(String id, String username) {
        String sql = "DELETE FROM cart WHERE id_item = ? AND username = ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, username);
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#DELETE# something error : " + e.toString());
        }
    }

    @Override
    public void clear(String username) {
        String sql = "DELETE FROM cart WHERE username = '" + username + "';";
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            statement.execute(sql);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#CLEAR# something error : " + e.toString());
        }
    }

    @Override
    public ShoppingCart getOne(String id) {
        String sql = "SELECT * FROM CART WHERE id_item = '" + id + "';";
        ShoppingCart shoppingCart = new ShoppingCart();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    shoppingCart = new ShoppingCart(
                            resultSet.getString("id_item"),
                            resultSet.getString("username"),
                            resultSet.getInt("quantity"),
                            resultSet.getString("item_name"),
                            resultSet.getDouble("subtotal"));
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#CHECK# something error : " + e.toString());
        }
        return shoppingCart;
    }
}
