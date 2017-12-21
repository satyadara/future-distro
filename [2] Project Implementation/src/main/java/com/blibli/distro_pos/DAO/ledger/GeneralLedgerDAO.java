package com.blibli.distro_pos.DAO.ledger;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.ledger.Ledger;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GeneralLedgerDAO extends MyConnection {

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
        List<Ledger> list = get(sql);
        return list;
    }

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
        List<Ledger> list = get(sql);
        return list;
    }

    private List<Ledger> get(String sql) {
        List<Ledger> list = new ArrayList<>();
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
            System.out.println("#FETCH# something error : " + e.toString());
        }

        return list;
    }
}
