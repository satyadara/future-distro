package com.blibli.distro_pos.DAO.discount;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.discount.Discount;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DiscountDAO extends MyConnection {

    public List<Discount> getAll() {
        String sql = "SELECT id_disc, id_emp, name_disc, description, percentage, " +
                "TO_CHAR(start_date, 'DD/MM/YYYY') AS start_date, " +
                "TO_CHAR(end_date,  'DD/MM/YYYY') AS end_date, status_disc FROM discount ORDER DESC BY id_disc;";
        List<Discount> discountList = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                System.out.println("getAll discount\t:");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_disc"));
                    Discount discount = new Discount(
                            rs.getString("id_disc"),
                            rs.getString("id_emp"),
                            rs.getString("name_disc"),
                            rs.getString("description"),
                            rs.getFloat("percentage"),
                            rs.getString("start_date"),
                            rs.getString("end_date"),
                            rs.getString("status_disc")
                    );
                    discountList.add(discount);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error :" + e.toString());
        }

        return discountList;
    }

    public Discount getOne(String id) {
        String sql = "SELECT id_disc, id_emp, name_disc, description, percentage, " +
                "TO_CHAR(start_date, 'YYYY-MM-DD') AS start_date, " +
                "TO_CHAR(end_date,  'YYYY-MM-DD') AS end_date, status_disc " +
                "FROM discount WHERE id_disc = '" + id +
                "' ORDER BY id_disc;";
        Discount discount = new Discount();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                System.out.println("get discount\t:");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_disc"));
                    discount = new Discount(
                            rs.getString("id_disc"),
                            rs.getString("id_emp"),
                            rs.getString("name_disc"),
                            rs.getString("description"),
                            rs.getFloat("percentage"),
                            rs.getString("start_date"),
                            rs.getString("end_date"),
                            rs.getString("status_disc")
                    );
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#GETONE# something error :" + e.toString());
        }

        return discount;
    }

    public void save(Discount discount) {
        String sql = "INSERT INTO discount(id_disc, id_emp, name_disc, description, percentage, start_date, end_date, status_disc) " +
                "VALUES (nextval('sec_disc') || ?,?,?,?,?,TO_DATE(?, 'YYYY-MM-DD'),TO_DATE(?, 'YYYY-MM-DD'),?);";
        System.out.println(discount.getId_disc());
        System.out.println(discount.getStart_date());
        System.out.println(discount.getEnd_date());
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, discount.getId_disc());
            preparedStatement.setString(2, discount.getId_emp());
            preparedStatement.setString(3, discount.getName());
            preparedStatement.setString(4, discount.getDesc());
            preparedStatement.setFloat(5, discount.getPercentage());
            preparedStatement.setString(6, discount.getStart_date());
            preparedStatement.setString(7, discount.getEnd_date());
            preparedStatement.setString(8, discount.getStatus());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#INSERT# something error : " + e.toString());
        }
    }

    public void update(Discount discount) {
        String sql = "UPDATE discount SET name_disc = ?, description = ?, " +
                "percentage = ?, start_date = TO_DATE(?, 'YYYY-MM-DD'), end_date = TO_DATE(?, 'YYYY-MM-DD') " +
                "WHERE id_disc = ?;";
        System.out.println(discount.getId_disc());
        System.out.println(sql);
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, discount.getName());
            preparedStatement.setString(2, discount.getDesc());
            preparedStatement.setFloat(3, discount.getPercentage());
            preparedStatement.setString(4, discount.getStart_date());
            preparedStatement.setString(5, discount.getEnd_date());
            preparedStatement.setString(6, discount.getId_disc());
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#UPDATE# something error : " + e.toString());
        }
    }

    public int count() {
        String sql = "SELECT COUNT(id_disc) FROM discount;";
        int count = 0;
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    count = rs.getInt("count");
                }
            }
            preparedStatement.executeQuery();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#COUNT# something error : " + e.toString());
        }

        return count;
    }

    public List<Discount> paginate(int page) {
        String sql = "SELECT id_disc, id_emp, name_disc, description, percentage, " +
                "TO_CHAR(start_date, 'DD/MM/YYYY') AS start_date, " +
                "TO_CHAR(end_date,  'DD/MM/YYYY') AS end_date, status_disc FROM discount ORDER DESC BY id_disc;";
        List<Discount> discountList = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                System.out.println("get discount page " + page + "\t:");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_disc"));
                    Discount discount = new Discount(
                            rs.getString("id_disc"),
                            rs.getString("id_emp"),
                            rs.getString("name_disc"),
                            rs.getString("description"),
                            rs.getFloat("percentage"),
                            rs.getString("start_date"),
                            rs.getString("end_date"),
                            rs.getString("status_disc")
                    );
                    discountList.add(discount);
                }
            }
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error :" + e.toString());
        }

        return discountList;
    }
}
