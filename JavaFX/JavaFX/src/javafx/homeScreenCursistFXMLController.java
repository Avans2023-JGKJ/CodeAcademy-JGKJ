package javafx;

import javafx.DataShare;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class homeScreenCursistFXMLController implements Initializable {

    @FXML
    private Label WelcomeLabelHomeScreen;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WelcomeLabelHomeScreen.setText("Hallo, "+DataShare.getInstance().getUsername());
    }

}
    

