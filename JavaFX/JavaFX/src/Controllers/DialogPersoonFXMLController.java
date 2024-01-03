/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import static Controllers.DialogCertificaatFXMLController.dbConnection;
import static Controllers.DialogCursistFXMLController.cursistDbConnection;
import Java2Database.DataBaseSQL;
import Java2Database.DataShare;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 *
 * @author gijsv
 */
public class DialogPersoonFXMLController {

    @FXML
    TextField PersoonRoleInput;

    @FXML
    TextField PersoonUserNameInput;

    @FXML
    TextField PersoonPassWordInput;

    @FXML
    TextField PersoonEmailInput;

    boolean FinishButtonUpdatePersoonClicked() {
        try {
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                    "UPDATE Persoon SET"
                    + " Rol = '" + PersoonRoleInput.getText()
                    + "', UserName = '" + PersoonUserNameInput.getText()
                    + "', PassWord = '" + PersoonPassWordInput.getText()
                    + "', Email = '" + PersoonEmailInput.getText()
                    + "'");
            System.out.println("Cursist succesfully updated");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DialogPersoonFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    boolean FinishButtonCreatePersoonClicked() {
        try {
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Persoon (Rol, UserName, PassWord, Email) VALUES('"
                    + PersoonRoleInput.getText()
                    + "',  '" + PersoonUserNameInput.getText()
                    + "',  '" + PersoonPassWordInput.getText()
                    + "',  '" + PersoonEmailInput.getText() + "')");
            System.out.println("speciaal voor jochem");
        } catch (SQLException ex) {
            CursistFXMLController.ErrorAlert("Er is iets fout gegaan!", "SQL Fout!");
            System.out.println(ex);
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }
}
