package Controllers;

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
import Java2Database.DataBaseSQL;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Java2Database.DataShare;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
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

    @Override //Methode toont welcome label, laad de RecenCursussen
    public void initialize(URL url, ResourceBundle rb) {
        WelcomeLabelHomeScreen.setText("Hallo, " + DataShare.getInstance().getUsername());
        loadRecentCursussen();
    }

    //Methode toont Laad insgeschreven cursussen van cursist in
    void loadRecentCursussen() {
        try {
            String command = "SELECT Cursus.naamCursus, Cursus.onderwerp, Cursus.introductieTekst "
                    + "FROM Cursus "
                    + "WHERE naamCursus IN "
                    + "(SELECT naamCursus FROM Inschrijven WHERE email IN "
                    + "(SELECT email FROM Persoon WHERE UserName = '" + DataShare.getInstance().getUsername() + "'))";

            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), command);
            boolean skip = true;

            if (rs.next()) {
                visibleEen(true);
                CursusEenTitel.setText(rs.getString("onderwerp"));
                CursusEenLabel.setText(rs.getString("introductieTekst"));
                DataShare.getInstance().setNaamCursusEen(rs.getString("naamCursus"));
            } else {
                skip = false;
                visibleEen(false);
            }

            if (rs.next() && skip) {
                visibleTwee(true);
                CursusTweeTitel.setText(rs.getString("onderwerp"));
                CursusTweeLabel.setText(rs.getString("introductieTekst"));
                DataShare.getInstance().setNaamCursusTwee(rs.getString("naamCursus"));
            } else {
                skip = false;
                visibleTwee(false);
            }

            if (rs.next() && skip) {
                visibleDrie(true);
                CursusDrieTitel.setText(rs.getString("onderwerp"));
                CursusDrieLabel.setText(rs.getString("introductieTekst"));
                DataShare.getInstance().setNaamCursusDrie(rs.getString("naamCursus"));
            } else {
                visibleDrie(false);
            }

        } catch (SQLException ex) {
            Logger.getLogger(HomeScreenCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML //Methode toont inschrijvenDialog op click
    void InschrijvenClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/inschrijvenCursistDialog.fxml"));
            DialogPane pane = loader.load();

            DialogCursistHomeScreenFXMLController inschrijvenController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Cursus aanmaken");

            dialog.getDialogPane().getButtonTypes().clear();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

            Button applyButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.FINISH);
            applyButton.addEventFilter(ActionEvent.ACTION, ae -> {
                if (!inschrijvenController.ValidateAndSignUp()) {
                    ae.consume();
                }
            });

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.FINISH) {
                loadRecentCursussen();
            }

        } catch (IOException ex) {
            Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML //Methode toont certificaatScreen op click
    void CertificaatClicked(MouseEvent event) {
        try {
            DataShare.getInstance().setNaamCursus(DataShare.getInstance().getNaamCursusEen());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/certificaatScreenCursist.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Methode zet visibility op true of false
    private void visibleEen(boolean x) {
        CursusEenTitel.setVisible(x);
        CursusEenLabel.setVisible(x);
        CursusEenRechthoek.setVisible(x);
    }

    //Methode zet visibility op true of false
    private void visibleTwee(boolean x) {
        CursusTweeTitel.setVisible(x);
        CursusTweeLabel.setVisible(x);
        CursusTweeRechthoek.setVisible(x);
    }

    //Methode zet visibility op true of false
    private void visibleDrie(boolean x) {
        CursusDrieTitel.setVisible(x);
        CursusDrieLabel.setVisible(x);
        CursusDrieRechthoek.setVisible(x);
    }

    @FXML //Methode laad alle ingeschreven cursussen scherm
    void BekijkAlleCursussenCursistClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/cursusScreenCursist.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML //Methode laad CurusInformatie van 1
    void CursusEenClicked(MouseEvent event) {
        try {
            DataShare.getInstance().setNaamCursus(DataShare.getInstance().getNaamCursusEen());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/CursusInformatie.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML //Methode laad Cursusinformatie van 2
    void CursusTweeClicked(MouseEvent event) {
        try {
            DataShare.getInstance().setNaamCursus(DataShare.getInstance().getNaamCursusTwee());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/CursusInformatie.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML  //Methode laad Cursusinformatie van 3
    void CursusDrieClicked(MouseEvent event) {
        try {
            DataShare.getInstance().setNaamCursus(DataShare.getInstance().getNaamCursusDrie());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/CursusInformatie.fxml"));
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
