package com.blibli.distro_pos.DAO.summary;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.DAO.summary.Interface.SummaryInterface;
import com.blibli.distro_pos.Model.summary.LoyalCustomer;
import com.blibli.distro_pos.Model.summary.MostSoldItem;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SummaryDAO extends MyConnection implements SummaryInterface {
    @Override
    public List<MostSoldItem> getMostSoldItemThisMonth() {
        String sql = "SELECT ord.ID_ITEM, i.NAME_ITEM , SUM(ord.QUANTITY) AS quantity, SUM(ord.SUBTOTAL) AS subtotal " +
                "FROM ORDERLINE ord JOIN ITEM i ON (ord.ID_ITEM = i.ID_ITEM) " +
                "JOIN TRANSACTION t ON (ord.ID_TRANS = t.ID_TRANS) " +
                "WHERE t.DATE_TRANS > DATE_TRUNC('month', t.DATE_TRANS) GROUP BY 1,2 ORDER BY 3 DESC LIMIT 3";

        return getMostSoldItem(sql);
    }

    @Override
    public List<MostSoldItem> getMostSoldItemThisYear() {
        String sql = "SELECT ord.ID_ITEM, i.NAME_ITEM , SUM(ord.QUANTITY) AS quantity, SUM(ord.SUBTOTAL) AS subtotal " +
                "FROM ORDERLINE ord JOIN ITEM i ON (ord.ID_ITEM = i.ID_ITEM) " +
                "JOIN TRANSACTION t ON (ord.ID_TRANS = t.ID_TRANS) " +
                "WHERE t.DATE_TRANS > DATE_TRUNC('year', t.DATE_TRANS) GROUP BY 1,2 ORDER BY 3 DESC LIMIT 3";

        return getMostSoldItem(sql);
    }

    @Override
    public List<LoyalCustomer> getLoyalCustomerThisMonth() {
        String sql = "SELECT CUSTOMER_TRANS, SUM(TOTAL_TRANS) AS TOTAL FROM TRANSACTION " +
                "WHERE DATE_TRANS > DATE_TRUNC('month', now()) GROUP BY 1 ORDER BY 2 DESC LIMIT 3;";
        return getLoyalCustomer(sql);
    }

    @Override
    public List<LoyalCustomer> getLoyalCustomerThisYear() {
        String sql = "SELECT CUSTOMER_TRANS, SUM(TOTAL_TRANS) AS TOTAL FROM TRANSACTION " +
                "WHERE DATE_TRANS > DATE_TRUNC('year', now()) GROUP BY 1 ORDER BY 2 DESC LIMIT 3;";
        return getLoyalCustomer(sql);
    }

    @Override
    public List<MostSoldItem> getMostSoldItem(String sql) {
        List<MostSoldItem> list = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    MostSoldItem mostSoldItem = new MostSoldItem(
                            resultSet.getString("ID_ITEM"),
                            resultSet.getString("NAME_ITEM"),
                            resultSet.getInt("QUANTITY"),
                            resultSet.getDouble("SUBTOTAL")
                    );
                    list.add(mostSoldItem);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#MOSTSOLDITEM# somthing error : " + e.toString());
        }

        return list;
    }

    @Override
    public List<LoyalCustomer> getLoyalCustomer(String sql) {
        List<LoyalCustomer> list = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    LoyalCustomer loyalCustomer = new LoyalCustomer(
                            resultSet.getString("CUSTOMER_TRANS"),
                            resultSet.getDouble("TOTAL")
                    );
                    list.add(loyalCustomer);
                }
            }
            this.disconnect();
        } catch (Exception e) {

        }
        return list;
    }

}
