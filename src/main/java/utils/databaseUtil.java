package utils;
import org.example.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class databaseUtil {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    static String configFilePath = "config.properties";
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            properties.load(fis);
        } catch (IOException e) {
            logger.severe("Error loading configuration file: " + e.getMessage());
        }

        String dbURL = properties.getProperty("dbURL");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        try {
            // Explicitly load the PostgreSQL JDBC driver class
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.severe("PostgreSQL JDBC driver not found. Make sure the driver JAR is in the classpath.");
            throw e;
        }

        return DriverManager.getConnection(dbURL, username, password);
    }
}
