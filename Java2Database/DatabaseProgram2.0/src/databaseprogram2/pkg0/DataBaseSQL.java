package databaseprogram2.pkg0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            System.out.println("An Error has occured, tyring to connect to the database:");
            e.printStackTrace(); // Als er een exeptie optreed, mooi afhandelen.
            return null;
        }
    }
}

   
    
    

