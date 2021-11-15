package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exception.DaoException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users(
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name TEXT,
                lastName TEXT,
                age TINYINT);
                """;

        try (var connection = Util.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (var connection = Util.getConnection();
             var prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.execute();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                INSERT INTO users(name, lastName, age)
                VALUES (?, ?, ?)
                """;

        try (var connection = Util.getConnection();
             var prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setByte(3, age);
            prepareStatement.execute();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }


    }

    public void removeUserById(long id) {
        String sql = """
                DELETE FROM users
                WHERE id = ?
                """;
        try (var connection = Util.getConnection();
             var prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setLong(1, id);
            prepareStatement.execute();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }

    }

    public List<User> getAllUsers() {
        String sql = """
                SELECT
                name,
                lastName,
                age
                FROM users
                """;

        List<User> users = new ArrayList<>();

        try (var connection = Util.getConnection();
             var prepareStatement = connection.prepareStatement(sql)) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                users.add(getUser(resultSet));
            }
            return users;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";

        try (var connection = Util.getConnection();
             var prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.execute();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getString("name"),
                resultSet.getString("lastName"),
                resultSet.getByte("age")
        );
    }
}
