package com.blibli.distro_pos.DAO.cashier;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.DAO.cashier.Interface.OrderLineInterface;
import com.blibli.distro_pos.Model.cashier.OrderLine;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderLineImpl extends MyConnection implements OrderLineInterface {
    @Override
    public List<OrderLine> getAll() {
        String sql = "SELECT * FROM orderline;";
        List<OrderLine> list = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    OrderLine orderLine = new OrderLine(
                            resultSet.getString("id_orderline"),
                            resultSet.getString("id_trans"),
                            resultSet.getString("id_item"),
                            resultSet.getDouble("item_price"),
                            resultSet.getDouble("subtotal"),
                            resultSet.getInt("quantity")
                    );
                    list.add(orderLine);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return list;
    }

    @Override
    public OrderLine getOne(String id) {
        String sql = "SELECT * FROM orderline WHERE id_orderline = '" + id + "';";
        OrderLine orderLine = new OrderLine();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    orderLine = new OrderLine(
                            resultSet.getString("id_orderline"),
                            resultSet.getString("id_trans"),
                            resultSet.getString("id_item"),
                            resultSet.getDouble("item_price"),
                            resultSet.getDouble("subtotal"),
                            resultSet.getInt("quantity")
                    );
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#GET# something error : " + e.toString());
        }
        return orderLine;
    }

    @Override
    public void save(OrderLine orderLine) {
        String sql = "INSERT INTO orderline( id_trans, id_item, item_price, quantity, subtotal) " +
                "VALUES(?,?,?,?,?)";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, orderLine.getId_trans());
            preparedStatement.setString(2, orderLine.getId_item());
            preparedStatement.setDouble(3, orderLine.getItem_price());
            preparedStatement.setInt(4, orderLine.getQuantity());
            preparedStatement.setDouble(5, orderLine.getSubtotal());
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#SAVE# something error : " + e.toString());
        }
    }

    @Override
    public void update(OrderLine orderLine) {
        String sql = "UPDATE orderline SET quantity = ? WHERE id_orderline = ?";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setInt(1, orderLine.getQuantity());
            preparedStatement.setString(2, orderLine.getId_orderline());
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#UPDATE# something error : " + e.toString());
        }
    }

    @Override
    public List<OrderLine> getByIdTransaction(String id_trans) {
        String sql = "SELECT o.id_orderline, o.id_trans, o.id_item, o.item_price, o.subtotal, o.quantity, i.name_item  FROM orderline o JOIN item i ON(o.id_item = i.id_item) WHERE id_trans = ?;";
        List<OrderLine> list = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id_trans);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    OrderLine orderLine = new OrderLine(
                            resultSet.getString("id_orderline"),
                            resultSet.getString("id_trans"),
                            resultSet.getString("id_item"),
                            resultSet.getDouble("item_price"),
                            resultSet.getDouble("subtotal"),
                            resultSet.getInt("quantity"),
                            resultSet.getString("name_item")
                    );
                    list.add(orderLine);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        return list;
    }
}
