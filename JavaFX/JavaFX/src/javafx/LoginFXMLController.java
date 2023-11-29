package javafx;

import Java2Database.DataBaseSQL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginFXMLController implements Initializable {

    @FXML
    private TextField UserNameField;
    @FXML
    private TextField PassWordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
    

    @FXML
    void LoginButtonClicked(ActionEvent event) throws MalformedURLException, IOException {
        System.out.println("Login Button Clicked");
        if (checkUserPassCombination(UserNameField.getText(), PassWordField.getText())) {
            String username = UserNameField.getText();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
            root = loader.load();

//            HomeScreenFXMLController homeScreenController = loader.getController();
//            homeScreenController.displayUserName(username);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            showAlert();
        }
    }

    boolean checkUserPassCombination(String InputUsername, String InputPassword) {
        try {
            ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT * FROM Persoon WHERE UserName='" + InputUsername + "' AND PassWord='" + InputPassword + "'").executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("An ERROR has occured...");
        alert.setHeaderText("Combination is not correct");
        alert.showAndWait();
    }
}
