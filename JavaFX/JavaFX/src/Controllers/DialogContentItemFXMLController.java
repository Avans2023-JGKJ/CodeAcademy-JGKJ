// NIET AF MOMENTEEL ALLE DATA IS CURSUS!!!!!
package Controllers;

import Java2Database.DataShare;

import Java2Database.DataShare;
import Java2Database.DataBaseSQL;
import Objects.Status;
import Objects.Niveau;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Controllers.DialogCursistFXMLController.cursistDbConnection;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;

public class DialogContentItemFXMLController implements Initializable {

    @FXML
    private ComboBox<Status> statusComboBox;

    //Module
    @FXML
    private TextField ModuleBeschrijvingColumnInput;

    @FXML
    private TextField ModuleEmailContactColumnInput;

    @FXML
    private TextField ModuleNaamContactColumnInput;

    @FXML
    private TextField ModuleTitelColumnInput;

    @FXML
    private TextField ModuleVersieColumnInput;

    @FXML
    private TextField ModuleVolgNrColumnInput;

    @FXML
    private ComboBox<String> contentItemsNaamCursusComboBoxInput;

    @FXML
    private TextField WebcastBeschrijvingColumnInput;

    @FXML
    private DatePicker WebcastDatumPublicatieColumnInput;

    @FXML
    private TextField WebcastNaamSprekerColumnInput;

    @FXML
    private TextField WebcastOrganisatieSprekerColumnInput;

    @FXML
    private TextField WebcastTijdsDuurColumnInput;

    @FXML
    private TextField WebcastUrlColumnInput;

    @FXML
    private TextField WebcastTitelColumnInput;

    //Lijst met CursusNamen
    @FXML
    private ObservableList<String> naamCursusList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            statusComboBox.setItems(FXCollections.observableArrayList(Status.values()));
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT naamCursus FROM Cursus");
            while (rs.next()) {
                naamCursusList.add(rs.getString("naamCursus"));
            }
            contentItemsNaamCursusComboBoxInput.setItems(naamCursusList);
            if (DataShare.getInstance().getNaamCursus() != null) {
                loadData();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadData() {
        contentItemsNaamCursusComboBoxInput.setValue(DataShare.getInstance().getNaamCursus());
        statusComboBox.setValue(DataShare.getInstance().getStatus());
        if (DataShare.getInstance().getVersie() == null) {
            //WEBCAST INLADEN
            DataShare.getInstance().setCreatedItem("webcast");
            WebcastTitelColumnInput.setText(DataShare.getInstance().getTitel());
            if (DataShare.getInstance().getModuleBeschrijving() != null) {
                WebcastBeschrijvingColumnInput.setText(String.valueOf(DataShare.getInstance().getModuleBeschrijving()));
            }
            WebcastDatumPublicatieColumnInput.setValue(DataShare.getInstance().getDatumPublicatie());
//            if(DataShare.getInstance().getURL() != null){
            WebcastUrlColumnInput.setText(DataShare.getInstance().getURL());
//            }
            WebcastTijdsDuurColumnInput.setText(String.valueOf(DataShare.getInstance().getTijdsduur()));
            WebcastNaamSprekerColumnInput.setText(DataShare.getInstance().getNaamSpreker());
            WebcastOrganisatieSprekerColumnInput.setText(DataShare.getInstance().getOrganisatieSpreker());

        } else if (DataShare.getInstance().getVersie() != null) {
//            MODULE INLADEN
            DataShare.getInstance().setCreatedItem("module");
            ModuleTitelColumnInput.setText(DataShare.getInstance().getTitel());
            ModuleVersieColumnInput.setText(DataShare.getInstance().getVersie());
            if (DataShare.getInstance().getModuleBeschrijving() != null) {
                ModuleBeschrijvingColumnInput.setText(String.valueOf(DataShare.getInstance().getModuleBeschrijving()));
            }
            ModuleNaamContactColumnInput.setText(DataShare.getInstance().getNaamContactPersoon());
            ModuleEmailContactColumnInput.setText(DataShare.getInstance().getEmailContactPersoon());
            ModuleVolgNrColumnInput.setText(String.valueOf(DataShare.getInstance().getVolgordeNr()));
        }

    }

    void CreateModule() {
        try {
            Status selectedStatus = statusComboBox.getValue();
            System.out.println("MODULE");
            String insertSQLContentitem = String.format("INSERT INTO contentItems (naamCursus, beschrijving, titel, datum, status) VALUES ('%s','%s', '%s', '%s', '%s')",
                    contentItemsNaamCursusComboBoxInput.getValue(),
                    ModuleBeschrijvingColumnInput.getText(),
                    ModuleTitelColumnInput.getText(),
                    LocalDate.now().toString(),
                    selectedStatus.name());
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQLContentitem);
            System.out.println("ContentItem successfully created.");

            String selectSQLId = "SELECT contentItemId FROM contentItems ORDER BY contentItemId desc";
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), selectSQLId);
            rs.next();
            DataShare.getInstance().setContentItemId(Integer.valueOf(rs.getString("contentItemId")));

