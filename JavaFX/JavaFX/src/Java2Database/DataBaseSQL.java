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

    public static Connection createConnection(Connection dbConnection) {
        if (dbConnection == null) {
            try {
                // Load the JDBC driver
                Class.forName(JDBC_DRIVER);

                // Create database connection
                dbConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                System.out.println("Connection made successfully!");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("An ERROR has occurred while trying to connect to the database:");
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
        return dbConnection;
    }

    public static void sendCommand(Connection connection, String command) throws SQLException {
        if (isSafe(command)) {
            try ( Statement statement = connection.createStatement()) {
                statement.executeUpdate(command);
                System.out.println("Command has been executed.");
            }
        } else {
            System.out.println("This Command is not accepted!");
        }
    }

    public static ResultSet sendCommandReturn(Connection connection, String command) throws SQLException {
        if (isSafe(command)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            System.out.println("Command has been executed.");
            return resultSet;
        } else {
            System.out.println("This Command is not accepted!");
            return null;
        }
    }

    public static void closeStatementAndResultSet(Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isSafe(String str) {
        return true;

    }

}
