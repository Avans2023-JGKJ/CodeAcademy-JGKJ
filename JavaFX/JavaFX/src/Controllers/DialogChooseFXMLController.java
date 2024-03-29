package Controllers;

import Java2Database.DataShare;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public class DialogChooseFXMLController implements Initializable {

//Deze methode stuurt je door naar het aanmaakscherm voor een webcast
    @FXML
    void CreateWebcast(ActionEvent event) {
        DataShare.getInstance().resetContentItem();
        try {
            DataShare.getInstance().setCreatedItem("webcast");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/createWebcastDialog.fxml"));
            DialogPane pane = loader.load();

            DialogContentItemFXMLController createWebcastController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Webcast aanmaken");

            dialog.getDialogPane().getButtonTypes().clear();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

            Button applyButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.FINISH);
            applyButton.addEventFilter(ActionEvent.ACTION, ae -> {
                if (!createWebcastController.ValidateAndCreateWebcast()) {
                    ae.consume();
                }
            });

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.CANCEL) {
            }

        } catch (IOException ex) {
            Logger.getLogger(ContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//Deze methode stuurt je door naar het aanmaakscherm voor een module
    @FXML
    void CreateModule(ActionEvent event) {
        DataShare.getInstance().resetContentItem();
        try {
            DataShare.getInstance().setCreatedItem("module");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/createModuleDialog.fxml"));
            DialogPane pane = loader.load();

            DialogContentItemFXMLController createModuleController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Module aanmaken");

            dialog.getDialogPane().getButtonTypes().clear();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

            Button applyButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.FINISH);
            applyButton.addEventFilter(ActionEvent.ACTION, ae -> {
                if (!createModuleController.ValidateAndCreateModule()) {
                    ae.consume();
                }
            });

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.CANCEL) {
            }

        } catch (IOException ex) {
            Logger.getLogger(ContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
