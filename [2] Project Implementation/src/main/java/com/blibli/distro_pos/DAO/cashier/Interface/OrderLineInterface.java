package com.blibli.distro_pos.DAO.cashier.Interface;

import com.blibli.distro_pos.Model.cashier.OrderLine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface OrderLineInterface {
    List<OrderLine> getAll();

    OrderLine getOne(String id);

    void save(OrderLine orderLine);

    void update(OrderLine orderLine);
}
