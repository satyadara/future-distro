package com.blibli.distro_pos.DAO;

import com.blibli.distro_pos.Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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

        try {
            String sql = "CREATE TABLE users(" +
                    "   username varchar(20) NOT NULL," +
                    "   password varchar(20) NOT NULL," +
                    "   enabled boolean NOT NULL DEFAULT FALSE," +
                    "   primary key(username)" +
                    ");";

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

        try {
            String sql = "create table user_roles (" +
                    "  user_role_id SERIAL PRIMARY KEY," +
                    "  username varchar(20) NOT NULL," +
                    "  role varchar(20) NOT NULL," +
                    "  UNIQUE (username,role)," +
                    "  FOREIGN KEY (username) REFERENCES users (username)" +
                    ");";

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();

            System.out.println("Finished creating user_ role table");
        }
        catch (Exception e) {

            System.out.println(e.toString());
        }
    }

    //Memasukkan data user
    public static int insertUser(User user) {

        int status = 0;

        try {
            String sql = "INSERT INTO users" +
                    "(username, password, enabled)" +
                    "VALUES(?,?,?)";

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            status = preparedStatement.executeUpdate();

            System.out.println("Finished inserting user");
        }
        catch (Exception e) {

            System.out.println(e.toString());
        }

        return status;
    }
}
