package Controllers;

import Java2Database.DataShare;

import Java2Database.DataShare;
import Java2Database.DataBaseSQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controllers.CursistFXMLController.parseDate;
import Validatie.DataValidatie;
import javafx.scene.control.DatePicker;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class DialogCursistFXMLController implements Initializable {

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
    RadioButton radioButtonCursistMan, radioButtonCursistVrouw;

    ToggleGroup group = new ToggleGroup();

    public static Connection cursistDbConnection;

    //       @FXML
    boolean ValidateAndCreateCursist() {
        if (DataValidatie.InsertCursistValid(
                cursistNaamColumninput.getText(),
                cursistPostcodeInput.getText(),
                cursistEmailInput.getText(),
                cursistGeboortedatumInput.getValue(),
                radioButtonCursistMan.isSelected(),
                radioButtonCursistVrouw.isSelected(),
                cursistHuisnummerInput.getText(),
                cursistWoonplaatsInput.getText(),
                cursistLandCodeInput.getText()
        )) {
            try {
                DataBaseSQL.sendCommand(cursistDbConnection, "INSERT INTO Cursist (naam, postCode, email, geboorteDatum, geslacht, huisNummer, woonPlaats, landCode) VALUES("
                        + "  '" + cursistNaamColumninput.getText()
                        + "',  '" + cursistPostcodeInput.getText()
                        + "',  '" + cursistEmailInput.getText().toLowerCase()
                        + "',  '" + cursistGeboortedatumInput.getValue()
                        + "',  '" + RadioButtonGeslachtCheck()
                        + "',   '" + cursistHuisnummerInput.getText()
                        + "',  '" + cursistWoonplaatsInput.getText()
                        + "',  '" + cursistLandCodeInput.getText() + "')");
                System.out.println("Cursist succesfully created");
                return true;
            } catch (SQLException ex) {
                if (ex.getSQLState().equals("23000")) {
                    CursistFXMLController.ErrorAlert("De ingevoerde email is al in gebruik!", "Email Incorrect");
                }
                CursistFXMLController.ErrorAlert("Er is iets fout gegaan!", "SQL Fout!");
            } catch (DateTimeParseException ex) {
                System.err.println("Invalid date input: " + cursistGeboortedatumInput.getValue());
                CursistFXMLController.ErrorAlert("De ingevoerde geboortedatum is niet correct!", "Geboortedatum Incorrect!");
            }
        }
        return false;
    }

    public char RadioButtonGeslachtCheck() {
        if (radioButtonCursistMan.isSelected()) {
            return 'm';
        } else if (radioButtonCursistVrouw.isSelected()) {
            return 'v';
        }
        return 0;
    }

    boolean ValidateAndUpdateCursist() {
        if (DataValidatie.UpdateCursistValid(
                cursistNaamColumninput.getText(),
                cursistPostcodeInput.getText(),
                cursistEmailInput.getText(),
                radioButtonCursistMan.isSelected(),
                radioButtonCursistVrouw.isSelected(),
                cursistHuisnummerInput.getText(),
                cursistWoonplaatsInput.getText(),
                cursistLandCodeInput.getText()
        )) {
            try {
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(cursistDbConnection),
                        "UPDATE Cursist SET"
                        + " naam = '" + cursistNaamColumninput.getText()
                        + "', postCode = '" + cursistPostcodeInput.getText()
                        + "', email = '" + cursistEmailInput.getText()
                        + "', geslacht = '" + RadioButtonGeslachtCheck()
                        + "', huisNummer = '" + cursistHuisnummerInput.getText()
                        + "', woonPlaats = '" + cursistWoonplaatsInput.getText()
                        + "', landCode = '" + cursistLandCodeInput.getText()
                        + "' WHERE email = '" + DataShare.getInstance().getCursistEmail() + "'");
                System.out.println("Cursist succesfully updated");
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(DialogCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        loadData();
        radioButtonCursistMan.setToggleGroup(group);
        radioButtonCursistVrouw.setToggleGroup(group);
        cursistDbConnection = DataBaseSQL.createConnection(cursistDbConnection);

    }

    private void loadData() {
        cursistNaamColumninput.setText(String.valueOf(DataShare.getInstance().getCursistNaam()));
        cursistPostcodeInput.setText(String.valueOf(DataShare.getInstance().getCursistPostCode()));
        cursistEmailInput.setText(String.valueOf(DataShare.getInstance().getCursistEmail()));
        cursistHuisnummerInput.setText(String.valueOf(DataShare.getInstance().getCursistHuisnummer()));
        cursistWoonplaatsInput.setText(String.valueOf(DataShare.getInstance().getCursistWoonPlaats()));
        cursistLandCodeInput.setText(String.valueOf(DataShare.getInstance().getCursistLandCode()));
        if (DataShare.getInstance().getCursistGeslacht() == 'm') {
            radioButtonCursistMan.setSelected(true);
            radioButtonCursistVrouw.setSelected(false);
        } else if (DataShare.getInstance().getCursistGeslacht() == 'v') {
            radioButtonCursistMan.setSelected(false);
            radioButtonCursistVrouw.setSelected(true);
        }

    }
}
