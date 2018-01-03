package com.blibli.distro_pos.DAO.summary;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.DAO.summary.Interface.SummaryInterface;
import com.blibli.distro_pos.Model.summary.LoyalCustomer;
import com.blibli.distro_pos.Model.summary.MostSoldItem;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.nimbus.State;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SummaryImpl extends MyConnection implements SummaryInterface {
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
//            System.out.println("#MOSTSOLDITEM# somthing error : " + e.toString());
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

    @Override
    public String getChartByMonth() {
        String sql = "SELECT array_to_json(ARRAY_AGG(row_to_json(response))) AS JSON FROM (" +
                "  SELECT " +
                "    TO_CHAR(DATE(DATE_TRUNC('month', DATE_TRANS)), 'YYYY-MM') AS y, " +
                "    AVG(TOTAL_TRANS)                      AS average" +
                "  FROM TRANSACTION " +
                "  WHERE DATE_TRANS > now() - INTERVAL '12 month' " +
                "  GROUP BY 1" +
                "  ORDER BY 2 DESC, 1 DESC" +
                ") response;";
        String result = "";
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    result = resultSet.getString("json");
                }
            }
            this.disconnect();
        } catch (Exception e) {

        }
        return result;
    }

    @Override
    public int countTransactionOfThisYear() {
        String sql = "SELECT COUNT(*) FROM TRANSACTION WHERE DATE_TRANS BETWEEN DATE_TRUNC('year', now()) AND now();";
        return getCount(sql);
    }

    @Override
    public int countItemOutOfStock() {
        String sql = "SELECT COUNT(*) FROM item WHERE stock_item < 1 AND status_item = 'Aktif'";
        return getCount(sql);
    }

    @Override
    public double getNetSalesOfThisYear() {
        String sql = "SELECT AVG(RESULT) AS RESULT FROM (SELECT t.TOTAL_TRANS AS RESULT FROM TRANSACTION AS t WHERE DATE_TRANS BETWEEN DATE_TRUNC('year', now()) AND now() UNION SELECT o.AMOUNT_OUT*-1 FROM OUTCOME AS o WHERE o.DATE_OUT BETWEEN DATE_TRUNC('year', now()) AND now()) SUMMARY;";
        return getSales(sql);
    }

    @Override
    public double getGrossSalesOfThisYear() {
        String sql = "SELECT SUM(TOTAL_TRANS) AS RESULT FROM TRANSACTION WHERE DATE_TRANS BETWEEN DATE_TRUNC('year', now()) AND now();";
        return getSales(sql);
    }

    private int getCount(String sql) {
        int result = 0;
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    result = resultSet.getInt("count");
                }
            }
            this.disconnect();
        } catch (Exception e) {

        }
        return result;
    }

    private double getSales(String sql) {
        double result = 0;
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null ) {
                while (resultSet.next())    {
                    result = resultSet.getDouble("result");
                }
            }
            this.disconnect();
        } catch (Exception e) {

        }
        return result;
    }
}

