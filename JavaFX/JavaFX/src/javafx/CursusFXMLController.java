package javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class CursusFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private TableView<?> CursusTableView;

    @FXML
    void CursusAanmakenClicked(ActionEvent event) {
        System.out.println("CursusAanmakenClicked");
    }

    @FXML
    void CursusAanpassenClicked(ActionEvent event) {
        System.out.println("CursusAanpassenClicked");
    }

    @FXML
    void CursusVerwijderenClicked(ActionEvent event) {
        System.out.println("CursusVerwijderenClicked");
    }

    @FXML
    void CursusBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
