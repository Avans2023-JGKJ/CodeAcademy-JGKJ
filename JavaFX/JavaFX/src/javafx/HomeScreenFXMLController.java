package javafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomeScreenFXMLController implements Initializable {
    
    Label displayUserNameLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
//    public void displayUserName(String username) {
//        displayUserNameLabel.setText("Hallo: " + username);
//    }
    
    @FXML
    void CertificatenButtonClicked(ActionEvent event) {
        System.out.println("Certificaat");
        //Change Scene to Certifcaat
    }
    
    @FXML
    void ContentItemButtonClicked(ActionEvent event) {
        System.out.println("Content-Item");
        //Change Scene to Content-Item
    }
    
    @FXML
    void CursistenButtonClicked(ActionEvent event) {
        System.out.println("Cursist");
        //Change Scene to Cursist
    }
    
    @FXML
    void CursusButtonClicked(ActionEvent event) {
        System.out.println("Cursus");
        //Change Scene to Cursus
    }
    
    @FXML
    void InschrijvenButtonClicked(ActionEvent event) {
        System.out.println("Inschrijven");
        //Change Scene to Inschrijven   
    }
    
    @FXML
    void OverigButtonClicked(ActionEvent event) {
        System.out.println("Overig");
        //Change Scene to Overig
    }
    
}
