package com.blibli.distro_pos.DAO.cashier.Interface;

import com.blibli.distro_pos.Model.cashier.ShoppingCart;

import java.util.List;

public interface ShoppingCartInterface {
    List<ShoppingCart> getAll();

    void save(ShoppingCart shoppingCart);

    void update(ShoppingCart shoppingCart);

    void cancel(String id, String username);

    void clear(String username);

    ShoppingCart getOne(String id);
}
