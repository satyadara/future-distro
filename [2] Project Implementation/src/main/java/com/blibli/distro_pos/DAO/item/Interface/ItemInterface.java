package com.blibli.distro_pos.DAO.item.Interface;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.Model.item.Item;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface ItemInterface extends BasicDAO<Item, String> {
    String getListString();

    List<Item> getItemList(ResultSet rs);

    void setActive(String id);

    Map<String, Object> search(String key, int page);

    void addOrMinStock(String id_item, int quantity);

    List<Item> getByType(String id_type);

    List<Item> getOutOfStock(int page);

    int countOutOfStock();
}
