package javafx;

import Java2Database.DataBaseSQL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DialogCursusFXMLController {

    @FXML
    private TextField naamCursusCursusColumnInput;

    @FXML
    private TextField aantalContentItemsCursusColumnInput;

    @FXML
    private TextField onderwerpCursusColumnInput;

    @FXML
    private TextField introductieTekstCursusColumnInput;

    @FXML
    private TextField niveauCursusColumnInput;

    @FXML
    void FinishButtonCreateCursusClicked() {
        try {
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                    "INSERT INTO Cursus (naamCursus, aantalContentItems, onderwerp, introductieTekst) VALUES"
                            + "  '" + naamCursusCursusColumnInput.getText()
                            + "',  '" + aantalContentItemsCursusColumnInput.getText()
                            + "',  '" + onderwerpCursusColumnInput.getText()
                            + "',  '" + introductieTekstCursusColumnInput.getText() + "'");
            System.out.println("SUCCESVOL GEDAAN");
        } catch (SQLException ex) {
            Logger.getLogger(DialogCursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void FinishButtonUpdateCursusClicked() {
        try {
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                    "INSERT INTO Cursus (naamCursus, aantalContentItems, introductieTekst, niveau) VALUES"
                            + "  '" + naamCursusCursusColumnInput.getText()
                            + "',  '" + aantalContentItemsCursusColumnInput.getText()
                            + "',  '" + introductieTekstCursusColumnInput.getText()
                            + "',  '" + niveauCursusColumnInput.getText() + "'");
            System.out.println("SUCCESVOL GEDAAN");
        } catch (SQLException ex) {
            Logger.getLogger(DialogCursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
