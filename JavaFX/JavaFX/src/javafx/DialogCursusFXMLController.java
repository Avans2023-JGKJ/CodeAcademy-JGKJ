package javafx;

import Java2Database.DataBaseSQL;
import Objects.Niveau;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
    private ComboBox<Niveau> niveauComboBox;

    public void initialize() {
        niveauComboBox.setItems(FXCollections.observableArrayList(Niveau.values()));
    }

    @FXML
    void FinishButtonCreateCursusClicked() {
        try {
            Niveau selectedNiveau = niveauComboBox.getValue();

            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                    "INSERT INTO Cursus (naamCursus, aantalContentItems, onderwerp, introductieTekst, niveau) VALUES"
                    + "('" + naamCursusCursusColumnInput.getText()
                    + "', '" + aantalContentItemsCursusColumnInput.getText()
                    + "', '" + onderwerpCursusColumnInput.getText()
                    + "', '" + introductieTekstCursusColumnInput.getText()
                    + "', '" + selectedNiveau.name() + "')");
            System.out.println("SUCCESVOL GEDAAN");
        } catch (SQLException ex) {
            Logger.getLogger(DialogCursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void FinishButtonUpdateCursusClicked() {
        // Implementation for updating a course should go here
    }
}
