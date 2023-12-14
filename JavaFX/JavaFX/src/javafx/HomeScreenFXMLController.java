package javafx;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("certificaatScreen.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    void ContentItemButtonClicked(MouseEvent event) throws IOException {
        System.out.println("Content-Item");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("contentItemsScreenAdmin.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    void CursistenButtonClicked(MouseEvent event) throws IOException {
        System.out.println("Cursist");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cursistScreenAdmin.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    void CursusButtonClicked(MouseEvent event) throws IOException {
        System.out.println("Cursus");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cursusScreenAdmin.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    void InschrijvenButtonClicked(MouseEvent event) throws IOException {
        System.out.println("Inschrijven");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inschrijvenScreen.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();  
    }
    
    @FXML
    void OverigButtonClicked(MouseEvent event) throws IOException {
        System.out.println("Overig");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
}
