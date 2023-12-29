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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DialogCertificaatFXMLController implements Initializable {
    
    @FXML
    TextField CertificaatId;

    @FXML
    TextField CertificaatNaamMedewerker;

    @FXML
    TextField CertificaatBeoordeling;

    @FXML
    TextField CertificaatInschrijfId;
    
    
    public static Connection dbConnection;


    //       @FXML
    void FinishButtonCreateCertificaatClicked() {
    try {
        DataBaseSQL.sendCommand(dbConnection, "INSERT INTO Certificaat (beoordeling, medewerkerNaam, inschrijfId) VALUES('"
                + CertificaatBeoordeling.getText()
                + "',  '" + CertificaatNaamMedewerker.getText()
                + "',  '" + CertificaatInschrijfId.getText() + "')");
        System.out.println(CertificaatInschrijfId.getText());
    }
    catch (SQLException ex) {
        CursistFXMLController.ErrorAlert("Er is iets fout gegaan!", "SQL Fout!");
        System.out.println(ex);
    } catch (Exception e) {
        System.out.println(e);
    }
}


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        dbConnection = DataBaseSQL.createConnection(dbConnection);
    
    }


    private void loadData() {
        if((DataShare.getInstance().getBeoordeling()) != -1){
        CertificaatBeoordeling.setText(String.valueOf(DataShare.getInstance().getBeoordeling()));
        }
        CertificaatNaamMedewerker.setText(String.valueOf(DataShare.getInstance().getMedeWerkerNaam()));
        CertificaatInschrijfId.setText(String.valueOf(DataShare.getInstance().getInschrijfId()));
    }
}