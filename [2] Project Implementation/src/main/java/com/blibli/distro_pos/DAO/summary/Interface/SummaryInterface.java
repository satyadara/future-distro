package com.blibli.distro_pos.DAO.summary.Interface;

import com.blibli.distro_pos.Model.summary.LoyalCustomer;
import com.blibli.distro_pos.Model.summary.MostSoldItem;

import java.util.List;

public interface SummaryInterface {
    List<MostSoldItem> getMostSoldItemThisMonth();

    List<MostSoldItem> getMostSoldItemThisYear();

    List<LoyalCustomer> getLoyalCustomerThisMonth();

    List<LoyalCustomer> getLoyalCustomerThisYear();

    List<MostSoldItem> getMostSoldItem(String sql);

    List<LoyalCustomer> getLoyalCustomer(String sql);

    String getChartByMonth();
}
