package com.blibli.distro_pos.DAO;

import com.blibli.distro_pos.Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    //Membuat koneksi
    public static Connection getConnection() {

        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5433/raditia",
                    "postgres",
                    "password"
            );

            System.out.println("Database opened successfully");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return connection;
    }

    //Membuat table users
    public static void createTableUser() {

        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "   username varchar(20) NOT NULL," +
                "   password varchar(20) NOT NULL," +
                "   enabled boolean NOT NULL DEFAULT FALSE," +
                "   primary key(username)" +
                ");";

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();

            System.out.println("Finished creating user table");
        } catch (Exception e) {

            System.out.println(e.toString());
        }
    }

    //Membuat table users_roles
    public static void createTableUserRole() {

        String sql = "create table IF NOT EXISTS user_roles (" +
                "  user_role_id SERIAL PRIMARY KEY," +
                "  username varchar(20) NOT NULL," +
                "  role varchar(20) NOT NULL," +
                "  UNIQUE (username,role)," +
                "  FOREIGN KEY (username) REFERENCES users (username)" +
                ");";

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();

            System.out.println("Finished creating user_role table");
        }
        catch (Exception e) {
            System.out.println("Finished creating user_ role table");
        }
    }

    //Memasukkan data user
    public static int insertUser(User user) {

        int status = 0;

        String sql = "INSERT INTO users(username,password,enabled) VALUES (?,?,?);";

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.isEnabled());

//            preparedStatement.setString(1, userRole.getUsername());
//            preparedStatement.setString(2, userRole.getRole());

            status = preparedStatement.executeUpdate();

            System.out.println("Finished inserting user");
        } catch (Exception e) {

            System.out.println(e.toString());
        }

        return status;
    }

    //Menampilkan semua user
    public static List<User> getAllUser() {

        List<User> userList = new ArrayList<User>();

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT username, password FROM users"
            );

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                userList.add(user);
            }
        }
        catch (Exception e) {

            System.out.println(e.toString());
        }

        return userList;
    }

    //Menghapus user
    public static int deleteUser(User user) {

        int status = 0;

        String sql = "DELETE FROM users, user_roles WHERE username = ?";

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getUsername());

            status = preparedStatement.executeUpdate();
        }
        catch (Exception e) {

            System.out.println(e.toString());
        }

        return status;
    }
}
