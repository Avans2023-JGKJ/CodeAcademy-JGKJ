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
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    Label PersoonEmailLabel;

    @FXML
    ComboBox<Rol> PersoonRoleInput;

    @FXML
    ComboBox<String> PersoonEmailComboBox;

    private static boolean isPersoonUpdate;
    private String email;

    private ObservableList<String> CursistEmailList = FXCollections.observableArrayList();

    boolean ValidateAndUpdatePersoon() {
        if (PersoonEmailInput.getText() == null || PersoonEmailInput.getText().isEmpty()) {
            email = String.valueOf(PersoonEmailComboBox.getValue()).toLowerCase();
        } else if (PersoonEmailInput.getText() != null || !PersoonEmailInput.getText().isEmpty()) {
            email = PersoonEmailInput.getText().toLowerCase();
        }
        if ((Rol.ADMIN.equals(DataShare.getInstance().getRol()) && DataValidatie.UpdatePersoonValid(
                PersoonRoleInput.getValue().toString(),
                PersoonUserNameInput.getText(),
                PersoonPassWordInput.getText(),
                PersoonEmailComboBox.getValue(),
                email
        )) || (Rol.CURSIST.equals(DataShare.getInstance().getRol()) && DataValidatie.UpdatePersoonValid(
                PersoonRoleInput.getValue().toString(),
                PersoonUserNameInput.getText(),
                PersoonPassWordInput.getText(),
                PersoonEmailInput.getText(),
                email
        ))) {
            try {
                if (Rol.ADMIN.equals(PersoonRoleInput.getValue())) {
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                            "UPDATE Persoon SET"
                            + " Rol = 'ADMIN'"
                            + ", UserName = '" + PersoonUserNameInput.getText()
                            + "', PassWord = '" + PersoonPassWordInput.getText()
                            + "', Email = null"
                            + " WHERE UserName = '" + DataShare.getInstance().getPersoonUserName() + "'");
                    return true;
                } else if (Rol.CURSIST.equals(PersoonRoleInput.getValue())) {

                    if (PersoonEmailInput.getText() != null || !PersoonEmailInput.getText().isEmpty()) {
                        DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                                "UPDATE Cursist SET email = '" + email + "' WHERE email = '" + DataShare.getInstance().getEmail() + "'");
                    }

                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                            "UPDATE Persoon SET"
                            + " Rol = 'CURSIST'"
                            + ", UserName = '" + PersoonUserNameInput.getText()
                            + "', PassWord = '" + PersoonPassWordInput.getText()
                            + "', Email = '" + email
                            + "' WHERE UserName = '" + DataShare.getInstance().getPersoonUserName() + "'");

                    return true;
                }

                return false;
            } catch (SQLException ex) {
                Logger.getLogger(DialogPersoonFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    boolean ValidateAndCreatePersoon() {
        if (DataValidatie.InsertPersoonValid(
                PersoonRoleInput.getValue().toString(),
                PersoonUserNameInput.getText(),
                PersoonPassWordInput.getText(),
                PersoonEmailInput.getText(),
                false
        ))
        try {
            if (Rol.ADMIN.equals(PersoonRoleInput.getValue())) {
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Persoon (Rol, UserName, PassWord) VALUES('"
                        + PersoonRoleInput.getValue()
                        + "',  '" + PersoonUserNameInput.getText()
                        + "',  '" + PersoonPassWordInput.getText() + "')");
                return true;
            } else if (Rol.CURSIST.equals(PersoonRoleInput.getValue())) {

                if (PersoonEmailInput.getText() == null || PersoonEmailInput.getText().isEmpty()) {
                    email = String.valueOf(PersoonEmailComboBox.getValue()).toLowerCase();
                } else if (PersoonEmailInput.getText() != null || !PersoonEmailInput.getText().isEmpty()) {
                    email = PersoonEmailInput.getText().toLowerCase();
                }
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Persoon (Rol, UserName, PassWord, Email) VALUES('"
                        + PersoonRoleInput.getValue()
                        + "',  '" + PersoonUserNameInput.getText()
                        + "',  '" + PersoonPassWordInput.getText()
                        + "',  '" + email + "')");
                return true;
            }
            return false;

        } catch (SQLException ex) {
            CursistFXMLController.ErrorAlert("Er is iets fout gegaan!", "SQL Fout!");
            System.out.println(ex);
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    private void loadData() {
        PersoonEmailComboBox.setItems(CursistEmailList);
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
        if (DataShare.getInstance().getRol() == Rol.valueOf("ADMIN")) {
            PersoonRoleInput.setValue(Rol.ADMIN);
        } else if (DataShare.getInstance().getRol() == Rol.valueOf("CURSIST")) {
            PersoonRoleInput.setValue(Rol.CURSIST);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT email FROM Cursist");
            while (rs.next()) {
                CursistEmailList.add(rs.getString("email"));
            }
            PersoonEmailComboBox.setItems(CursistEmailList);
            if (DataShare.getInstance().getUsername() != null) {
                loadData();
            }
            PersoonRoleInput.setItems(FXCollections.observableArrayList(Rol.values()));
            PersoonEmailLabel.setVisible(false);
            PersoonEmailInput.setVisible(false);
            PersoonEmailComboBox.setVisible(false);
            if (isPersoonUpdate && (Rol.CURSIST.equals(PersoonRoleInput.getValue()))) {
                PersoonEmailLabel.setVisible(true);
                PersoonEmailInput.setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogPersoonFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void checkRolHide(ActionEvent event) {
        if (Rol.ADMIN.equals(PersoonRoleInput.getValue())) {
            PersoonEmailLabel.setVisible(false);
            PersoonEmailInput.setVisible(false);
            PersoonEmailComboBox.setVisible(false);

        } else if (Rol.CURSIST.equals(PersoonRoleInput.getValue())) {
            PersoonEmailLabel.setVisible(true);
            if (isPersoonUpdate) {
                PersoonEmailInput.setVisible(true);

                if (isPersoonUpdate && (Rol.ADMIN.equals(DataShare.getInstance().getRol()))) {
                    PersoonEmailInput.setVisible(false);
                    PersoonEmailComboBox.setVisible(true);
                }
            } else {
                PersoonEmailInput.setVisible(false);
                PersoonEmailComboBox.setVisible(true);
            }

        }
    }

    public static void setIsUpdatePersoon(boolean x) {
        isPersoonUpdate = x;
    }
}
