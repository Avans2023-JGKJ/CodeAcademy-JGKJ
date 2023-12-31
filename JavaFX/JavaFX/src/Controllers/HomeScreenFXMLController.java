package Controllers;
import Java2Database.DataShare;

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

public class HomeScreenFXMLController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    Label displayUserNameLabel;
    
    @FXML
    private Label WelcomeLabelHomeScreen;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        WelcomeLabelHomeScreen.setText("Hallo, "+DataShare.getInstance().getUsername());
    }
    
//    public void displayUserName(String username) {
//        displayUserNameLabel.setText("Hallo: " + username);
//    }
    
    @FXML
    void CertificatenButtonClicked(MouseEvent event) throws IOException {
        System.out.println("Certificaat");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/certificaatScreenAdmin.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    void ContentItemButtonClicked(MouseEvent event) throws IOException {
        System.out.println("Content-Item");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/contentItemsScreenAdmin.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    void CursistenButtonClicked(MouseEvent event) throws IOException {
        System.out.println("Cursist");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/cursistScreenAdmin.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    void CursusButtonClicked(MouseEvent event) throws IOException {
        System.out.println("Cursus");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/cursusScreenAdmin.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    void PersoonButtonClicked(MouseEvent event) throws IOException {
        System.out.println("Inschrijven");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/persoonScreenAdmin.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();  
    }
    
    @FXML
    void OverzichtButtonClicked(MouseEvent event) throws IOException {
        System.out.println("Overzicht");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/overzichtScreenAdmin.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
}
