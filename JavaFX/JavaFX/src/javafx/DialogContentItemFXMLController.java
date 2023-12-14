// NIET AF MOMENTEEL ALLE DATA IS CURSUS!!!!!

package javafx;

import Java2Database.DataBaseSQL;
import Objects.Niveau;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class DialogContentItemFXMLController {
        @FXML
    private TextField contentItemsIdColumnInput;

    @FXML
    private TextField contentItemsTitelColumnInput;

    @FXML
    private TextField contentItemsDatumColumnInput;

    @FXML
    private TextField contentItemsStatusColumnInput;

    @FXML
    private ComboBox<Niveau> niveauComboBox;
    
    public boolean validateAndCreateContentItem(){
    try {

            Niveau selectedNiveau = niveauComboBox.getValue();
            String insertSQL = String.format("INSERT INTO Cursus (naamCursus, aantalContentItems, onderwerp, introductieTekst, niveau) VALUES ('%s', '%s', '%s', '%s', '%s')",
                    contentItemsIdColumnInput.getText(),
                    contentItemsTitelColumnInput.getText(),
                    contentItemsDatumColumnInput.getText(),
                    contentItemsStatusColumnInput.getText(),
                    selectedNiveau.name());
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQL);
            System.out.println("Cursus successfully created.");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DialogCursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
