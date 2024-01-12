package Controllers;

import Java2Database.DataBaseSQL;
import Java2Database.DataShare;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class OV8OverzichtFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ComboBox<String> SelecteerNaamCursusBox;

    @FXML
    private Label WelcomeLabelHomeScreen;
    @FXML
    private Label CertificatenDisplay;

    @FXML
    private ObservableList<String> naamCursusList = FXCollections.observableArrayList();

    //Methode selecteerd de naamCursus vanuit database
    void selecteerNaamCursus() {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT COUNT(*) AS CertPerCur ,naamCursus\n"
                    + "FROM Inschrijven\n"
                    + "JOIN Certificaat ON Certificaat.inschrijfId = Inschrijven.inschrijfId\n"
                    + "GROUP BY naamCursus\n"
                    + "ORDER BY CertPerCur DESC");

            while (rs.next()) {
                naamCursusList.add(rs.getString("naamCursus"));
            }
            SelecteerNaamCursusBox.setItems(naamCursusList);

        } catch (SQLException ex) {
            Logger.getLogger(OV8OverzichtFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML //Methode zet het aantal certificaten 
    void SetAantalCertificaten(ActionEvent event) {
        try {
            ResultSet Certpercur = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT COUNT(*) AS CertPerCur ,naamCursus\n"
                    + "FROM Inschrijven\n"
                    + "JOIN Certificaat ON Certificaat.inschrijfId = Inschrijven.inschrijfId\n"
                    + "WHERE naamCursus = '" + SelecteerNaamCursusBox.getValue() + "'\n"
                    + "GROUP BY naamCursus\n"
                    + "ORDER BY CertPerCur DESC");
            while (Certpercur.next()) {
                CertificatenDisplay.setText(String.valueOf(Certpercur.getInt("CertPerCur")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OV8OverzichtFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override //Methode zet de WelcomeLabel en spreekt ander Methode in
    public void initialize(URL arg0, ResourceBundle arg1) {
        WelcomeLabelHomeScreen.setText("Hallo, " + DataShare.getInstance().getUsername());
        selecteerNaamCursus();
    }

    @FXML //Methode zorgt dat je terug kunt naar OverzichtScreen
    void OverzichtBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/OverzichtScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
