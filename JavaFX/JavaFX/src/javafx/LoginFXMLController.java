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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import static javafx.CursistFXMLController.parseDate;
import static javafx.DialogCursistFXMLController.cursistDbConnection;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    TextField cursistGeboortedatumInput;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
            root = loader.load();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreenCursist.fxml"));
            root = loader.load();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginRegistreren.fxml"));
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
                LocalDate parsedDate = parseDate(cursistGeboortedatumInput.getText());
                if (parsedDate == null) {
                    return;
                }

                if (checkRegistreerFields()) {
                    String formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Cursist (naam, postCode, email, geboorteDatum, geslacht, huisNummer, woonPlaats, landCode) VALUES("
                            + "  '" + cursistNaamColumninput.getText()
                            + "',  '" + cursistPostcodeInput.getText()
                            + "',  '" + cursistEmailInput.getText()
                            + "',  '" + formattedDate
                            + "',  '" + RadioButtonGeslachtCheck()
                            + "',   '" + cursistHuisnummerInput.getText()
                            + "',  '" + cursistWoonplaatsInput.getText()
                            + "',  '" + cursistLandCodeInput.getText() + "')");
                    System.out.println("test123");
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Persoon (Rol, UserName, PassWord, Email) VALUES("
                            + "'Cursist' ,'"
                            + UserNameFieldRegistreren.getText()
                            + "',  '" + PassWordFieldRegistreren.getText()
                            + "',  '" + cursistEmailInput.getText() + "')");

                    showPopup("Succesvol Cursist Aangemaakt Je kunt nu inloggen!");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                    root = loader.load();
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }

            } else {
                showAlert();
            }
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("23000")) {
                CursistFXMLController.ErrorAlert("De ingevoerde email is al in gebruik!", "Email Incorrect");
                return;
            }
            CursistFXMLController.ErrorAlert("Er is iets fout gegaan!", "SQL Fout!");
        } catch (DateTimeParseException ex) {
            System.err.println("Invalid date input: " + cursistGeboortedatumInput.getText());
            CursistFXMLController.ErrorAlert("De ingevoerde geboortedatum is niet correct!", "Geboortedatum Incorrect!");
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    boolean checkRegistreerFields() {
        if (cursistNaamColumninput.getText() != null
                && cursistPostcodeInput.getText() != null
                && cursistEmailInput.getText() != null
                && cursistHuisnummerInput.getText() != null
                && cursistWoonplaatsInput.getText() != null
                && cursistLandCodeInput.getText() != null
                && cursistGeboortedatumInput.getText() != null
                && (RadioButtonGeslachtCheck() == 'm' || RadioButtonGeslachtCheck() == 'v')
                && UserNameFieldRegistreren.getText() != null
                && PassWordFieldRegistreren.getText() != null
                && PassWordFieldRegistreren2.getText() != null) {
            return true;
        } else {
            return false;
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

    void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("An ERROR has occured...");
        alert.setHeaderText("Combination is not correct");
        alert.showAndWait();
    }

    void showPopup(String Message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Something interesting happened!");
        alert.setHeaderText(Message);
        alert.showAndWait();
    }
}
