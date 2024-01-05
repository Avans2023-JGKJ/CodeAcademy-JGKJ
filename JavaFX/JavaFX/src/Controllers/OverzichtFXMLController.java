/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author gijsv
 */
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

    @FXML
    void Ov1Clicked(MouseEvent event) throws IOException {
        System.out.println("Overzicht 1");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/ov1ScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Ov2Clicked(MouseEvent event) throws IOException {
        System.out.println("Overzicht 2");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/ov2ScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Ov3Clicked(MouseEvent event) throws IOException {
        System.out.println("Overzicht 3");
        Error.Ov3Clicked();
    }

    @FXML
    void Ov4Clicked(MouseEvent event) throws IOException {
        System.out.println("Overzicht 4");
        Error.OV4Clicked();
    }

    @FXML
    void Ov5Clicked(MouseEvent event) throws IOException {
        System.out.println("Overzicht 5");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/ov5ScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Ov6Clicked(MouseEvent event) throws IOException {
        System.out.println("Overzicht 6");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/ov6ScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Ov7Clicked(MouseEvent event) throws IOException {
        System.out.println("Overzicht 7");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/ov7ScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Ov8Clicked(MouseEvent event) throws IOException {
        System.out.println("Overzicht 8");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/ov8ScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void OverzichtBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
