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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author gijsv
 */
public class DialogPersoonFXMLController implements Initializable {

    @FXML
    TextField PersoonUserNameInput;

    @FXML
    TextField PersoonPassWordInput;

    @FXML
    TextField PersoonEmailInput;

    @FXML
    ComboBox<Rol> PersoonRoleInput;

    boolean ValidateAndUpdatePersoon() {
        if (DataValidatie.UpdatePersoonValid(
                PersoonRoleInput.getValue().toString(),
                PersoonUserNameInput.getText(),
                PersoonPassWordInput.getText(),
                PersoonEmailInput.getText()
        ))
        try {
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                    "UPDATE Persoon SET"
                    + " Rol = '" + PersoonRoleInput.getValue()
                    + "', UserName = '" + PersoonUserNameInput.getText()
                    + "', PassWord = '" + PersoonPassWordInput.getText()
                    + "', Email = '" + PersoonEmailInput.getText()
                    + "' WHERE UserName = '" + DataShare.getInstance().getPersoonUserName() + "'");
            System.out.println("Persoon succesfully updated");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DialogPersoonFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    boolean ValidateAndCreatePersoon() {
        if (DataValidatie.InsertPersoonValid(
                PersoonRoleInput.getValue().toString(),
                PersoonUserNameInput.getText(),
                PersoonPassWordInput.getText(),
                PersoonEmailInput.getText()
        ))
        try {
            if (PersoonRoleInput.getValue().equals("ADMIN")) {
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Persoon (Rol, UserName, PassWord) VALUES('"
                        + PersoonRoleInput.getValue()
                        + "',  '" + PersoonUserNameInput.getText()
                        + "',  '" + PersoonPassWordInput.getText() + "')");
            } else {
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Persoon (Rol, UserName, PassWord, Email) VALUES('"
                        + PersoonRoleInput.getValue()
                        + "',  '" + PersoonUserNameInput.getText()
                        + "',  '" + PersoonPassWordInput.getText()
                        + "',  '" + PersoonEmailInput.getText().toLowerCase() + "')");
            }

        } catch (SQLException ex) {
            CursistFXMLController.ErrorAlert("Er is iets fout gegaan!", "SQL Fout!");
            System.out.println(ex);
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    private void loadData() {
        PersoonRoleInput.setValue((DataShare.getInstance().getRol()));
        if (DataShare.getInstance().getPersoonUserName() != null) {
            PersoonUserNameInput.setText(String.valueOf(DataShare.getInstance().getPersoonUserName()));
        }
        if (DataShare.getInstance().getPassWord() != null) {
            PersoonPassWordInput.setText(String.valueOf(DataShare.getInstance().getPassWord()));
        }
        if (DataShare.getInstance().getEmail() != null) {
            PersoonEmailInput.setText(String.valueOf(DataShare.getInstance().getEmail()));
        }
        if (DataShare.getInstance().getRol() == Rol.valueOf("Admin")) {
            PersoonRoleInput.setValue(Rol.ADMIN);
        } else if (DataShare.getInstance().getRol() == Rol.valueOf("Cursist")) {
            PersoonRoleInput.setValue(Rol.CURSIST);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if (DataShare.getInstance().getUsername() != null) {
            loadData();
        }
        PersoonRoleInput.setItems(FXCollections.observableArrayList(Rol.values()));
    }
}
