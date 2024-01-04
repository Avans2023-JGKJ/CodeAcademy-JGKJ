/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

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
import javafx.stage.Stage;

/**
 *
 * @author gijsv
 */
public class DialogOverzichtFXMLController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    Label displayUserNameLabel;

    @FXML
    private Label WelcomeLabelHomeScreen;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        WelcomeLabelHomeScreen.setText("Hallo, " + DataShare.getInstance().getUsername());
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
