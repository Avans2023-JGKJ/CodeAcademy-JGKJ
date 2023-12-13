package Validatie;

import Java2Database.DataBaseSQL;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.DataShare;
import static javafx.DialogCursistFXMLController.cursistDbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeScreenCursistFXMLController implements Initializable {

    @FXML
    private Label CursusDrieLabel;

    @FXML
    private Rectangle CursusDrieRechthoek;

    @FXML
    private Label CursusDrieTitel;

    @FXML
    private Label CursusEenLabel;

    @FXML
    private Rectangle CursusEenRechthoek;

    @FXML
    private Label CursusEenTitel;

    @FXML
    private Label CursusTweeLabel;

    @FXML
    private Rectangle CursusTweeRechthoek;

    @FXML
    private Label CursusTweeTitel;

    @FXML
    private Label WelcomeLabelHomeScreen;
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WelcomeLabelHomeScreen.setText("Hallo, " + DataShare.getInstance().getUsername());
//        visibleEen(false);
        loadRecentCursussen();
    }

private void loadRecentCursussen() {
    try {
        String command = "SELECT Cursus.onderwerp, Cursus.introductieTekst "
                + "FROM Cursus "
                + "WHERE naamCursus IN "
                + "(SELECT naamCursus FROM Inschrijven WHERE email IN "
                + "(SELECT email FROM Persoon WHERE UserName = ?))";

        try (PreparedStatement preparedStatement = DataBaseSQL.createConnection().prepareStatement(command)) {
            preparedStatement.setString(1, DataShare.getInstance().getUsername());

            try (ResultSet rs = preparedStatement.executeQuery()) {
                boolean skip = true;

                if (rs.next()) {
                    CursusEenTitel.setText(rs.getString("onderwerp"));
                    CursusEenLabel.setText(rs.getString("introductieTekst"));
                } else {
                    skip = false;
                    visibleEen(false);
                }

                if (rs.next() && skip) {
                    CursusTweeTitel.setText(rs.getString("onderwerp"));
                    CursusTweeLabel.setText(rs.getString("introductieTekst"));
                } else {
                    skip = false;
                    visibleTwee(false);
                }

                if (rs.next() && skip) {
                    CursusDrieTitel.setText(rs.getString("onderwerp"));
                    CursusDrieLabel.setText(rs.getString("introductieTekst"));
                } else {
                    visibleDrie(false);
                }
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(HomeScreenCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    private void visibleEen(boolean x) {
        CursusEenTitel.setVisible(x);
        CursusEenLabel.setVisible(x);
        CursusEenRechthoek.setVisible(x);
    }

    private void visibleTwee(boolean x) {
        CursusTweeTitel.setVisible(x);
        CursusTweeLabel.setVisible(x);
        CursusTweeRechthoek.setVisible(x);
    }

    private void visibleDrie(boolean x) {
        CursusDrieTitel.setVisible(x);
        CursusDrieLabel.setVisible(x);
        CursusDrieRechthoek.setVisible(x);
    }

    @FXML
    void BekijkAlleCursussenCursistClicked(ActionEvent event) {

    }

    @FXML
    void CursusDrieClicked(MouseEvent event) {
        try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("loginRegistreren.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
                    } catch (IOException ex) {
            Logger.getLogger(HomeScreenCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void CursusEenClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CursusInformatie.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
                    } catch (IOException ex) {
            Logger.getLogger(HomeScreenCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void CursusTweeClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cursusInformatie.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
                    } catch (IOException ex) {
            Logger.getLogger(HomeScreenCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
