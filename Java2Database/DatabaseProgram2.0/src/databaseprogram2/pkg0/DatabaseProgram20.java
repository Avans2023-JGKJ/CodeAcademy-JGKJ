
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProgram20 {
private String url = "jdbc:sqlserver://192.168.178.109:1433;Database=CodeAcademyDatabase;encrypt=false;";
private String user = "admin";
private String password = "admin";

    public static void main(String[] args) {
        DatabaseProgram20 pro = new DatabaseProgram20();

        try {
            pro.addNaam("Jochemtb","firstName");

        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }

    }

    Connection createConnection() {
//        String url = "jdbc:sqlserver://jochem_laptop;Database=CodeAcademyDatabase;IntegratedSecurity=true;encrypt=false;";
//        String url = "jdbc:sqlserver://192.168.178.109:1433;Database=CodeAcademyDatabase;encrypt=false;";
        // url is opgebouwd uit: jdbc:sqlserver://<naam van sqlserver>:<poort (default 1433)>;Database=<naam van database>;encrypt=false;
//        String user = "admin";
//        String password = "admin";
        
        try (Connection con = DriverManager.getConnection(this.url, this.user, this.password)) {
            System.out.println("Connection made successfully!");
            return DriverManager.getConnection(url, user, password);
            // Add your database operations here
//            Statement st = con.createStatement();
//            Scanner first = new Scanner(System.in);
//            System.out.println("Typ naam, voor insert: ");
//            String name = first.nextLine();

//            String SQL = "INSERT INTO firstName VALUES ('" + name + "')";
//            st.execute(SQL);
//
//            System.out.println("Input succesvol");
//            con.close();
        } catch (SQLException e) {
            System.out.println("An Error has occured, tyring to connect to the database:");
            e.printStackTrace();
            return null;
        }

    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
    

    public void addNaam(String naam, String DatabaseTabel) {
        try {
            Connection con = createConnection();
            Statement st = con.createStatement();
            String SQL = "INSERT INTO "+DatabaseTabel+" VALUES ('" + naam + "')";
            st.execute(SQL);
            System.out.println("Addnaam succesvol...");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProgram20.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeNaam(String naam, String DatabaseTabel){
        try {
            Connection con = createConnection();
            Statement st = con.createStatement();
            String SQL = "DELETE FROM "+DatabaseTabel+" WHERE Naam= ('" + naam + "')";
            st.execute(SQL);
            System.out.println("Addnaam succesvol...");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProgram20.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
