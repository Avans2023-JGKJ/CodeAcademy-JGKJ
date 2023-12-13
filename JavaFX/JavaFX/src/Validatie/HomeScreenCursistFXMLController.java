package Validatie;

import Java2Database.DataBaseSQL;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WelcomeLabelHomeScreen.setText("Hallo, " + DataShare.getInstance().getUsername());
    }

    @FXML
    void BekijkAlleCursussenCursistClicked(ActionEvent event) {

    }

    @FXML
    void CursusDrieClicked(MouseEvent event) {
        System.out.println("CURSUS 3");
        CursusTweeRechthoek.setVisible(false);
    }

    @FXML
    void CursusEenClicked(MouseEvent event) {
        System.out.println("CURSUS 1");
    }

    @FXML
    void CursusTweeClicked(MouseEvent event) {
        System.out.println("CURSUS 2");
    }

}
