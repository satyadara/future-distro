package com.blibli.distro_pos.DAO.ledger;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.DAO.ledger.Interface.LedgerInterface;
import com.blibli.distro_pos.Model.ledger.Ledger;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.nimbus.State;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LedgerImpl extends MyConnection implements LedgerInterface {
    @Override
    public List<Ledger> getIndex() {
        String sql = "SELECT TO_CHAR(t.DATE_TRANS, 'YYYY-MM-DD')     AS DATE, " +
                "'Transaksi pembelian ' || t.ID_TRANS       AS TITLE, " +
                "t.TOTAL_TRANS                              AS IN, " +
                "NULL                                       AS OUT " +
                "FROM TRANSACTION AS t " +
                "WHERE t.DATE_TRANS BETWEEN DATE_TRUNC('month', now()) AND now() " +
                "UNION " +
                "SELECT TO_CHAR(o.DATE_OUT, 'YYYY-MM-DD') AS DATE, " +
                "o.TITLE_OUT                            AS TITLE, " +
                "NULL                                   AS IN, " +
                "o.AMOUNT_OUT                           AS OUT " +
                "FROM OUTCOME                           AS o " +
                "WHERE o.DATE_OUT BETWEEN DATE_TRUNC('month', now()) AND now() " +
                "ORDER BY DATE DESC, TITLE DESC LIMIT 10 OFFSET 0;";
        return get(sql);
    }

    @Override
    public List<Ledger> getFilter(String date_from, String date_to, int offset) {
        String sql = "SELECT TO_CHAR(t.DATE_TRANS, 'YYYY-MM-DD')     AS DATE, " +
                "'Transaksi pembelian ' || t.ID_TRANS       AS TITLE, " +
                "t.TOTAL_TRANS                              AS IN, " +
                "NULL                                       AS OUT " +
                "FROM TRANSACTION AS t " +
                "WHERE t.DATE_TRANS BETWEEN TO_DATE('" + date_from + "', 'YYYY-MM-DD') AND TO_DATE('" + date_to + "', 'YYYY-MM-DD') " +
                "UNION " +
                "SELECT TO_CHAR(o.DATE_OUT, 'YYYY-MM-DD') AS DATE, " +
                "o.TITLE_OUT                            AS TITLE, " +
                "NULL                                   AS IN, " +
                "o.AMOUNT_OUT                           AS OUT " +
                "FROM OUTCOME                           AS o " +
                "WHERE o.DATE_OUT BETWEEN TO_DATE('" + date_from + "', 'YYYY-MM-DD') AND TO_DATE('" + date_to + "', 'YYYY-MM-DD') " +
                "ORDER BY DATE DESC, TITLE DESC LIMIT 10 OFFSET " + offset + ";";

        return get(sql);
    }

    @Override
    public double getTotalIn(String date_from, String date_to) {
        String sql = "SELECT SUM(TOTAL_TRANS) FROM TRANSACTION WHERE DATE_TRANS " +
                "BETWEEN TO_DATE('" + date_from + "', 'YYYY-MM-DD') AND TO_DATE('" + date_to + "', 'YYYY-MM-DD');";
        return getTotal(sql);
    }

    @Override
    public double getTotalOut(String date_from, String date_to) {
        String sql = "SELECT SUM(amount_out) FROM outcome WHERE date_out " +
                "BETWEEN TO_DATE('" + date_from + "', 'YYYY-MM-DD') AND TO_DATE('" + date_to + "', 'YYYY-MM-DD');";
        return getTotal(sql);
    }

    @Override
    public List<Ledger> paginate(int page) {
        String sql = "SELECT TO_CHAR(t.DATE_TRANS, 'YYYY-MM-DD')     AS DATE, " +
                "'Transaksi pembelian ' || t.ID_TRANS       AS TITLE, " +
                "t.TOTAL_TRANS                              AS IN, " +
                "NULL                                       AS OUT " +
                "FROM TRANSACTION AS t " +
                "WHERE t.DATE_TRANS BETWEEN DATE_TRUNC('month', now()) AND now() " +
                "UNION " +
                "SELECT TO_CHAR(o.DATE_OUT, 'YYYY-MM-DD') AS DATE, " +
                "o.TITLE_OUT                            AS TITLE, " +
                "NULL                                   AS IN, " +
                "o.AMOUNT_OUT                           AS OUT " +
                "FROM OUTCOME                           AS o " +
                "WHERE o.DATE_OUT BETWEEN DATE_TRUNC('month', now()) AND now() " +
                "ORDER BY DATE DESC, TITLE DESC LIMIT 10 OFFSET " + (page - 1) * 10 + ";";

        return get(sql);
    }

    @Override
    public int count(String date_from, String date_to) {
        String sql = "SELECT COUNT(*) FROM ( " +
                "SELECT t.id_trans FROM TRANSACTION AS t " +
                "WHERE t.DATE_TRANS BETWEEN TO_DATE('" + date_from + "', 'YYYY-MM-DD') AND TO_DATE('" + date_to + "', 'YYYY-MM-DD') " +
                "UNION " +
                "SELECT o.id_outcome " +
                "FROM OUTCOME                           AS o " +
                "WHERE o.DATE_OUT BETWEEN TO_DATE('" + date_from + "', 'YYYY-MM-DD') AND TO_DATE('" + date_to + "', 'YYYY-MM-DD') " +
                ") AS LEDGER;";
        int count = 0;
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
            this.disconnect();
        } catch (Exception e) {

        }
        return count;
    }

    private double getTotal(String sql) {
        double total = 0;
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    total = resultSet.getDouble("sum");
                }
            }
            this.disconnect();
        } catch (Exception e) {

        }
        return total;
    }

    private List<Ledger> get(String sql) {
        List<Ledger> list = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    Ledger ledger = new Ledger(
                            resultSet.getString("date"),
                            resultSet.getString("title"),
                            resultSet.getDouble("in"),
                            resultSet.getDouble("out")
                    );
                    list.add(ledger);
                }
            }
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#FETCH# something error : " + e.toString());
        }

        return list;
    }
}
