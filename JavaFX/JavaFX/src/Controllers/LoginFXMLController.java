package Controllers;

import Java2Database.DataShare;
import Java2Database.DataBaseSQL;
import Validatie.Error;
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
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.time.format.DateTimeParseException;
import Validatie.DataValidatie;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class LoginFXMLController implements Initializable {

    @FXML
    TextField UserNameField;

    @FXML
    TextField PassWordField;

    @FXML
    TextField UserNameFieldRegistreren;

    @FXML
    TextField PassWordFieldRegistreren;

    @FXML
    TextField PassWordFieldRegistreren2;

    @FXML
    TextField cursistNaamColumninput;

    @FXML
    TextField cursistPostcodeInput;

    @FXML
    DatePicker cursistGeboortedatumInput;

    @FXML
    TextField cursistEmailInput;

    @FXML
    TextField cursistHuisnummerInput;

    @FXML
    TextField cursistWoonplaatsInput;

    @FXML
    TextField cursistLandCodeInput;

    @FXML
    RadioButton RadioButtonCursistVrouw;
    @FXML
    RadioButton RadioButtonCursistMan;

    ToggleGroup group = new ToggleGroup();

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Error Error = new Error();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void LoginButtonClicked(ActionEvent event) throws MalformedURLException, IOException {
        System.out.println("Login Button Clicked");
//        if (checkUserPassCombination(UserNameField.getText(), PassWordField.getText())) {
//            String username = UserNameField.getText();

//            HomeScreenFXMLController homeScreenController = loader.getController();
        DataShare.getInstance().setUsername(UserNameField.getText());

        if (checkRole(UserNameField.getText(), PassWordField.getText())) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreenAdmin.fxml"));
            root = loader.load();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreenCursist.fxml"));
                ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT email from Cursist WHERE email = (SELECT email FROM Persoon WHERE UserName = '" + UserNameField.getText() + "');").executeQuery();
                while (rs.next()) {
                    DataShare.getInstance().setCursistEmail(rs.getString("email"));
                }
                root = loader.load();
            } catch (SQLException ex) {
                Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

//        } else {
//            showAlert();
//        }
    }

    @FXML
    void RegistrerenButtonClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/loginRegistreren.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void RegistrerenKlaarButtonClicked(ActionEvent event) {
        try {
            if (PassWordFieldRegistreren.getText().equals(PassWordFieldRegistreren2.getText())) {
                if (DataValidatie.InsertCursistValid(
                        cursistNaamColumninput.getText(),
                        cursistPostcodeInput.getText(),
                        cursistEmailInput.getText(),
                        cursistGeboortedatumInput.getValue(),
                        RadioButtonCursistMan.isSelected(),
                        RadioButtonCursistVrouw.isSelected(),
                        cursistHuisnummerInput.getText(),
                        cursistWoonplaatsInput.getText(),
                        cursistLandCodeInput.getText())) {
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Cursist (naam, postCode, email, geboorteDatum, geslacht, huisNummer, woonPlaats, landCode) VALUES("
                            + "  '" + cursistNaamColumninput.getText()
                            + "',  '" + cursistPostcodeInput.getText()
                            + "',  '" + cursistEmailInput.getText()
                            + "',  '" + cursistGeboortedatumInput.getValue()
                            + "',  '" + RadioButtonGeslachtCheck()
                            + "',   '" + cursistHuisnummerInput.getText()
                            + "',  '" + cursistWoonplaatsInput.getText()
                            + "',  '" + cursistLandCodeInput.getText() + "')");

                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Persoon (Rol, UserName, PassWord, Email) VALUES("
                            + "'Cursist' ,'"
                            + UserNameFieldRegistreren.getText()
                            + "',  '" + PassWordFieldRegistreren.getText()
                            + "',  '" + cursistEmailInput.getText() + "')");

                    Error.ErrorSucces();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/login.fxml"));
                    root = loader.load();
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                Error.ErrorPassWord();
            }
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("23000")) {
            }
            Error.ErrorNull("SQL Fout!");
        } catch (DateTimeParseException ex) {
        } catch (IOException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public char RadioButtonGeslachtCheck() {
        if (RadioButtonCursistMan.isSelected()) {
            return 'm';
        } else if (RadioButtonCursistVrouw.isSelected()) {
            return 'v';
        }
        return 0;
    }

    @FXML
    void AfsluitenButtonClicked(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void BackButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/login.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

    boolean checkRole(String InputUsername, String InputPassword) {
        try {
            ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT * FROM Persoon WHERE UserName='" + InputUsername + "' AND PassWord='" + InputPassword + "' AND Rol = 'Admin'").executeQuery();

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
}
