package com.blibli.distro_pos.DAO.discount.Interface;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.Model.discount.Discount;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface DiscountInterface extends BasicDAO<Discount, String> {

    void setActive(String id);

    Map<String, Object> search(String key, int page);

    List<Discount> getDiscountList(ResultSet rs);

    String getListString();
}
