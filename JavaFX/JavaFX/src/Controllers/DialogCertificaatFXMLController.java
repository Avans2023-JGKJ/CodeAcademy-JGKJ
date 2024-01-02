package Controllers;

import Java2Database.DataShare;

import Java2Database.DataShare;
import Java2Database.DataBaseSQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

public class DialogCertificaatFXMLController implements Initializable {

    @FXML
    TextField CertificaatId;

    @FXML
    TextField CertificaatNaamMedewerker;

    @FXML
    TextField CertificaatBeoordeling;

    @FXML
    TextField CertificaatInschrijfId;

    @FXML
    private ComboBox<String> inschrijfIdSelectBox;

    @FXML
    private ObservableList<String> inschrijfIdList = FXCollections.observableArrayList();

    public static Connection dbConnection;

    //       @FXML
    void FinishButtonCreateCertificaatClicked() {
        try {   
            if (Integer.valueOf(CertificaatBeoordeling.getText()) >= 0 && Integer.valueOf(CertificaatBeoordeling.getText()) <= 10) {
                DataBaseSQL.sendCommand(dbConnection, "INSERT INTO Certificaat (beoordeling, medewerkerNaam, inschrijfId) VALUES('"
                        + CertificaatBeoordeling.getText()
                        + "',  '" + CertificaatNaamMedewerker.getText()
                        + "',  '" + inschrijfIdSelectBox.getValue() + "')");
            } else {
                showAlert();
            }

        } catch (SQLException ex) {
            CursistFXMLController.ErrorAlert("Er is iets fout gegaan!", "SQL Fout!");
            System.out.println(ex);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ResultSet rs = null;
        try {
            loadData();
            dbConnection = DataBaseSQL.createConnection(dbConnection);
            rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT inschrijfId FROM inschrijven");
            while (rs.next()) {
                inschrijfIdList.add(rs.getString("inschrijfId"));
            }
            inschrijfIdSelectBox.setItems(inschrijfIdList);
            CertificaatNaamMedewerker.setText(DataShare.getInstance().getUsername());
        } catch (SQLException ex) {
            Logger.getLogger(DialogCertificaatFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogCertificaatFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void loadData() {
        if ((DataShare.getInstance().getBeoordeling()) != -1) {
            CertificaatBeoordeling.setText(String.valueOf(DataShare.getInstance().getBeoordeling()));
        }
        CertificaatNaamMedewerker.setText(String.valueOf(DataShare.getInstance().getMedeWerkerNaam()));
        if (DataShare.getInstance().getInschrijfId() != 0) {
            CertificaatInschrijfId.setText(String.valueOf(DataShare.getInstance().getInschrijfId()));
        }
    }

    void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Number incorrect");
        alert.setHeaderText("The value entered is not a valid score!");
        alert.showAndWait();

    }
}
