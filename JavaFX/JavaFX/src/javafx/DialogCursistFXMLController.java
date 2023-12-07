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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class DialogCursistFXMLController implements Initializable {

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
    RadioButton radioButtonCursistMan, radioButtonCursistVrouw;
    
    ToggleGroup group = new ToggleGroup();
    


//       @FXML
    void FinishButtonCreateCursistClicked() {
            try {

                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                        "INSERT INTO Cursist (naam,postCode,email,geboorteDatum,geslacht,huisNummer,woonPlaats,landCode) VALUES("
                    + "  '" + cursistNaamColumninput.getText()
                    + "',  '" + cursistPostcodeInput.getText()
                    + "',  '" + cursistEmailInput.getText()
                    + "',  '" + cursistGeboortedatumInput.getText()
                    + "',  '" + RadioButtonGeslachtCheck()
                    + "',   '" + cursistHuisnummerInput.getText()
                    + "',  '" + cursistWoonplaatsInput.getText()
                    + "',  '" + cursistLandCodeInput.getText() + "')");

            } catch (SQLException ex) {
                Logger.getLogger(DialogCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public char RadioButtonGeslachtCheck() {
        if (radioButtonCursistMan.isSelected()) {
            return 'm';
        }
        else if (radioButtonCursistVrouw.isSelected()) {
            return 'v';
        }
        return 0;
    }

        @FXML
        void ApplyButtonUpdateCursistClicked
        
            () {
        try {
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                        "UPDATE Cursist SET"
                        + " naam = '" + cursistNaamColumninput.getText()
                        + "', postCode = '" + cursistPostcodeInput.getText()
                        + "', email = '" + cursistEmailInput.getText()
                        + "', geslacht = '" + RadioButtonGeslachtCheck()
                        + "', huisNummer = '" + cursistHuisnummerInput.getText()
                        + "', woonPlaats = '" + cursistWoonplaatsInput.getText()
                        + "', landCode = '" + cursistLandCodeInput.getText()
                        + "' WHERE email = '" + DataShare.getInstance().getCursistEmail() + "'");
               

            } catch (SQLException ex) {
                Logger.getLogger(DialogCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void initialize
        (URL url, ResourceBundle rb
        
            ) {
        loadData();
        radioButtonCursistMan.setToggleGroup(group);
        radioButtonCursistVrouw.setToggleGroup(group);
        }

    

    private void loadData() {
        cursistNaamColumninput.setText(String.valueOf(DataShare.getInstance().getCursistNaam()));
        cursistPostcodeInput.setText(String.valueOf(DataShare.getInstance().getCursistPostCode()));
        cursistEmailInput.setText(String.valueOf(DataShare.getInstance().getCursistEmail()));
        cursistHuisnummerInput.setText(String.valueOf(DataShare.getInstance().getCursistHuisnummer()));
        cursistWoonplaatsInput.setText(String.valueOf(DataShare.getInstance().getCursistWoonPlaats()));
        cursistLandCodeInput.setText(String.valueOf(DataShare.getInstance().getCursistLandCode()));
        if (DataShare.getInstance().getCursistGeslacht() == 'm'){
        radioButtonCursistMan.setSelected(true);
        radioButtonCursistVrouw.setSelected(false);
    }
        else if (DataShare.getInstance().getCursistGeslacht() == 'v'){
        radioButtonCursistMan.setSelected(false);
        radioButtonCursistVrouw.setSelected(true);
    }
        
        
    }
}
