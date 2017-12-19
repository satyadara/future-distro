package com.blibli.distro_pos.DAO.outcome;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.outcome.Outcome;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OutcomeDAO extends MyConnection implements BasicDAO<Outcome, String> {
    public static final String LIST = "outcomeList";
    @Override
    public List<Outcome> getAll() {
        String sql = "SELECT id_outcome, username, title_out, amount_out, quantity_out," +
                "TO_CHAR(date_out, 'DD/MM/YYYY') AS date_out, desc_out, status FROM outcome ORDER BY id_outcome;";
        List<Outcome> outcomeList = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            outcomeList = getOutcomeList(rs);
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }
        this.disconnect();

        return outcomeList;
    }

    @Override
    public Outcome getOne(String id) {
        String sql = "SELECT id_outcome, username, title_out, amount_out, quantity_out," +
                "TO_CHAR(date_out, 'YYYY-MM-DD') AS date_out, desc_out, status FROM outcome " +
                "WHERE id_outcome = ?;";
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
                            rs.getString("username"),
                            rs.getString("title_out"),
                            rs.getDouble("amount_out"),
                            rs.getInt("quantity_out"),
                            rs.getString("date_out"),
                            rs.getString("desc_out"),
                            rs.getString("status")
                    );
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#GET# something error : " + e.toString());
        }

        return outcome;
    }

    @Override
    public void save(Outcome outcome) {
        String sql = "INSERT INTO outcome(username, amount_out, quantity_out, date_out, desc_out, title_out) " +
                "VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, outcome.getUsername());
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
        String sql = "UPDATE outcome SET title_out = ?, amount_out = ?, quantity_out = ?, date_out = TO_DATE(?, 'YYYY-MM-DD')   , desc_out = ? " +
                " WHERE id_outcome = ?;";
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
        String sql = "DELETE FROM outcome " +
                "WHERE id_outcome = ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#DELETE# something error : " + e.toString());
        }
    }

    @Override
    public void softDelete(String id) {
        String sql = "UPDATE outcome SET status = 'Tidak Aktif' " +
                "WHERE id_outcome = ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#SOFT.DELETE# something error : " + e.toString());
        }
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(id_outcome) FROM outcome;";
        int count = 0;
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    count = rs.getInt("count");
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#COUNT# something error : " + e.toString());
        }
        return count;
    }

    @Override
    public List<Outcome> paginate(int page) {
        String sql = "SELECT id_outcome, username, title_out, amount_out, quantity_out," +
                "TO_CHAR(date_out, 'DD/MM/YYYY') AS date_out, desc_out, status FROM outcome ORDER BY id_outcome " +
                "LIMIT 10 OFFSET ?;";
        List<Outcome> outcomeList = new ArrayList<>();
        try {
            this.connect();
            int offset = (page - 1) * 10;
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            outcomeList = getOutcomeList(rs);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error : " + e.toString());
        }

        return outcomeList;
    }

    public void setActive(String id) {
        String sql = "UPDATE outcome SET status = 'Aktif' " +
                "WHERE id_outcome = ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#SET ACTIVE# something error : " + e.toString());
        }
    }

    public List<Outcome> getOutcomeList(ResultSet rs) {
        List<Outcome> outcomeList = new ArrayList<>();
        try {
            if (rs != null) {
                while (rs.next()) {
                    Outcome outcome = new Outcome(
                            rs.getString("id_outcome"),
                            rs.getString("username"),
                            rs.getString("title_out"),
                            rs.getDouble("amount_out"),
                            rs.getInt("quantity_out"),
                            rs.getString("date_out"),
                            rs.getString("desc_out"),
                            rs.getString("status")
                    );
                    outcomeList.add(outcome);
                }
            }
        } catch (Exception e) {
            System.out.println("Get Outcome List Problem : " + e.toString());
        }
        return outcomeList;
    }

    public Map<String, Object> search(String key, int page) {
        String sql = "SELECT id_outcome, username, title_out, amount_out, quantity_out," +
                "TO_CHAR(date_out, 'DD/MM/YYYY') AS date_out, desc_out, status FROM outcome " +
                "WHERE LOWER(title_out) LIKE '%'||?||'%'  ORDER BY id_outcome LIMIT 10 OFFSET ?;";
        String sql_counter = "SELECT COUNT(id_outcome) FROM outcome WHERE LOWER(title_out) LIKE '%'||?||'%';";
        Map<String, Object> map = new HashMap<>();
        List<Outcome> outcomeList;
        int count = 0;
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            int offset = (page - 1) * 10;
            preparedStatement.setString(1, key);
            preparedStatement.setInt(2, offset);
            ResultSet rs = preparedStatement.executeQuery();
            outcomeList = getOutcomeList(rs);

            preparedStatement = this.con.prepareStatement(sql_counter);
            preparedStatement.setString(1, key);
            rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    count = rs.getInt("count");
                }
            }
            map.put(LIST, outcomeList);
            map.put("count", count);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error :" + e.toString());
        }

        return map;
    }
}
