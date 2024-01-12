package Controllers;

import Validatie.Error;
import Java2Database.DataShare;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OverzichtFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    Label displayUserNameLabel;
    private Error Error = new Error();

    @FXML
    private Label WelcomeLabelHomeScreen;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        WelcomeLabelHomeScreen.setText("Hallo, " + DataShare.getInstance().getUsername());
    }

    @FXML //Methode toont ov1 scherm
    void Ov1Clicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/ov1ScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML //Methode toont ov2 scherm
    void Ov2Clicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/ov2ScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML //Methode toont  ov3 error
    void Ov3Clicked(MouseEvent event) throws IOException {
        Error.Ov3Clicked();
    }

    @FXML //Methode toont  ov4 error
    void Ov4Clicked(MouseEvent event) throws IOException {
        Error.OV4Clicked();
    }

    @FXML //Methode toont ov5 scherm
    void Ov5Clicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/ov5ScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML //Methode toont ov6 scherm
    void Ov6Clicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/ov6ScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML //Methode toont ov7 error
    void Ov7Clicked(MouseEvent event) throws IOException {
        Error.OV7Clicked();
    }

    @FXML //Methode toont  ov8 scherm
    void Ov8Clicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/ov8ScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML //Methode toont homescreen als je terug wilt
    void OverzichtBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
