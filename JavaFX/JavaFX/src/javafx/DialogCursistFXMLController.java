package javafx;

import Java2Database.DataBaseSQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DialogCursistFXMLController implements Initializable {

    @FXML
    TextField cursistNaamColumninput;

    @FXML
    TextField cursistPostcodeInput;

    @FXML
    TextField cursistEmailInput;

    @FXML
    TextField cursistHuisnummerInput;

    @FXML
    TextField cursistWoonplaatsInput;

    @FXML
    TextField cursistLandCodeInput;

//       @FXML
    void FinishButtonCreateCursistClicked() {
        try {
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                    "INSERT INTO Cursist(naam,postCode,email,huisNummer,woonPlaats,landCode) VALUES"
                    + "  '" + cursistNaamColumninput.getText()
                    + "',  '" + cursistPostcodeInput.getText()
                    + "',  '" + cursistEmailInput.getText()
                    + "',   '" + cursistHuisnummerInput.getText()
                    + "',  '" + cursistWoonplaatsInput.getText()
                    + "',  '" + cursistLandCodeInput.getText());

            System.out.println("SUCCESVOL GEDAAN");

        } catch (SQLException ex) {
            Logger.getLogger(DialogCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void ApplyButtonUpdateCursistClicked() {
        try {
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                    "UPDATE Cursist SET"
                    + " naam = '" + cursistNaamColumninput.getText()
                    + "', postCode = '" + cursistPostcodeInput.getText()
                    + "', email = '" + cursistEmailInput.getText()
                    + "', huisNummer = '" + cursistHuisnummerInput.getText()
                    + "', woonPlaats = '" + cursistWoonplaatsInput.getText()
                    + "', landCode = '" + cursistLandCodeInput.getText()
                    + "' WHERE email = '" + DataShare.getInstance().getCursistEmail() + "'");
            System.out.println("SUCCESVOL GEDAAN");

        } catch (SQLException ex) {
            Logger.getLogger(DialogCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    private void loadData() {
        cursistNaamColumninput.setText(String.valueOf(DataShare.getInstance().getCursistNaam()));
        cursistPostcodeInput.setText(String.valueOf(DataShare.getInstance().getCursistPostCode()));
        cursistEmailInput.setText(String.valueOf(DataShare.getInstance().getCursistEmail()));
        cursistHuisnummerInput.setText(String.valueOf(DataShare.getInstance().getCursistHuisnummer()));
        cursistWoonplaatsInput.setText(String.valueOf(DataShare.getInstance().getCursistWoonPlaats()));
        cursistLandCodeInput.setText(String.valueOf(DataShare.getInstance().getCursistLandCode()));
    }
}
