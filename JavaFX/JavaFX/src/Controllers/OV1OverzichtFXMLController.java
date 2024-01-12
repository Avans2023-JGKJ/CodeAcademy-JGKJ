/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.OverzichtFXMLController;
import java.sql.ResultSet;
import Java2Database.DataBaseSQL;
import Java2Database.DataShare;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

/**
 *
 * @author gijsv
 */
public class OV1OverzichtFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    Label displayUserNameLabel;

    @FXML
    private Label WelcomeLabelHomeScreen;

    @FXML
    private Label percentageDisplayM;

    @FXML
    private Label percentageDisplayV;

    @FXML
    private ProgressBar ProgressVrouw;

    @FXML
    private ProgressBar ProgressMan;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calcPercentageVrouwCertificaat();
        calcPercentageManCertificaat();
        WelcomeLabelHomeScreen.setText("Hallo, " + DataShare.getInstance().getUsername());
    }

    void calcPercentageVrouwCertificaat() {
        try {
            // TODO
            ResultSet VTOT = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT COUNT(inschrijfId) AS VTOT\n"
                    + "FROM Inschrijven\n"
                    + "WHERE email IN(SELECT email\n"
                    + "			FROM Cursist\n"
                    + "			WHERE geslacht = 'v')");
            VTOT.next();
            float vrouwTot = VTOT.getInt("VTOT");
            ResultSet VCERT = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT COUNT(CertificaatId) AS VCERT\n"
                    + "FROM Certificaat\n"
                    + "WHERE inschrijfId IN(SELECT inschrijfId\n"
                    + "					FROM Inschrijven\n"
                    + "					WHERE email IN(SELECT email\n"
                    + "								FROM Cursist\n"
                    + "								WHERE geslacht = 'v'))");

            VCERT.next();
            float vrouwCert = VCERT.getInt("VCERT");
            float CertPercentage = (vrouwCert / vrouwTot) * 100;
            DecimalFormat perct = new DecimalFormat("#.##");
            percentageDisplayV.setText(perct.format(CertPercentage) + "%");
            ProgressVrouw.setProgress(CertPercentage / 100);
        } catch (SQLException ex) {
            Logger.getLogger(OV1OverzichtFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void calcPercentageManCertificaat() {
        try {
            // TODO
            ResultSet MTOT = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT COUNT(inschrijfId) AS MTOT\n"
                    + "FROM Inschrijven\n"
                    + "WHERE email IN(SELECT email\n"
                    + "			FROM Cursist\n"
                    + "			WHERE geslacht = 'm')");
            MTOT.next();
            float manTot = MTOT.getInt("MTOT");
            ResultSet MCERT = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT COUNT(CertificaatId) AS MCERT\n"
                    + "FROM Certificaat\n"
                    + "WHERE inschrijfId IN(SELECT inschrijfId\n"
                    + "					FROM Inschrijven\n"
                    + "					WHERE email IN(SELECT email\n"
                    + "								FROM Cursist\n"
                    + "								WHERE geslacht = 'm'))");

            MCERT.next();
            float manCert = MCERT.getInt("MCERT");
            if (manTot == 0) {
                percentageDisplayM.setText(0 + "%");
                ProgressMan.setProgress(0);
            } else {
                float CertPercentage = (manCert / manTot) * 100;
                 DecimalFormat perct = new DecimalFormat("#.##");
                percentageDisplayM.setText(perct.format(CertPercentage) + "%");
                ProgressMan.setProgress(CertPercentage / 100);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OV1OverzichtFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void OverzichtBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/OverzichtScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
