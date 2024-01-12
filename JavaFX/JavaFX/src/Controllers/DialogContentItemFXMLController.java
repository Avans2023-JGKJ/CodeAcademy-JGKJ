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
import Validatie.DataValidatie;
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
    private ComboBox<Short> ModuleVolgNrColumnInput;

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

    @FXML
    private ObservableList<Short> volgNrList = FXCollections.observableArrayList();

    //Variable
    private boolean checkId;
    private short VolgNrInput;

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

    @FXML
    void refreshVolgNr(ActionEvent event) {
        volgNrList.clear();
        try {
            ResultSet rs2 = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT aantalContentItems FROM Cursus WHERE naamCursus = '" + contentItemsNaamCursusComboBoxInput.getValue() + "'");
            rs2.next();
            short max = rs2.getShort("aantalContentItems");
            for (short i = 1; i <= max; i++) {
                volgNrList.add(i);
            }
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT m.volgNr from Module m "
                    + "JOIN contentItems co on co.contentItemId =m.contentitemId "
                    + "JOIN Cursus cu on cu.naamCursus = co.naamCursus "
                    + "WHERE cu.naamCursus = '" + contentItemsNaamCursusComboBoxInput.getValue() + "'");
            while (rs.next()) {
                volgNrList.remove(rs.getShort("volgNr") - 1);
            }
            ModuleVolgNrColumnInput.setItems(volgNrList);
        } catch (SQLException ex) {
            Logger.getLogger(DialogContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void refreshVolgNr() {
        volgNrList.clear();
        try {
            ResultSet rs2 = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT aantalContentItems FROM Cursus WHERE naamCursus = '" + DataShare.getInstance().getNaamCursus() + "'");
            rs2.next();
            short max = rs2.getShort("aantalContentItems");
            for (short i = 1; i <= max; i++) {
                volgNrList.add(i);
            }
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT m.volgNr from Module m "
                    + "JOIN contentItems co ON co.contentItemId = m.contentitemId "
                    + "JOIN Cursus cu ON cu.naamCursus = co.naamCursus "
                    + "WHERE cu.naamCursus = '" + contentItemsNaamCursusComboBoxInput.getValue() + "'");
            while (rs.next()) {
                if (rs.getShort("volgNr") != DataShare.getInstance().getVolgordeNr()) {
                    volgNrList.remove(Short.valueOf(rs.getShort("volgNr")));
                }
            }
            ModuleVolgNrColumnInput.setItems(volgNrList);
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
            refreshVolgNr();
            ModuleVolgNrColumnInput.setValue((DataShare.getInstance().getVolgordeNr()));
        }

    }

    boolean ValidateAndCreateModule() {
        try {
            if (ModuleVolgNrColumnInput.getValue() == null) {
                VolgNrInput = -1;
            }
            else{
                VolgNrInput = ModuleVolgNrColumnInput.getValue();
            }

            checkId = false;
            if (DataValidatie.AlterContentItemValid(
                    contentItemsNaamCursusComboBoxInput.getValue(),
                    ModuleBeschrijvingColumnInput.getText(),
                    ModuleTitelColumnInput.getText(),
                    statusComboBox.getValue())
                    && DataValidatie.AlterModuleValid(
                            ModuleTitelColumnInput.getText(),
                            ModuleVersieColumnInput.getText(),
                            ModuleNaamContactColumnInput.getText(),
                            ModuleEmailContactColumnInput.getText(),
                            VolgNrInput)) {

                String insertSQLContentitem = String.format("INSERT INTO contentItems (naamCursus, beschrijving, titel, datum, status) VALUES ('%s','%s', '%s', '%s', '%s')",
                        contentItemsNaamCursusComboBoxInput.getValue(),
                        ModuleBeschrijvingColumnInput.getText(),
                        ModuleTitelColumnInput.getText(),
                        LocalDate.now().toString(),
                        statusComboBox.getValue().name());
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQLContentitem);

                String selectSQLId = "SELECT contentItemId FROM contentItems ORDER BY contentItemId desc";
                ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), selectSQLId);
                rs.next();
                DataShare.getInstance().setContentItemId(Integer.valueOf(rs.getString("contentItemId")));

                String insertSQLModule = String.format("INSERT INTO Module (contentitemId , titel, versie, naamContactPersoon, emailContactPersoon, volgNr) VALUES ('%s','%s', '%s', '%s', '%s', '%s')",
                        DataShare.getInstance().getContentItemId(),
                        ModuleTitelColumnInput.getText(),
                        ModuleVersieColumnInput.getText(),
                        ModuleNaamContactColumnInput.getText(),
                        ModuleEmailContactColumnInput.getText(),
                        ModuleVolgNrColumnInput.getValue());

                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQLModule);
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DialogContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    boolean ValidateAndCreateWebcast() {
        try {
            DataShare.getInstance().setNaamCursus(contentItemsNaamCursusComboBoxInput.getValue());
            checkId = false;
            if (DataValidatie.AlterContentItemValid(
                    contentItemsNaamCursusComboBoxInput.getValue(),
                    WebcastBeschrijvingColumnInput.getText(),
                    WebcastTitelColumnInput.getText(),
                    statusComboBox.getValue())
                    && DataValidatie.AlterWebcastValid(
                            WebcastTitelColumnInput.getText(),
                            WebcastTijdsDuurColumnInput.getText(),
                            WebcastDatumPublicatieColumnInput.getValue(),
                            WebcastUrlColumnInput.getText(),
                            WebcastNaamSprekerColumnInput.getText(),
                            WebcastOrganisatieSprekerColumnInput.getText())) {

                String insertSQLContentitem = String.format("INSERT INTO contentItems (naamCursus, beschrijving, titel, datum, status) VALUES ('%s','%s', '%s', '%s', '%s')",
                        contentItemsNaamCursusComboBoxInput.getValue(),
                        WebcastBeschrijvingColumnInput.getText(),
                        WebcastTitelColumnInput.getText(),
                        LocalDate.now().toString(),
                        statusComboBox.getValue().name());
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), insertSQLContentitem);

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
                return true;

            }

            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DialogContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean validateAndUpdateContentItem() {
        //VALIDATE ALL FIELDS
        if (DataShare.getInstance().getCreatedItem().toLowerCase().equals("module")) {
            try {
                DataShare.getInstance().setNaamCursus(contentItemsNaamCursusComboBoxInput.getValue());
                if (DataValidatie.AlterContentItemValid(
                        contentItemsNaamCursusComboBoxInput.getValue(),
                        ModuleBeschrijvingColumnInput.getText(),
                        ModuleTitelColumnInput.getText(),
                        statusComboBox.getValue())
                        && DataValidatie.AlterModuleValid(
                                ModuleTitelColumnInput.getText(),
                                ModuleVersieColumnInput.getText(),
                                ModuleNaamContactColumnInput.getText(),
                                ModuleEmailContactColumnInput.getText(),
                                ModuleVolgNrColumnInput.getValue())) {
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                            "UPDATE contentItems SET"
                            + " naamCursus = '" + contentItemsNaamCursusComboBoxInput.getValue()
                            + "', beschrijving = '" + ModuleBeschrijvingColumnInput.getText()
                            + "', titel = '" + ModuleTitelColumnInput.getText()
                            + "', status = '" + statusComboBox.getValue()
                            + "' WHERE contentItemId = '" + DataShare.getInstance().getContentItemId() + "'");
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "UPDATE Module SET"
                            + " titel = '" + ModuleTitelColumnInput.getText()
                            + "', versie = '" + ModuleVersieColumnInput.getText()
                            + "', naamContactPersoon = '" + ModuleNaamContactColumnInput.getText()
                            + "', emailContactPersoon = '" + ModuleEmailContactColumnInput.getText()
                            + "', volgNr = '" + ModuleVolgNrColumnInput.getValue()
                            + "' WHERE contentItemId = '" + DataShare.getInstance().getContentItemId() + "'");
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DialogContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (DataShare.getInstance().getCreatedItem().toLowerCase().equals("webcast")) {
            try {
                if (DataValidatie.AlterContentItemValid(
                        contentItemsNaamCursusComboBoxInput.getValue(),
                        WebcastBeschrijvingColumnInput.getText(),
                        WebcastTitelColumnInput.getText(),
                        statusComboBox.getValue())
                        && DataValidatie.AlterWebcastValid(
                                WebcastTitelColumnInput.getText(),
                                WebcastTijdsDuurColumnInput.getText(),
                                WebcastDatumPublicatieColumnInput.getValue(),
                                WebcastUrlColumnInput.getText(),
                                WebcastNaamSprekerColumnInput.getText(),
                                WebcastOrganisatieSprekerColumnInput.getText())) {
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                            "UPDATE contentItems SET"
                            + " naamCursus = '" + contentItemsNaamCursusComboBoxInput.getValue()
                            + "', beschrijving = '" + WebcastBeschrijvingColumnInput.getText()
                            + "', titel = '" + WebcastTitelColumnInput.getText()
                            + "', status = '" + statusComboBox.getValue()
                            + "' WHERE contentItemId = '" + DataShare.getInstance().getContentItemId() + "'");
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "UPDATE Webcast SET"
                            + " titel = '" + WebcastTitelColumnInput.getText()
                            + "', tijdsDuur = '" + Integer.valueOf(WebcastTijdsDuurColumnInput.getText())
                            + "', datumPublicatie = '" + WebcastDatumPublicatieColumnInput.getValue()
                            + "', url = '" + WebcastUrlColumnInput.getText()
                            + "', naamSpreker = '" + WebcastNaamSprekerColumnInput.getText()
                            + "', organisatieSpreker = '" + WebcastOrganisatieSprekerColumnInput.getText()
                            + "' WHERE contentItemId = '" + DataShare.getInstance().getContentItemId() + "'");
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DialogContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

}
