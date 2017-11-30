package com.blibli.distro_pos.DAO.cashier;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.cashier.Transaction;
import com.blibli.distro_pos.Model.discount.Discount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO extends MyConnection implements BasicDAO<Transaction, String> {
    private static final String LIST = "transactionList";

    @Override
    public List<Transaction> getAll() {
        String sql = "SELECT id_trans, id_disc, id_emp, total_trans, customer_trans, status_trans, " +
                "TO_CHAR(date_trans, 'DD/MM/YYYY') AS date_trans, " +
                " status_disc FROM transaction ORDER BY id_trans DESC;";
        List<Transaction> transactionList = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            transactionList = getTransactionList(rs);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error :" + e.toString());
        }

        return transactionList;
    }

    @Override
    public Transaction getOne(String id) {
        String sql = "SELECT id_trans, id_disc, id_emp, total_trans, customer_trans, status_trans, " +
                "TO_CHAR(date_trans, 'DD/MM/YYYY') AS date_trans, " +
                " status_disc FROM transaction WHERE id_trans = '" + id + "';";
        Transaction transaction = new Transaction();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                System.out.println("get transaction\t:");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_trans"));
                    transaction = new Transaction(
                            rs.getString("id_trans"),
                            rs.getString("id_disc"),
                            rs.getString("id_emp"),
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
        String sql = "INSERT INTO transaction(id_disc, id_emp, customer_trans, total_trans, date_trans, status_trans) " +
                "VALUES (?,?,?,?,TO_DATE(?, 'YYYY-MM-DD'),?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, transaction.getId_disc());
            preparedStatement.setString(2, transaction.getId_emp());
            preparedStatement.setString(3, transaction.getCustomer_name());
            preparedStatement.setDouble(4, transaction.getTotal_trans());
            preparedStatement.setString(5, transaction.getDate());
            preparedStatement.setString(6, transaction.getStatus());
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#INSERT# something error : " + e.toString());
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
            System.out.println("#UPDATE# something error : " + e.toString());
        }
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void softDelete(String id) {
        String sql = "UPDATE transaction SET status_trans = 'Tidak Aktif'" +
                "WHERE id_trans = '" + id + "';";
        System.out.println(sql);
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#SOFTDELETE# something error : " + e.toString());
        }
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(id_trans) FROM transaction;";
        System.out.println(sql);
        int count = 0;
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null)  {
                while (resultSet.next())    {
                    count = resultSet.getInt("count");
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#COUNT# something error : " + e.toString());
        }
        return count;
    }

    @Override
    public List<Transaction> paginate(int page) {
        return null;
    }


    private List<Transaction> getTransactionList(ResultSet rs) {
        List<Transaction> transactionList = new ArrayList<>();
        try {
            if (rs != null) {
                System.out.println("getAll transaction\t:");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_trans"));
                    Transaction transaction = new Transaction(
                            rs.getString("id_trans"),
                            rs.getString("id_disc"),
                            rs.getString("id_emp"),
                            rs.getString("customer_trans"),
                            rs.getDouble("total_trans"),
                            rs.getString("date_trans"),
                            rs.getString("status_trans")
                    );
                    transactionList.add(transaction);
                }
            }
        } catch (Exception e) {
            System.out.println("Get Transaction List Problem : " + e.toString());
        }
        return transactionList;
    }
}
