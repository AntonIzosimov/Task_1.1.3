package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class Util {
    private static final String URL_KEY = "jdbc:mysql://localhost:3306/kata";
    private static final String USERNAME_KEY = "root";
    private static final String PASSWORD_KEY = "1000fuckingtimes";

    private Util() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL_KEY, USERNAME_KEY, PASSWORD_KEY);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
