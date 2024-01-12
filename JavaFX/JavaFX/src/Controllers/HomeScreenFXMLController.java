package Controllers;

import Java2Database.DataShare;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomeScreenFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    Label displayUserNameLabel;

    @FXML
    private Label WelcomeLabelHomeScreen;

    @Override //Methode toont welcome label
    public void initialize(URL url, ResourceBundle rb) {
        WelcomeLabelHomeScreen.setText("Hallo, " + DataShare.getInstance().getUsername());
    }

    @FXML //Methode toont CertificaatSCreenAdmin 
    void CertificatenButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/certificaatScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML //Methode toont contentitemsScreenAdmin
    void ContentItemButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/contentItemsScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML //Methode toont cursistScreenAdmin
    void CursistenButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/cursistScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML //Methode toont cursusScreenAdmin
    void CursusButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/cursusScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML //Methode toont persoonScreenAdmin
    void PersoonButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/persoonScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML //Methode toont overzichtScreenAdmin
    void OverzichtButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/overzichtScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
