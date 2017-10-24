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
        String sql = "SELECT id_disc, id_emp, name_disc, percentage, " +
                "TO_CHAR(start_date, 'DD/MM/YYYY') AS start_date, " +
                "TO_CHAR(end_date,  'DD/MM/YYYY') AS end_date, status_disc FROM discount ORDER BY id_disc;";
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

    public void save(Discount discount) {
        String sql = "INSERT INTO discount(id_disc, id_emp, name_disc, percentage, start_date, end_date, status_disc) " +
                "VALUES (nextval('sec_disc') || ?,?,?,?,TO_DATE(?, 'DD/MM/YYYY'),TO_DATE(?, 'DD/MM/YYYY'),?);";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, discount.getId_disc());
            preparedStatement.setString(2, discount.getId_emp());
            preparedStatement.setString(3, discount.getName());
            preparedStatement.setFloat(4, discount.getPercentage());
            preparedStatement.setString(5, discount.getStart_date());
            preparedStatement.setString(6, discount.getEnd_date());
            preparedStatement.setString(7, discount.getStatus());
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#INSERT# something error : " + e.toString());
        }
    }
}
