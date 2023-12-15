package javafx;

import Objects.Certificaat;
import Java2Database.DataBaseSQL;
import Objects.Cursist;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.CursistFXMLController.ErrorAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static javafx.DialogCursistFXMLController.cursistDbConnection;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class CertificaatFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private TableView<Certificaat> CertificaatTableView;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        loadTableCertificaat();
        cursistDbConnection = DataBaseSQL.createConnection(cursistDbConnection);
    }
   

    @FXML
    void CertificaatAanmakenClicked(MouseEvent event) {
     DataShare.getInstance().resetCertificaat();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createCertificaatDialog.fxml"));
            DialogPane pane = loader.load();

            DialogCertificaatFXMLController createCertificaatController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            Optional<ButtonType> clickedFinish = dialog.showAndWait();

            if (clickedFinish.isPresent() && clickedFinish.get() == ButtonType.FINISH) {
                createCertificaatController.FinishButtonCreateCertificaatClicked();
                loadTableCertificaat(); // Reload the table after update
            }

            dialog.setTitle("Certificaat toevoegen");

        } catch (IOException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @FXML
    void CertificaatVerwijderenClicked(MouseEvent event) {
        if (removeAlert()){
            try {
                String delete = "DELETE FROM Certificaat WHERE certificaatId = '" + DataShare.getInstance().getCertificaatId() + "'";
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), delete);
            } catch (SQLException ex) {
                //Alert NIET GEVONDEN OF NIET VOLTOOID
                Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        loadTableCertificaat();
    }
    
    boolean removeAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Certificaat Verwijderen!");
        alert.setHeaderText("Weet je zeker dat je de certificaat met ID: " + DataShare.getInstance().getCertificaatId() + "wil verwijderen?");

        // Yes, No knoppen
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        // Check the result
        return result.isPresent() && result.get() == buttonTypeYes;
    }

    @FXML
    void CertificaatBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    TableColumn<Certificaat, String> IdCertificaatColumn;
    @FXML
    TableColumn<Certificaat, Byte> BeoordelingCertificaatColumn;
    @FXML
    TableColumn<Certificaat, String> MedewerkerNummerCertificaatColumn;
    @FXML
    TableColumn<Certificaat, Integer> InschrijfIdCertificaatColumn;

    ObservableList<Certificaat> observableCertificaat;

    private void initTable() {
        observableCertificaat = FXCollections.observableArrayList();
        IdCertificaatColumn.setCellValueFactory(new PropertyValueFactory<>("certificaatId"));
        BeoordelingCertificaatColumn.setCellValueFactory(new PropertyValueFactory<>("beoordeling"));
        MedewerkerNummerCertificaatColumn.setCellValueFactory(new PropertyValueFactory<>("medeWerkerNaam"));
        InschrijfIdCertificaatColumn.setCellValueFactory(new PropertyValueFactory<>("inschrijfId"));
    }

    public void loadTableCertificaat() {
        try {
//            , totaalVoortgang
            System.out.println("test");
            initTable();
            System.out.println("test");
            try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT certificaatId, beoordeling, medewerkerNaam, inschrijfId FROM Certificaat").executeQuery()) {
                while (rs.next()) {
                    Certificaat Certificaat = new Certificaat();
                    Certificaat.setCertificaatId(rs.getInt("certificaatId"));
                    Certificaat.setBeoordeling(rs.getByte("beoordeling"));
                    Certificaat.setMedeWerkerNaam(rs.getString("medewerkerNaam"));
                    Certificaat.setInschrijfId(rs.getInt("inschrijfId"));
                    
                    System.out.println(Certificaat.getNaamCursist());
                    
        observableCertificaat.add(Certificaat); 
                }
            }
            System.out.println("PUNT 4");
            CertificaatTableView.setItems(observableCertificaat);
            CertificaatTableView.refresh();
            System.out.println("PUNT 5");

        } catch (SQLException ex) {
            Logger.getLogger(CertificaatFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void rowClicked(MouseEvent event) {
      try {
        Certificaat clickedCertificaat = CertificaatTableView.getSelectionModel().getSelectedItem();

        if (clickedCertificaat != null) {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT * FROM Certificaat WHERE certificaatId = '" + clickedCertificaat.getCertificaatId() + "'");
            
            if (rs.next()) {
                DataShare.getInstance().setCertificaatId(rs.getInt("certificaatId"));
                DataShare.getInstance().setCertificaatInschrijfId(rs.getInt("inschrijfId"));
                DataShare.getInstance().setBeoordeling(rs.getByte("beoordeling"));
                DataShare.getInstance().setMedeWerkerNaam(rs.getString("medewerkerNaam"));
            } else {
                ErrorAlert("Deze cursist bestaat niet meer!", "Onbekende cursist");
            }
        }
    } catch (SQLException | DateTimeParseException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
