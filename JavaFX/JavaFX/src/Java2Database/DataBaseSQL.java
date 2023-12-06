package Java2Database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DataBaseSQL {

    private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = "jdbc:sqlserver://aei-sql2.avans.nl:1443;DatabaseName=CodeAcademyJGKJ;encrypt=false;";
    private static final String USER = "jgkj";
    private static final String PASSWORD = "jgkj2023";

    public static Connection createConnection() {
        try {
            // Laad de JDBC driver
            Class.forName(JDBC_DRIVER);

            // Maak connectie database
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connection made successfully!");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("An ERROR has occured, trying to connect to the database:");
            e.printStackTrace(); // Als er een exeptie optreed, mooi afhandelen.
            return null;
        }
    }

    public static void sendCommand(Connection connection, String command) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(command);
            System.out.println("Command has been executed.");
        }
    }
    
     public static ResultSet sendCommandReturn(Connection connection, String command) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery(command);
            System.out.println("Command has been executed.");
            return connection.createStatement().executeQuery(command);
        }
    }
}
    
  

