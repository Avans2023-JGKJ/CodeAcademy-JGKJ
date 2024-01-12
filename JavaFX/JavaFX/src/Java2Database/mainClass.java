package Java2Database;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class mainClass {

    public static void main(String args[]) throws SQLException {
        DataBaseSQL db = new DataBaseSQL();
        PreparedStatement p = null;
        ResultSet rs = null;
        

            
        db.sendCommand(db.createConnection(), "");

    }
}

