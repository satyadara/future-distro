package com.blibli.distro_pos.DAO.discount;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.DAO.MyConnection;
import com.blibli.distro_pos.Model.discount.Discount;
import org.omg.CORBA.Any;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

@Repository
public class DiscountDAO extends MyConnection implements BasicDAO<Discount, String> {
    public static final String LIST = "discountList";

    @Override
    public List<Discount> getAll() {
        String sql = "SELECT id_disc, username, name_disc, description, percentage, " +
                "TO_CHAR(start_date, 'DD/MM/YYYY') AS start_date, " +
                "TO_CHAR(end_date,  'DD/MM/YYYY') AS end_date, status_disc FROM discount ORDER BY id_disc DESC;";
        List<Discount> discountList = new ArrayList<>();
        try {
            this.connect();
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            discountList = getDiscountList(rs);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error :" + e.toString());
        }

        return discountList;
    }

    @Override
    public Discount getOne(String id) {
        String sql = "SELECT id_disc, username, name_disc, description, percentage, " +
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
                            rs.getString("username"),
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

    @Override
    public void save(Discount discount) {
        String sql = "INSERT INTO discount(username, name_disc, description, percentage, start_date, end_date, status_disc) " +
                "VALUES (?,?,?,?,TO_DATE(?, 'YYYY-MM-DD'),TO_DATE(?, 'YYYY-MM-DD'),?);";
        System.out.println(discount.getId_disc());
        System.out.println(discount.getStart_date());
        System.out.println(discount.getEnd_date());
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, discount.getUsername());
            preparedStatement.setString(2, discount.getName());
            preparedStatement.setString(3, discount.getDesc());
            preparedStatement.setFloat(4, discount.getPercentage());
            preparedStatement.setString(5, discount.getStart_date());
            preparedStatement.setString(6, discount.getEnd_date());
            preparedStatement.setString(7, discount.getStatus());
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#INSERT# something error : " + e.toString());
        }
    }

    @Override
    public void update(Discount discount) {
        String sql = "UPDATE discount SET name_disc = ?, description = ?, " +
                "percentage = ?, start_date = TO_DATE(?, 'YYYY-MM-DD'), end_date = TO_DATE(?, 'YYYY-MM-DD') " +
                "WHERE id_disc = ?;";
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, discount.getName());
            preparedStatement.setString(2, discount.getDesc());
            preparedStatement.setFloat(3, discount.getPercentage());
            preparedStatement.setString(4, discount.getStart_date());
            preparedStatement.setString(5, discount.getEnd_date());
            preparedStatement.setString(6, discount.getId_disc());
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#UPDATE# something error : " + e.toString());
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM discount " +
                "WHERE id_disc = ?;";
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
        String sql = "UPDATE discount SET status_disc = 'Tidak Aktif'" +
                " WHERE id_disc = ?;";
        System.out.println(sql);
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#SOFT DELETE# something error : " + e.toString());
        }
    }

    @Override
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
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#COUNT# something error : " + e.toString());
        }

        return count;
    }

    @Override
    public List<Discount> paginate(int page) {
        String sql = "SELECT id_disc, username, name_disc, description, percentage, " +
                "TO_CHAR(start_date, 'DD/MM/YYYY') AS start_date, " +
                "TO_CHAR(end_date,  'DD/MM/YYYY') AS end_date, status_disc FROM discount ORDER BY id_disc DESC LIMIT 10 OFFSET ?;";
        List<Discount> discountList = new ArrayList<>();
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            int offset = (page - 1) * 10;
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            discountList = getDiscountList(rs);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error :" + e.toString());
        }

        return discountList;
    }

    public void setActive(String id) {
        String sql = "UPDATE discount SET status_disc = 'Aktif' " +
                "WHERE id_disc = ?;";
        System.out.println(sql);
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

    public Map<String, Object> search(String key, int page) {
        String sql = "SELECT id_disc, username, name_disc, description, percentage, " +
                "TO_CHAR(start_date, 'DD/MM/YYYY') AS start_date, " +
                "TO_CHAR(end_date,  'DD/MM/YYYY') AS end_date, status_disc FROM discount " +
                "WHERE name_disc LIKE '%'||?||'%' ORDER BY id_disc DESC LIMIT 10 OFFSET ?;";
        String sql_counter = "SELECT COUNT(id_disc) FROM discount WHERE name_disc LIKE '%'||?||'%';";
        Map<String, Object> map = new HashMap<>();
        List<Discount> discountList;
        int count = 0;
        try {
            this.connect();
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            int offset = (page - 1) * 10;
            preparedStatement.setString(1, key);
            preparedStatement.setInt(2, offset);
            ResultSet rs = preparedStatement.executeQuery();
            discountList = getDiscountList(rs);

            preparedStatement = this.con.prepareStatement(sql_counter);
            preparedStatement.setString(1, key);
            rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    count = rs.getInt("count");
                }
            }

            map.put(LIST, discountList);
            map.put("count", count);
            this.disconnect();
        } catch (Exception e) {
            System.out.println("#FETCH# something error :" + e.toString());
        }

        return map;
    }

    private List<Discount> getDiscountList(ResultSet rs) {
        List<Discount> discountList = new ArrayList<>();
        try {
            if (rs != null) {
                System.out.println("getAll discount\t:");
                while (rs.next()) {
                    System.out.println("\t" + rs.getString("id_disc"));
                    Discount discount = new Discount(
                            rs.getString("id_disc"),
                            rs.getString("username"),
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
        } catch (Exception e) {
            System.out.println("Get Discount List Problem : " + e.toString());
        }
        return discountList;
    }
}
