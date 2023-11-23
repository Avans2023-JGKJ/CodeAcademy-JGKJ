
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProgram20 {

    public static void main(String[] args) {
        DatabaseProgram20 pro = new DatabaseProgram20();

        try {
            pro.addNaam("Jochem","firstName");

        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }

    }

    Connection createConnection() {
        String url = "jdbc:sqlserver://jochem_laptop;Database=CodeAcademyDatabase;IntegratedSecurity=true;encrypt=false;";
//        String url = "jdbc:sqlserver://192.168.178.109:1433;Database=CodeAcademyDatabase;encrypt=false;";
        // url is opgebouwd uit: jdbc:sqlserver://<naam van sqlserver>:<poort (default 1433)>;Database=<naam van database>;encrypt=false;
        String user = "admin";
        String password = "admin";
        
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");
            // Add your database operations here
            Statement st = con.createStatement();
            return DriverManager.getConnection(url, user, password);
//            Scanner first = new Scanner(System.in);
//            System.out.println("Typ naam, voor insert: ");
//            String name = first.nextLine();

//            String SQL = "INSERT INTO firstName VALUES ('" + name + "')";
//            st.execute(SQL);
//
//            System.out.println("Input succesvol");
//            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database:");
            e.printStackTrace();
            return null;
        }

    }

    public String getUrl() {
        return "jdbc:sqlserver://jochem_laptop;Database=CodeAcademyDatabase;IntegratedSecurity=true;encrypt=false;";
    }

    public void addNaam(String naam, String DatabaseTabel) {
        try {
            Connection con = createConnection();
            Statement st = con.createStatement();
            String SQL = "INSERT INTO "+DatabaseTabel+" VALUES ('" + naam + "')";
            st.execute(SQL);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProgram20.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
