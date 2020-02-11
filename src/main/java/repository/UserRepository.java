package repository;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dto.UserDto;
import entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRepository {

    private final String URL = "jdbc:mysql://localhost:3306/user?useSSL=false";
    private final String SQL_USER = "root";
    private final String PASSWORD = "3smileduck3";
    private final String DRIVER = "com.mysql.jdbc.Driver";

    public UserRepository() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
           log.error(String.format("ERROR connect driver in class", e.getMessage()));
        }
    }

    public void post(User user) {

        try (Connection conn = DriverManager.getConnection(URL, SQL_USER, PASSWORD)) {

            String quer = String.format("INSERT INTO users (name) VALUES ('%s')", user.getName());
            Statement statement = conn.createStatement();
            statement.executeUpdate("ALTER TABLE users AUTO_INCREMENT = 1;");
            statement.executeUpdate(quer);

        } catch (SQLException e) {
            log.error(String.format("ERROR INPUT data: %s", e.getMessage()));
        }
    }

    public UserDto get(int id) {
        UserDto user = null;
        try (Connection conn = DriverManager.getConnection(URL, SQL_USER, PASSWORD)) {

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users");

            while (rs.next()) {
                if (rs.getInt("id") == id) {
                    user = new UserDto(rs.getInt("id"), rs.getString("name"));
                    break;
                }
            }
        } catch (SQLException e) {
            log.error(String.format("ERROR GET data: %s", e.getMessage()));
        }
        return user;
    }

    public void put(Integer id, User user) {

        try (Connection conn = DriverManager.getConnection(URL, SQL_USER, PASSWORD)) {

            Statement statement = conn.createStatement();
            statement.executeUpdate(String.format("UPDATE users SET name='%s' WHERE id = '%d';", user.getName(), id));

        } catch (SQLException e) {
            log.error(String.format("ERROR UPDATE data: %s", e.getMessage()));
        }

    }

    public void delete(int id) {
        try (Connection conn = DriverManager.getConnection(URL, SQL_USER, PASSWORD)) {
            
            Statement statement = conn.createStatement();
            statement.executeUpdate(String.format("DELETE FROM users WHERE id =%d;", id));
            
        } catch (SQLException e) {
            log.error(String.format("ERROR DELETE data: %s", e.getMessage()));
        }
    }

}
