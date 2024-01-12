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
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
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
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
        return dbConnection;
    }

    public static void sendCommand(Connection connection, String command) throws SQLException {
        try ( Statement statement = connection.createStatement()) {
            statement.executeUpdate(command);
        }
    }

    public static ResultSet sendCommandReturn(Connection connection, String command) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(command);
        return resultSet;

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

}
