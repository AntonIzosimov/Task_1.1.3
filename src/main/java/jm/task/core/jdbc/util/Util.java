package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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

    public static SessionFactory getSessionFactory() {
        Properties properties = new Properties();
        Configuration configuration = new Configuration();

        properties.setProperty(Environment.URL, URL_KEY);
        properties.setProperty(Environment.USER, USERNAME_KEY);
        properties.setProperty(Environment.PASS, PASSWORD_KEY);
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

        configuration.setProperties(properties).addAnnotatedClass(User.class);
        return configuration.buildSessionFactory();
    }
}
