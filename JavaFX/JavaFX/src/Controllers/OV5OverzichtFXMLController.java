package Controllers;

import Java2Database.DataBaseSQL;
import Java2Database.DataShare;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class OV5OverzichtFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label WelcomeLabelHomeScreen;

    @FXML
    private Label firstWebcastDisplay;

    @FXML
    private Label secondWebcastDisplay;

    @FXML
    private Label thirdWebcastDisplay;

    //Methode toont de webcasts inc database data
    void DisplayWebcasts() throws SQLException {
        ResultSet webcasts = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT COUNT(*) AS #Webcasts_Bekeken, Voortgang.naamCursus, contentItems.titel\n"
                + "FROM contentItems\n"
                + "JOIN Voortgang ON Voortgang.contentItemId = contentItems.contentItemId\n"
                + "JOIN Webcast ON Webcast.contentitemId = contentItems.contentItemId\n"
                + "GROUP BY Voortgang.naamCursus, Voortgang.voortgangsPercentage, contentItems.titel\n"
                + "HAVING COUNT(voortgang.naamCursus) > 0 AND Voortgang.voortgangspercentage = 100 ");
        int i = 0;
        while (webcasts.next()) {
            if (i == 0) {
                firstWebcastDisplay.setText(webcasts.getString("titel"));
            }
            if (i == 1) {
                secondWebcastDisplay.setText(webcasts.getString("titel"));
            }
            if (i == 2) {
                thirdWebcastDisplay.setText(webcasts.getString("titel"));
                break;
            }
            i++;
        }
    }

    @Override //Methode zet Welcome label, en spreekt Methode aan DisplayWebcast
    public void initialize(URL arg0, ResourceBundle arg1) {
        WelcomeLabelHomeScreen.setText("Hallo, " + DataShare.getInstance().getUsername());
        try {
            DisplayWebcasts();
        } catch (SQLException ex) {
            Logger.getLogger(OV5OverzichtFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML //Methode toont overzichtScreen als je terug wilt
    void OverzichtBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/OverzichtScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
