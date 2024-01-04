/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Java2Database.DataBaseSQL;
import Java2Database.DataShare;
import Objects.Status;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class DialogCursistHomeScreenFXMLController implements Initializable {

    @FXML
    private ComboBox<String> InschrijvenNaamCursusBox;

    @FXML
    private ObservableList<String> naamCursusList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT naamCursus "
                    + "FROM Cursus");
            while (rs.next()) {
                naamCursusList.add(rs.getString("naamCursus"));
            }
            ResultSet rs1 = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT c.naamCursus "
                    + "FROM Cursus c JOIN Inschrijven i ON i.naamCursus = c.naamCursus "
                    + "JOIN Cursist cu ON cu.email = i.email "
                    + "JOIN Persoon p ON p.Email = cu.email "
                    + "WHERE p.UserName = '" + DataShare.getInstance().getUsername() + "'");

            while (rs1.next()) {
                naamCursusList.remove(naamCursusList.indexOf(rs1.getString("naamCursus")));
            }
            InschrijvenNaamCursusBox.setItems(naamCursusList);

        } catch (SQLException ex) {
            Logger.getLogger(DialogCursistHomeScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    boolean ValidateAndSignUp() {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT naamCursus FROM Cursus");
            ResultSet rs1 = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT c.email FROM Cursist c JOIN Persoon p ON p.Email = c.email WHERE UserName = '"+DataShare.getInstance().getUsername()+"'");
            if (InschrijvenNaamCursusBox.getValue() != null || !InschrijvenNaamCursusBox.getValue().isEmpty()) {
                if (rs.next()) {
                    rs1.next();
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Inschrijven (email, naamCursus, datum, totaalVoortgang) VALUES "
                            + "('" + rs1.getString("email") + "', '" + InschrijvenNaamCursusBox.getValue() + "', '" + LocalDate.now() + "', '0')");
                    System.out.println("Inschrijven succesvol afgerond.");

                    ResultSet rs2 = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT inschrijfId FROM Inschrijven ORDER BY inschrijfId DESC");
                    rs2.next();
                    DataShare.getInstance().setInschrijfId(rs2.getInt("inschrijfId"));
                    DataShare.getInstance().setCursistEmail(rs1.getString("email"));
                    DataShare.getInstance().setNaamCursus(InschrijvenNaamCursusBox.getValue());

                    createAccessories(DataShare.getInstance().getInschrijfId());
                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogCursistHomeScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    private void createAccessories(int inschrijfId) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT contentItemId FROM contentItems WHERE naamCursus = '" + DataShare.getInstance().getNaamCursus() + "'");
            while (rs.next()) {
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Voortgang (inschrijfId, naamCursus, contentItemId, voortgangsPercentage) VALUES ("
                        + "'" + inschrijfId + "', '"
                        + DataShare.getInstance().getNaamCursus() + "', '" + rs.getString("contentItemId") + "', '0')");
            }
            System.out.println("Benodigde Tupels aangemaakt...");
        } catch (SQLException ex) {
            Logger.getLogger(DialogCursistHomeScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
