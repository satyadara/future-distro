package com.blibli.distro_pos.DAO.outcome;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.outcome.Outcome;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OutcomeDAO extends MyConnection implements BasicDAO<Outcome, String> {

    @Override
    public List<Outcome> getAll() {
        String sql = "SELECT id_outcome, id_emp, title_out, amount_out, quantity_out," +
                "TO_CHAR(date_out, 'DD/MM/YYYY') AS date_out, desc_out FROM outcome ORDER BY id_outcome;";
        List<Outcome> outcomeList = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    Outcome outcome = new Outcome(
                            rs.getString("id_outcome"),
                            rs.getString("id_emp"),
                            rs.getString("title_out"),
                            rs.getDouble("amount_out"),
                            rs.getInt("quantity_out"),
                            rs.getString("date_out"),
                            rs.getString("desc_out")
                    );
                    outcomeList.add(outcome);
                }
            }
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        this.disconnect();

        return outcomeList;
    }

    @Override
    public Outcome getOne(String id) {
        String sql = "SELECT id_outcome, id_emp, title_out, amount_out, quantity_out," +
                "TO_CHAR(date_out, 'DD/MM/YYYY') AS date_out, desc_out FROM outcome " +
                "WHERE id_outcome = ? ORDER BY id_outcome;";
        Outcome outcome = new Outcome();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    outcome = new Outcome(
                            rs.getString("id_outcome"),
                            rs.getString("id_emp"),
                            rs.getString("title_out"),
                            rs.getDouble("amount_out"),
                            rs.getInt("quantity_out"),
                            rs.getString("date_out"),
                            rs.getString("desc_out")
                    );
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }

        return outcome;
    }

    @Override
    public void save(Outcome outcome) {
        String sql = "INSERT INTO outcome(id_emp, amount_out, quantity_out, date_out, desc_out, title_out) " +
                "VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, outcome.getId_emp());
            preparedStatement.setDouble(2, outcome.getAmount());
            preparedStatement.setInt(3, outcome.getQuantity());
            preparedStatement.setString(4, outcome.getDate());
            preparedStatement.setString(5, outcome.getDesc());
            preparedStatement.setString(6, outcome.getTitle());
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#SAVE# something error : " + e.toString());
        }
    }

    @Override
    public void update(Outcome outcome) {
        String sql = "UPDATE outcome SET title_out = ?, amount_out = ?, quantity_out = ?, date_out = ?, desc_out = ? " +
                "WHERE id_outcome = ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, outcome.getTitle());
            preparedStatement.setDouble(2, outcome.getAmount());
            preparedStatement.setInt(3, outcome.getQuantity());
            preparedStatement.setString(4, outcome.getDate());
            preparedStatement.setString(5, outcome.getDesc());
            preparedStatement.setString(6, outcome.getId_outcome());
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
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(id_outcome) FROM outcome;";
        int count = 0;
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            statement.execute(sql);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#COUNT# something error : " + e.toString());
        }
        return count;
    }

    @Override
    public List<Outcome> paginate(int page) {
        String sql = "SELECT id_outcome, id_emp, title_out, amount_out, quantity_out," +
                "TO_CHAR(date_out, 'DD/MM/YYYY') AS date_out, desc_out FROM outcome ORDER BY id_outcome " +
                "LIMIT 10 OFFSET ?;";
        List<Outcome> outcomeList = new ArrayList<>();
        try {
            this.connect();
            int offset = (page - 1) * 10;
            PreparedStatement preparedStatement= this.con.prepareStatement(sql);
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Outcome outcome = new Outcome(
                            rs.getString("id_outcome"),
                            rs.getString("id_emp"),
                            rs.getString("title_out"),
                            rs.getDouble("amount_out"),
                            rs.getInt("quantity_out"),
                            rs.getString("date_out"),
                            rs.getString("desc_out")
                    );
                    outcomeList.add(outcome);
                }
            }
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        this.disconnect();

        return outcomeList;
    }
}
