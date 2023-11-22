
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseProgram20 {

    public static void main(String[] args) {
        DatabaseProgram20 pro = new DatabaseProgram20();

        try {

            pro.createConnection();

        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }

    }

    void createConnection() {
        String url = "jdbc:sqlserver://jochem_laptop;Database=CodeAcademyDatabase;IntegratedSecurity=true;encrypt=false;";
        String user = "admin";
        String password = "admin";

        try ( Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");
            // Add your database operations here
            Statement st = con.createStatement();
            Scanner first = new Scanner(System.in);
            System.out.println("Typ naam, voor insert: ");
            String name = first.nextLine();

            String SQL = "INSERT INTO firstName VALUES ('" + name + "')";
            st.execute(SQL);

            System.out.println("Input succesvol");
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database:");
            e.printStackTrace();
        }

    }

    public String getUrl() {
        return "jdbc:sqlserver://jochem_laptop;Database=CodeAcademyDatabase;IntegratedSecurity=true;encrypt=false;";

    }
}
