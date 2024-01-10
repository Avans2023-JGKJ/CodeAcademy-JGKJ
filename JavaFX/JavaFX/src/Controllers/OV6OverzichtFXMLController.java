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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author gijsv
 */
public class OV6OverzichtFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label WelcomeLabelHomeScreen;

    @FXML
    private Label firstCursusDisplay;

    @FXML
    private Label secondCursusDisplay;

    @FXML
    private Label thirdCursusDisplay;

    void DisplayCertificaatPerCursus() throws SQLException {
        ResultSet certcurs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT COUNT(*) AS CertPerCur ,naamCursus\n"
                + "FROM Inschrijven\n"
                + "JOIN Certificaat ON Certificaat.inschrijfId = Inschrijven.inschrijfId\n"
                + "GROUP BY naamCursus\n"
                + "ORDER BY CertPerCur DESC");
        int i = 0;
        while (certcurs.next()) {
            if (i == 0) {
                firstCursusDisplay.setText(certcurs.getString("naamCursus"));
            }
            if (i == 1) {
                secondCursusDisplay.setText(certcurs.getString("naamCursus"));
            }
            if (i == 2) {
                thirdCursusDisplay.setText(certcurs.getString("naamCursus"));
                break;
            }
            i++;
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        WelcomeLabelHomeScreen.setText("Hallo, " + DataShare.getInstance().getUsername());
        try {
            DisplayCertificaatPerCursus();
        } catch (SQLException ex) {
            Logger.getLogger(OV5OverzichtFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
