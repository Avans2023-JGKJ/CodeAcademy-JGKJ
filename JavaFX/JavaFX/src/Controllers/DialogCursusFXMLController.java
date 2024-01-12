package Controllers;

import Java2Database.DataShare;
import Java2Database.DataBaseSQL;
import Objects.Niveau;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Validatie.DataValidatie;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class DialogCursusFXMLController implements Initializable {

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

//Deze methode laad de geselecteerde data in
    private void loadData() {
        naamCursusCursusColumnInput.setText(String.valueOf(DataShare.getInstance().getNaamCursus()));
        if ((DataShare.getInstance().getAantalContentItems()) != -1) {
            aantalContentItemsCursusColumnInput.setText(String.valueOf(DataShare.getInstance().getAantalContentItems()));
        }
        onderwerpCursusColumnInput.setText(String.valueOf(DataShare.getInstance().getOnderwerp()));
        introductieTekstCursusColumnInput.setText(String.valueOf(DataShare.getInstance().getIntroductieTekst()));
        niveauComboBox.setValue(DataShare.getInstance().getNiveau());

        if (DataShare.getInstance().getNiveau() == Niveau.valueOf("GEVORDERD")) {
            niveauComboBox.setValue(Niveau.GEVORDERD);
        } else if (DataShare.getInstance().getNiveau() == Niveau.valueOf("BEGINNER")) {
            niveauComboBox.setValue(Niveau.BEGINNER);
        } else if (DataShare.getInstance().getNiveau() == Niveau.valueOf("EXPERT")) {
            niveauComboBox.setValue(Niveau.EXPERT);
        }

    }
    //Deze methode voert de tests uit op de ingevoerde data en stuurt deze naar de database

    public boolean validateAndCreateCursus() {
        if (DataValidatie.InsertCursusValid(
                naamCursusCursusColumnInput.getText(),
                aantalContentItemsCursusColumnInput.getText(),
                onderwerpCursusColumnInput.getText(),
                introductieTekstCursusColumnInput.getText(),
                niveauComboBox.getValue())) {
            try {
                String insertSQL = String.format("INSERT INTO Cursus (naamCursus, aantalContentItems, onderwerp, introductieTekst, niveau) VALUES ('%s', '%s', '%s', '%s', '%s')",
                        naamCursusCursusColumnInput.getText(),
                        aantalContentItemsCursusColumnInput.getText(),
                        onderwerpCursusColumnInput.getText(),
                        introductieTekstCursusColumnInput.getText(),
                        niveauComboBox.getValue().name());
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQL);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(DialogCursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }

    //Deze methode voert de tests uit op de ingevoerde data en stuurt deze naar de database
    @FXML
    boolean validateAndUpdateCursus() {
        if (DataValidatie.UpdateCursusValid(naamCursusCursusColumnInput.getText(),
                aantalContentItemsCursusColumnInput.getText(),
                onderwerpCursusColumnInput.getText(),
                introductieTekstCursusColumnInput.getText(),
                niveauComboBox.getValue())) {
            String updateSQL = String.format("UPDATE Cursus SET naamCursus = '%s', aantalContentItems = '%s', onderwerp = '%s', introductieTekst = '%s', niveau = '%s' WHERE naamCursus = '" + DataShare.getInstance().getNaamCursus() + "'",
                    naamCursusCursusColumnInput.getText(),
                    aantalContentItemsCursusColumnInput.getText(),
                    onderwerpCursusColumnInput.getText(),
                    introductieTekstCursusColumnInput.getText(),
                    niveauComboBox.getValue().name());
            try {
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), updateSQL);
            } catch (SQLException ex) {
                Logger.getLogger(DialogCursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;

        } else {
            return false;
        }

    }

    //Deze methode sluit de geselecteerde dialog 
    @FXML
    void handleClose() {
        // Close the dialog window
        Stage stage = (Stage) naamCursusCursusColumnInput.getScene().getWindow();
        stage.close();
    }
    //Initialize wordt aangeroepen bij het inladen van de pagina

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
        niveauComboBox.setItems(FXCollections.observableArrayList(Niveau.values()));
    }
}
