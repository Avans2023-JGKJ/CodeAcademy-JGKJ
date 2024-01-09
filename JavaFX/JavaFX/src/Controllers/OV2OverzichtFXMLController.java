/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class OV2OverzichtFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label WelcomeLabelHomeScreen;

    @FXML
    private ComboBox<String> SelecteerNaamCursusBox;

    @FXML
    private ObservableList<String> naamCursusList = FXCollections.observableArrayList();

    @FXML
    private Label percentageDisplayCursus;

    @FXML
    private Label messageDisplayCursus;

    @FXML
    private ProgressBar ProgressCursus;

    void selecteerNaamCursus() {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT naamCursus "
                    + "FROM Cursus");
            while (rs.next()) {
                naamCursusList.add(rs.getString("naamCursus"));
            }
            SelecteerNaamCursusBox.setItems(naamCursusList);

        } catch (SQLException ex) {
            Logger.getLogger(OV2OverzichtFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void calcVoortgangsPercentageCursus(ActionEvent event) {
        selecteerNaamCursus();
        try {

            float i = 0;
            float sum = 0;
            ResultSet VoortgangCursus = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT voortgangsPercentage AS VrtPerct FROM Voortgang WHERE naamCursus = '" + SelecteerNaamCursusBox.getValue() + "'");
            while (VoortgangCursus.next()) {
                sum = sum + VoortgangCursus.getInt("VrtPerct");
                i++;
            }
            float TotPerct = sum / i;
            percentageDisplayCursus.setText((TotPerct) + "%");
            ProgressCursus.setProgress(TotPerct / 100);
            System.out.println(sum);
            System.out.println(i);
            System.out.println(TotPerct);
            setmessageDisplayCursus();
        } catch (SQLException ex) {
            Logger.getLogger(OV1OverzichtFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setmessageDisplayCursus() {
        selecteerNaamCursus();
        messageDisplayCursus.setText("Dit is het voortgangspercentage van alle modules van alle accounts die ingeschreven zijn voor de cursus " + SelecteerNaamCursusBox.getValue());
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        selecteerNaamCursus();
        WelcomeLabelHomeScreen.setText("Hallo, " + DataShare.getInstance().getUsername());
    }
}
