package com.blibli.distro_pos.DAO.discount;

import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.discount.Discount;
import com.blibli.distro_pos.Model.item.Item;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DiscountDAO extends MyConnection {

    public List<Discount> getAll() {
        String sql = "SELECT id_disc, id_emp, name_disc, percentage, " +
                "TO_CHAR(start_date, 'DD/MM/YYYY') AS 'start_date', " +
                "TO_CHAR(end_date, ) 'DD/MM/YYYY' AS 'end_date', status_disc FROM discount ORDER BY id_disc;";
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
        } catch (
                Exception e)

        {
            System.out.println("something error :" + e.toString());
        }

        return discountList;
    }
}
