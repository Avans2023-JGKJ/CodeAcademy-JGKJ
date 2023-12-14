// NIET AF MOMENTEEL ALLE DATA IS CURSUS!!!!!
package javafx;

import Java2Database.DataBaseSQL;
import Objects.Status;
import Objects.Niveau;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DialogContentItemFXMLController implements Initializable {

    @FXML
    private TextField contentItemsIdColumnInput;

    @FXML
    private TextField contentItemsTitelColumnInput;

    @FXML
    private TextField contentItemsDatumColumnInput;

    @FXML
    private ComboBox<Status> statusComboBox;

    @FXML
    void FinishButtonCreateContentItemClicked() {
        try {
            Status selectedStatus = statusComboBox.getValue();

            String insertSQL = String.format("INSERT INTO Cursus (contentItemId, titel, datum, status) VALUES ('%s', '%s', '%s', '%s')",
                    contentItemsIdColumnInput.getText(),
                    contentItemsTitelColumnInput.getText(),
                    contentItemsDatumColumnInput.getText(),
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
            String insertSQL = String.format("INSERT INTO Cursus (contentItemId, titel, datum, status) VALUES ('%s', '%s', '%s', '%s', '%s')",
                    contentItemsIdColumnInput.getText(),
                    contentItemsTitelColumnInput.getText(),
                    contentItemsDatumColumnInput.getText(),
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
    void FinishButtonUpdateContentItemClicked() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    private void loadData() {
        contentItemsIdColumnInput.setText(String.valueOf(DataShare.getInstance().getContentItemId()));
        contentItemsTitelColumnInput.setText(String.valueOf(DataShare.getInstance().getTitel()));
        contentItemsDatumColumnInput.setText(String.valueOf(DataShare.getInstance().getDatumPublicatie()));
//        statusComboBox(Status.valueOf(DataShare.getInstance().getStatus()));
        
        if (DataShare.getInstance().getStatus()== Status.valueOf("CONCEPT")) {
            statusComboBox.setValue(Status.CONCEPT);
        } 
        else if (DataShare.getInstance().getStatus()== Status.valueOf("ACTIEF")) {
            statusComboBox.setValue(Status.GEARCHIVEERD);
        } 
        else if (DataShare.getInstance().getStatus()== Status.valueOf("GEARCHIVEERD")) {
            statusComboBox.setValue(Status.GEARCHIVEERD);
        } 
    }
}
