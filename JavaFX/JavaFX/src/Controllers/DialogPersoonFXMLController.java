/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Validatie.DataValidatie;
import Validatie.Error;
import Objects.Rol;
import Java2Database.DataBaseSQL;
import Java2Database.DataShare;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 *
 * @author gijsv
 */
public class DialogPersoonFXMLController implements Initializable{

    @FXML
    TextField PersoonRoleInput;

    @FXML
    TextField PersoonUserNameInput;

    @FXML
    TextField PersoonPassWordInput;

    @FXML
    TextField PersoonEmailInput;

    boolean ValidateAndUpdatePersoon() {
        if(DataValidatie.UpdatePersoonValid(
        PersoonRoleInput.getText(),
        PersoonUserNameInput.getText(),
        PersoonPassWordInput.getText(),
        PersoonEmailInput.getText()
        ))
        try {
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                    "UPDATE Persoon SET"
                    + " Rol = '" + PersoonRoleInput.getText()
                    + "', UserName = '" + PersoonUserNameInput.getText()
                    + "', PassWord = '" + PersoonPassWordInput.getText()
                    + "', Email = '" + PersoonEmailInput.getText()
                    + "' WHERE UserName = '" + DataShare.getInstance().getPersoonUserName() + "'");
            System.out.println("Persoon succesfully updated");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DialogPersoonFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    boolean ValidateAndCreatePersoon() {
         if(DataValidatie.InsertPersoonValid(
        PersoonRoleInput.getText(),
        PersoonUserNameInput.getText(),
        PersoonPassWordInput.getText(),
        PersoonEmailInput.getText()
        ))
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
    private void loadData() {
        PersoonRoleInput.setText(String.valueOf(DataShare.getInstance().getRol()));
        PersoonUserNameInput.setText(String.valueOf(DataShare.getInstance().getPersoonUserName()));
        PersoonPassWordInput.setText(String.valueOf(DataShare.getInstance().getPassWord()));
        PersoonEmailInput.setText(String.valueOf(DataShare.getInstance().getEmail()));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
    }
}
