package com.blibli.distro_pos.DAO;

import com.blibli.distro_pos.Model.Role;
import com.blibli.distro_pos.Model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {

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

            System.out.println(e.toString());
        }
    }

    //Memasukkan data user
    public static int insertUser(User user, Role role) {

        int status = 0;

        String sql = "INSERT INTO users(username,password,enabled) VALUES (?,?,?);";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setBoolean(3, user.isEnabled());

//            preparedStatement.setString(1, role.getUsername());
//            preparedStatement.setString(2, role.getRole());

            status = preparedStatement.executeUpdate();
            insertUserRole(user.getUsername(), role.getRole());

            System.out.println("Finished inserting user");
        }
        catch (Exception e) {

            System.out.println("ERROR : "+ e.getMessage());
        }

        return status;
    }

    //Memasukkan data user roles
    public static int insertUserRole(String username, String role) {

        int status = 0;

        String sql = "INSERT INTO user_roles(username, role) VALUES (?,?)";

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, role);

            status = preparedStatement.executeUpdate();

            System.out.println("Finished inserting user role");
        }
        catch (Exception e) {

            System.out.println(e.toString());
        }

        return status;
    }

    //Menampilkan semua user
    public static List<User> getAllUser() {

        List<User> userList = new ArrayList<User>();

        String sql = "select users.username, user_roles.role from users, user_roles where users.enabled = ? AND users.username=user_roles.username;";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setBoolean(1, true);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setRole(resultSet.getString("role"));

                userList.add(user);
            }
        }
        catch (Exception e) {

            System.out.println(e.toString());
        }

        return userList;
    }

    // TODO: Mengedit user
    public static  int editUser(User user) {

        return 0;

    }

    //Menghapus user
    public static int deleteUser(String username) {

//        int status = 0;
//
//        String sql = "DELETE FROM users WHERE username = ?;";
//
//        try {
//
//            Connection connection = getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            preparedStatement.setString(1, user.getUsername());
//
//            status = preparedStatement.executeUpdate();
//        }
//        catch (Exception e) {
//
//            System.out.println(e.toString());
//        }
//
//        return status;
//    }

        int status = 0;

        String sql = "UPDATE users SET enabled = ? WHERE username = ?";

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, username);

//            preparedStatement.setInt(1, user.getId());
//            preparedStatement.setString(1, user.getUsername());

            status = preparedStatement.executeUpdate();
        }
        catch (Exception e) {

            System.out.println(e.toString());
        }

        return status;
    }
}
