package databaseprogram2.pkg0;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class mainClass {

    public static void main(String args[]) throws SQLException {
        DataBaseSQL db = new DataBaseSQL();
        PreparedStatement p = null;
        ResultSet rs = null;

        p = db.createConnection().prepareStatement("SELECT * FROM Cursus");
        rs = p.executeQuery();

        while (rs.next()) {

            String naamCursus = rs.getString("naamCursus");
            int aantalContentItems = rs.getInt("aantalContentItems");
            String onderwerp = rs.getString("onderwerp");
            String introductieTekst = rs.getString("introductieTekst");
            String niveau = rs.getString("niveau");
            System.out.println(naamCursus + "\t\t" + aantalContentItems
                    + "\t\t" + onderwerp + "\t\t" + introductieTekst + "\t\t" + niveau);
        }

//        db.sendCommand(db.createConnection(), "SELECT * FROM Cursus");
//        db.sendCommand(db.createConnection(), genInsert("Cursus", "test", "2", "test", "test", "testtest"));
    }

    // Genereer makkelijk, de INSERT INTO statement voor 3 t/m 8 values)
    private static String genInsert(String db_table, String value1, String value2, String value3) {
        return "INSERT INTO " + db_table + " VALUES ('" + value1 + "','" + value2 + "','" + value3 + "');";
    }

    private static String genInsert(String db_table, String value1, String value2, String value3, String value4) {
        return "INSERT INTO " + db_table + " VALUES ('" + value1 + "','" + value2 + "','" + value3 + "','" + value4 + "');";
    }

    private static String genInsert(String db_table, String value1, String value2, String value3, String value4, String value5) {
        return "INSERT INTO " + db_table + " VALUES ('" + value1 + "','" + value2 + "','" + value3 + "','" + value4 + "','" + value5 + "');";
    }

    private static String genInsert(String db_table, String value1, String value2, String value3, String value4, String value5, String value6) {
        return "INSERT INTO " + db_table + " VALUES ('" + value1 + "','" + value2 + "','" + value3 + "','" + value4 + "','" + value5 + "','" + value6 + "');";
    }

    private static String genInsert(String db_table, String value1, String value2, String value3, String value4, String value5, String value6, String value7) {
        return "INSERT INTO " + db_table + " VALUES ('" + value1 + "','" + value2 + "','" + value3 + "','" + value4 + "','" + value5 + "','" + value6 + "','" + value7 + "');";
    }

    private static String genInsert(String db_table, String value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8) {
        return "INSERT INTO " + db_table + " VALUES ('" + value1 + "','" + value2 + "','" + value3 + "','" + value4 + "','" + value5 + "','" + value6 + "','" + value7 + "','" + value8 + "');";
    }

    // Genereer 
}
