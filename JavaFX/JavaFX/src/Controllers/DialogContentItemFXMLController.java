// NIET AF MOMENTEEL ALLE DATA IS CURSUS!!!!!
package Controllers;
import Java2Database.DataShare;

import Java2Database.DataShare;
import Java2Database.DataBaseSQL;
import Objects.Status;
import Objects.Niveau;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Controllers.DialogCursistFXMLController.cursistDbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DialogContentItemFXMLController implements Initializable {

    @FXML
    private TextField contentItemsNaamCursusColumnInput;

    @FXML
    private TextField contentItemscontentItemIdColumnInput;

    @FXML
    private TextField contentItemsTitelColumnInput;

    @FXML
    private DatePicker contentItemsDatumColumnInput;

    @FXML
    private TextField contentItemsBeschrijvingColumnInput;

    @FXML
    private ComboBox<Status> statusComboBox;

    @FXML
    void FinishButtonCreateContentItemClicked() {
        try {
            Status selectedStatus = statusComboBox.getValue();

            String insertSQL = String.format("INSERT INTO Cursus (naamCursus, contentItemId, titel, datum, beschrijving, status) VALUES ('%s','%s', '%s', '%s', '%s', '%s')",
                    contentItemsNaamCursusColumnInput.getText(),
                    contentItemscontentItemIdColumnInput.getText(),
                    contentItemsTitelColumnInput.getText(),
                    contentItemsDatumColumnInput.getValue(),
                    contentItemsBeschrijvingColumnInput.getText(),
                    selectedStatus.name());
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQL);
            System.out.println("ContentItem successfully created.");

        } catch (SQLException ex) {
            Logger.getLogger(DialogContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validateAndCreateContentItem() {
        try {

            Status selectedStatus = statusComboBox.getValue();
            String insertSQL = String.format("INSERT INTO Cursus (naamCursus, contentItemId, titel, datum, beschrijving, status) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                    contentItemsNaamCursusColumnInput.getText(),
                    contentItemscontentItemIdColumnInput.getText(),
                    contentItemsTitelColumnInput.getText(),
                    contentItemsDatumColumnInput.getValue(),
                    contentItemsBeschrijvingColumnInput.getText(),
                    selectedStatus.name());
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQL);
            System.out.println("ContentItem successfully created.");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DialogContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @FXML
    void ApplyButtonUpdateContentItemClicked() {
        try {
            Status selectedStatus = statusComboBox.getValue();
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(cursistDbConnection),
                    "UPDATE Cursist SET"
                    + " naamCursus = '" + contentItemsNaamCursusColumnInput.getText()
                    + "', titel = '" + contentItemsTitelColumnInput.getText()
                    + "', beschrijving = '" + contentItemsBeschrijvingColumnInput.getText()
                    + "', status = '" + selectedStatus.name()
                    + "' WHERE contentItemId = '" + DataShare.getInstance().getContentItemId() + "'");

        } catch (SQLException ex) {
            Logger.getLogger(DialogCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    private void loadData() {
        contentItemsNaamCursusColumnInput.setText(String.valueOf(DataShare.getInstance().getNaamCursus()));
        contentItemsTitelColumnInput.setText(String.valueOf(DataShare.getInstance().getTitel()));
        contentItemsBeschrijvingColumnInput.setText(String.valueOf(DataShare.getInstance().getBeschrijving())); 
        statusComboBox.setValue(DataShare.getInstance().getStatus());

        if (DataShare.getInstance().getStatus() == Status.valueOf("CONCEPT")) {
            statusComboBox.setValue(Status.CONCEPT);
        } else if (DataShare.getInstance().getStatus() == Status.valueOf("ACTIEF")) {
            statusComboBox.setValue(Status.GEARCHIVEERD);
        } else if (DataShare.getInstance().getStatus() == Status.valueOf("GEARCHIVEERD")) {
            statusComboBox.setValue(Status.GEARCHIVEERD);
        }
    }
}
