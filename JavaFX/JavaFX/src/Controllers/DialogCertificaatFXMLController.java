package Controllers;

import Java2Database.DataShare;

import Java2Database.DataShare;
import Java2Database.DataBaseSQL;
import Validatie.DataValidatie;
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
    boolean ValidateAndCreateCertificaat() {
        System.out.println("punt 1");
        if (DataValidatie.InsertCertificaatValid(
                CertificaatBeoordeling.getText(),
                CertificaatNaamMedewerker.getText(),
                inschrijfIdSelectBox.getValue()
        )) {
            System.out.println("2");
            try {
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Certificaat (beoordeling, medewerkerNaam, inschrijfId) VALUES('"
                        + CertificaatBeoordeling.getText()
                        + "',  '" + CertificaatNaamMedewerker.getText()
                        + "',  '" + inschrijfIdSelectBox.getValue() + "')");
                System.out.println("Certificaat succesful created");
                return true;
            } catch (SQLException ex) {
                System.out.println(ex);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("FALSE CODE");
        return false;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ResultSet rs = null;
        try {
            loadData();
            dbConnection = DataBaseSQL.createConnection(dbConnection);
            rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT inschrijfId \n"
                    + "FROM inschrijven \n"
                    + "WHERE totaalVoortgang = '100' AND  inschrijfid NOT IN(SELECT inschrijfId\n"
                    + "FROM Certificaat)");
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
}