            String insertSQLModule = String.format("INSERT INTO Module (contentitemId , titel, versie, naamContactPersoon, emailContactPersoon) VALUES ('%s','%s', '%s', '%s', '%s')",
                    DataShare.getInstance().getContentItemId(),
                    ModuleTitelColumnInput.getText(),
                    ModuleVersieColumnInput.getText(),
                    ModuleNaamContactColumnInput.getText(),
                    ModuleEmailContactColumnInput.getText());

            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQLModule);
            System.out.println("Module successfully created.");
        } catch (SQLException ex) {
            Logger.getLogger(DialogContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void CreateWebcast() {
        try {
            Status selectedStatus = statusComboBox.getValue();
            System.out.println("WEBCAST");
            String insertSQLContentitem = String.format("INSERT INTO contentItems (naamCursus, beschrijving, titel, datum, status) VALUES ('%s','%s', '%s', '%s', '%s')",
                    contentItemsNaamCursusComboBoxInput.getValue(),
                    WebcastBeschrijvingColumnInput.getText(),
                    WebcastTitelColumnInput.getText(),
                    LocalDate.now().toString(),
                    selectedStatus.name());
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQLContentitem);
            System.out.println("ContentItem successfully created.");

            String selectSQLId = "SELECT contentItemId FROM contentItems ORDER BY contentItemId desc";
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), selectSQLId);
            rs.next();
            DataShare.getInstance().setContentItemId(Integer.valueOf(rs.getString("contentItemId")));

            String insertSQLModule = String.format("INSERT INTO Webcast (contentitemId , titel, tijdsDuur, datumPublicatie, url, naamSpreker, organisatieSpreker) VALUES ('%s','%s', '%s', '%s', '%s', '%s', '%s')",
                    DataShare.getInstance().getContentItemId(),
                    WebcastTitelColumnInput.getText(),
                    Integer.valueOf(WebcastTijdsDuurColumnInput.getText()),
                    WebcastDatumPublicatieColumnInput.getValue().toString(),
                    WebcastUrlColumnInput.getText(),
                    WebcastNaamSprekerColumnInput.getText(),
                    WebcastOrganisatieSprekerColumnInput.getText()
            );

            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQLModule);
            System.out.println("Webcast successfully created.");
        } catch (SQLException ex) {
            Logger.getLogger(DialogContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validateAndCreateContentItem() {
        if (DataShare.getInstance().getCreatedItem().toLowerCase().equals("module")) {
            CreateModule();
            return true;
        } else if (DataShare.getInstance().getCreatedItem().toLowerCase().equals("webcast")) {
            CreateWebcast();
            return true;
        } else {
            return false;
        }
    }

    public boolean validateAndUpdateContentItem() {
        //VALIDATE ALL FIELDS
        boolean VALID = true;
        if (VALID) {
            if (DataShare.getInstance().getCreatedItem().toLowerCase().equals("module")) {
                try {
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                            "UPDATE contentItems SET"
                            + " naamCursus = '" + contentItemsNaamCursusComboBoxInput.getValue()
                            + "', beschrijving = '" + ModuleBeschrijvingColumnInput.getText()
                            + "', titel = '" + ModuleTitelColumnInput.getText()
                            + "', status = '" + statusComboBox.getValue()
                            + "' WHERE contentItemId = '" + DataShare.getInstance().getContentItemId() + "'");
                    System.out.println("ContentItem Updated!");
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "UPDATE Module SET"
                            + " titel = '" + ModuleTitelColumnInput.getText()
                            + "', versie = '" + ModuleVersieColumnInput.getText()
                            + "', naamContactPersoon = '" + ModuleNaamContactColumnInput.getText()
                            + "', emailContactPersoon = '" + ModuleEmailContactColumnInput.getText()
                            + "', volgNr = '" + ModuleVolgNrColumnInput.getText()
                            + "' WHERE contentItemId = '" + DataShare.getInstance().getContentItemId() + "'");
                    System.out.println("Module Updated!");
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DialogContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (DataShare.getInstance().getCreatedItem().toLowerCase().equals("webcast")) {
                try {
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                            "UPDATE contentItems SET"
                            + " naamCursus = '" + contentItemsNaamCursusComboBoxInput.getValue()
                            + "', beschrijving = '" + WebcastBeschrijvingColumnInput.getText()
                            + "', titel = '" + WebcastTitelColumnInput.getText()
                            + "', status = '" + statusComboBox.getValue()
                            + "' WHERE contentItemId = '" + DataShare.getInstance().getContentItemId() + "'");
                    System.out.println("ContentItem Updated!");
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "UPDATE Webcast SET"
                            + " titel = '" + WebcastTitelColumnInput.getText()
                            + "', tijdsDuur = '" + Integer.valueOf(WebcastTijdsDuurColumnInput.getText())
                            + "', datumPublicatie = '" + WebcastDatumPublicatieColumnInput.getValue()
                            + "', url = '" + WebcastUrlColumnInput.getText()
                            + "', naamSpreker = '" + WebcastNaamSprekerColumnInput.getText()
                            + "', organisatieSpreker = '" + WebcastOrganisatieSprekerColumnInput.getText()
                            + "' WHERE contentItemId = '" + DataShare.getInstance().getContentItemId() + "'");
                    System.out.println("Webcast Updated!");
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DialogContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

}
