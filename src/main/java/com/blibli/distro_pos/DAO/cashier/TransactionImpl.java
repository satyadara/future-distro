package com.blibli.distro_pos.DAO.cashier;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.DAO.cashier.Interface.TransactionInterface;
import com.blibli.distro_pos.Model.cashier.Transaction;
import com.blibli.distro_pos.Model.item.Item;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionImpl extends MyConnection implements TransactionInterface {
    private static final String LIST = "transactionList";

    @Override
    public List<Transaction> getAll() {
        String sql = "SELECT id_trans, id_disc, username, total_trans, customer_trans, status_trans, " +
                "TO_CHAR(date_trans, 'DD/MM/YYYY') AS date_trans " +
                " FROM transaction ORDER BY id_trans DESC;";
        List<Transaction> transactionList = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            transactionList = getTransactionList(rs);
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#FETCH# something error :" + e.toString());
        }

        return transactionList;
    }

    @Override
    public Transaction getOne(String id) {
        String sql = "SELECT id_trans, id_disc, username, total_trans, customer_trans, status_trans, " +
                "TO_CHAR(date_trans, 'DD/MM/YYYY') AS date_trans " +
                " FROM transaction WHERE id_trans = '" + id + "';";
        Transaction transaction = new Transaction();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
//                System.out.println("get transaction\t:");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_trans"));
                    transaction = new Transaction(
                            rs.getString("id_trans"),
                            rs.getString("id_disc"),
                            rs.getString("username"),
                            rs.getString("customer_trans"),
                            rs.getDouble("total_trans"),
                            rs.getString("date_trans"),
                            rs.getString("status_trans")
                    );
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error :" + e.toString());
        }

        return transaction;
    }

    @Override
    public void save(Transaction transaction) {
        String sql = "INSERT INTO transaction(id_trans, id_disc, username, customer_trans, total_trans, date_trans, status_trans) " +
                "VALUES (?,?,?,?,?,TO_DATE(?, 'YYYY-MM-DD'),?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, transaction.getId_trans());
            preparedStatement.setString(2, transaction.getId_disc());
            preparedStatement.setString(3, transaction.getUsername());
            preparedStatement.setString(4, transaction.getCustomer_name());
            preparedStatement.setDouble(5, transaction.getTotal_trans());
            preparedStatement.setString(6, transaction.getDate());
            preparedStatement.setString(7, transaction.getStatus());
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#INSERT# something error : " + e.toString());
        }
    }

    @Override
    public void update(Transaction transaction) {
        String sql = "UPDATE transaction SET id_disc = ?, customer_trans = ?, total_trans = ?, status_trans = ? " +
                "WHERE id_trans = '" + transaction.getId_trans() + "';";
        System.out.println(sql);
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, transaction.getId_disc());
            preparedStatement.setString(2, transaction.getCustomer_name());
            preparedStatement.setDouble(3, transaction.getTotal_trans());
            preparedStatement.setString(4, transaction.getStatus());
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#UPDATE# something error : " + e.toString());
        }
    }

    public List<Transaction> paginate(int page) {
        String sql = "SELECT id_trans, id_disc, username, total_trans, customer_trans, status_trans, " +
                "TO_CHAR(date_trans, 'DD/MM/YYYY') AS date_trans " +
                " FROM transaction ORDER BY id_trans DESC LIMIT 10 OFFSET ?;";
        List<Transaction> list = new ArrayList<>();
        this.disconnect();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            int offset = (page - 1) * 10;
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            list = getTransactionList(rs);
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("something error :" + e.toString());
        }

        return list;
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM transaction;";
        int result = 0;
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                result = rs.getInt("count");
            }
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#COUNT# something error : " + e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> search(String key, int page) {
        String sql = "SELECT id_trans, id_disc, username, total_trans, customer_trans, status_trans, " +
                "TO_CHAR(date_trans, 'DD/MM/YYYY') AS date_trans " +
                " FROM transaction WHERE id_trans LIKE '%'||?||'%' ORDER BY id_trans DESC LIMIT 10 OFFSET ?;";
        String sql_counter = "SELECT COUNT(*) FROM transaction WHERE id_trans LIKE '%'||?||'%';";
        Map<String, Object> map = new HashMap<>();
        List<Transaction> list;
        int count = 0;
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            int offset = (page - 1) * 10;
            preparedStatement.setString(1, key);
            preparedStatement.setInt(2, offset);
            ResultSet rs = preparedStatement.executeQuery();
            list = getTransactionList(rs);

            preparedStatement = this.con.prepareStatement(sql_counter);
            preparedStatement.setString(1, key);
            rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    count = rs.getInt("count");
                }
            }
            map.put(LIST, list);
            map.put("count", count);
            this.disconnect();
        } catch (Exception e) {
//            System.out.println("#FETCH# something error :" + e.toString());
        }

        return map;
    }

    @Override
    public List<Transaction> getTransactionList(ResultSet rs) {
        List<Transaction> transactionList = new ArrayList<>();
        try {
            if (rs != null) {
//                System.out.println("getAll transaction\t:");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_trans"));
                    Transaction transaction = new Transaction(
                            rs.getString("id_trans"),
                            rs.getString("id_disc"),
                            rs.getString("username"),
                            rs.getString("customer_trans"),
                            rs.getDouble("total_trans"),
                            rs.getString("date_trans"),
                            rs.getString("status_trans")
                    );
                    transactionList.add(transaction);
                }
            }
        } catch (Exception e) {
//            System.out.println("Get Transaction List Problem : " + e.toString());
        }
        return transactionList;
    }

    @Override
    public String getTransID() {
        String sql = "SELECT nextval('sec_trans');";
        String id = "";
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    id = resultSet.getString("nextval");
                }
            }
            this.disconnect();
        } catch (Exception e) {

        }
        return id;
    }

    @Override
    public String getListString() {
        return LIST;
    }

}
