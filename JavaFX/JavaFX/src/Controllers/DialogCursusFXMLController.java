package Controllers;

import Java2Database.DataShare;

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
import Controllers.CursusFXMLController;
import Validatie.SQLValid;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

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
        loadData();
        niveauComboBox.setItems(FXCollections.observableArrayList(Niveau.values()));
    }

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

    @FXML
    void FinishButtonCreateCursusClicked() {
        try {
            Niveau selectedNiveau = niveauComboBox.getValue();

            String insertSQL = String.format("INSERT INTO Cursus (naamCursus, aantalContentItems, onderwerp, introductieTekst, niveau) VALUES ('%s', '%s', '%s', '%s', '%s')",
                    naamCursusCursusColumnInput.getText(),
                    aantalContentItemsCursusColumnInput.getText(),
                    onderwerpCursusColumnInput.getText(),
                    introductieTekstCursusColumnInput.getText(),
                    selectedNiveau.name());
            System.out.println(insertSQL);
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQL);
            System.out.println("Cursus successfully created.");

        } catch (SQLException ex) {
            Logger.getLogger(DialogCursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validateAndCreateCursus() {
        try {

            Niveau selectedNiveau = niveauComboBox.getValue();
            String insertSQL = String.format("INSERT INTO Cursus (naamCursus, aantalContentItems, onderwerp, introductieTekst, niveau) VALUES ('%s', '%s', '%s', '%s', '%s')",
                    naamCursusCursusColumnInput.getText(),
                    aantalContentItemsCursusColumnInput.getText(),
                    onderwerpCursusColumnInput.getText(),
                    introductieTekstCursusColumnInput.getText(),
                    selectedNiveau.name());
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQL);
            System.out.println("Cursus successfully created.");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DialogCursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @FXML
    boolean FinishButtonUpdateCursusClicked() {
        if (SQLValid.validatieUpdateCursus(naamCursusCursusColumnInput.getText(), aantalContentItemsCursusColumnInput.getText(), onderwerpCursusColumnInput.getText(),introductieTekstCursusColumnInput.getText(), niveauComboBox.getValue().name())) {
            String updateSQL = String.format("UPDATE Cursus SET naamCursus = '%s', aantalContentItems = '%s', onderwerp = '%s', introductieTekst = '%s', niveau = '%s' WHERE naamCursus = '"+DataShare.getInstance().getNaamCursus()+"'",
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
            System.out.println("Cursus successfully created.");
            return true;
            
        } else {
            return false;
        }

    }

    @FXML
    void handleClose() {
        // Close the dialog window
        Stage stage = (Stage) naamCursusCursusColumnInput.getScene().getWindow();
        stage.close();
    }
}
